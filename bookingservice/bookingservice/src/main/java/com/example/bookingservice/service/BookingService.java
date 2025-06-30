package com.example.bookingservice.service;

import com.example.bookingservice.client.InventoryServiceClient;
import com.example.bookingservice.entity.Customer;
import com.example.bookingservice.event.BookingEvent;
import com.example.bookingservice.repository.CustomerRepository;
import com.example.bookingservice.request.BookingRequest;
import com.example.bookingservice.response.BookingResponse;
import com.example.bookingservice.response.InventoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class BookingService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private InventoryServiceClient inventoryServiceClient;

    @Autowired
    private KafkaTemplate<String, BookingEvent> kafkaTemplate;

    public BookingResponse createBooking(BookingRequest bookingRequest) {

        //check if user exists
        final Customer customer = customerRepository.findById(bookingRequest.getUserId()).orElse(null);

        if (customer == null){
            throw new RuntimeException("User not found with id: " + bookingRequest.getUserId());
        }

        System.out.println("\n\n===>\n");
        System.out.println("Booking request received for user: " + customer.getName());

        final InventoryResponse inventoryResponse = inventoryServiceClient.getInventory(bookingRequest.getEventId());
        log.info("Retrieved inventory for event {}: {}", bookingRequest.getEventId(), inventoryResponse);

        if(inventoryResponse.getCapacity() < bookingRequest.getTicketCount()) {
            throw new RuntimeException("Not enough tickets available for event: " + bookingRequest.getEventId());
        }

        //create booking
        final BookingEvent bookingEvent = createBookingEvent(bookingRequest, customer, inventoryResponse);

        //send the bookingevent to kafka
        kafkaTemplate.send("booking", bookingEvent);
        log.info("Booking event sent to Kafka: {}", bookingEvent);

        return BookingResponse.builder()
                .userId(customer.getId())
                .eventId(bookingEvent.getEventId())
                .ticketCount(bookingEvent.getTicketCount())
                .totalPrice(bookingEvent.getTotalPrice())
                .build();
    }

    private BookingEvent createBookingEvent(BookingRequest bookingRequest, Customer customer, InventoryResponse inventoryResponse) {

        return BookingEvent.builder()
                .userId(customer.getId())
                .eventId(bookingRequest.getEventId())
                .ticketCount(bookingRequest.getTicketCount())
                .totalPrice(inventoryResponse.getTicketPrice().multiply(BigDecimal.valueOf(bookingRequest.getTicketCount())))
                .build();
    }
}
