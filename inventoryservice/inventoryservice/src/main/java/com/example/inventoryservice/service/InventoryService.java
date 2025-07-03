package com.example.inventoryservice.service;

import com.example.inventoryservice.entity.Event;
import com.example.inventoryservice.entity.Venue;
import com.example.inventoryservice.repository.EventRepository;
import com.example.inventoryservice.repository.VenueRepository;
import com.example.inventoryservice.response.EventInventoryResponse;
import com.example.inventoryservice.response.VenueInventoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InventoryService {

    @Autowired
    private  EventRepository eventRepository;

    @Autowired
    private VenueRepository venueRepository;
    public List<EventInventoryResponse> getAllEvents() {
        final List<Event> events = eventRepository.findAll();

        // This code is to convert the list of Event entities to a list of EventInventoryResponse DTOs
        // DTOs are used to transfer data between layers, in this case from the service layer to the controller layer.
        return events.stream().map(event -> EventInventoryResponse.builder()
                .event_id(event.getId())
                .event(event.getName())
                .capacity(event.getLeftCapacity())
                .venue(event.getVenue())
                .build()).collect(Collectors.toList());


    }

    public VenueInventoryResponse getVenueInformation(long venueId) {
        final Venue venue = venueRepository.findById(venueId).orElse(null);

        return VenueInventoryResponse.builder()
                .venueId(venue.getId())
                .venueName(venue.getName())
                .totalCapacity(venue.getTotalCapacity())
                .build();

    }

    public EventInventoryResponse getEventInventory(long eventId) {
        final Event event = eventRepository.findById(eventId).orElse(null);

        if (event == null) {
            throw new RuntimeException("Event not found with id: " + eventId);
        }

        return EventInventoryResponse.builder()
                .event(event.getName())
                .capacity(event.getLeftCapacity())
                .venue(event.getVenue())
                .ticketPrice(event.getTicketPrice())
                .event_id(event.getId())
                .build();
    }

    public void updateEventCapacity(long eventId, long capacityCount) {

        final Event event = eventRepository.findById(eventId).orElseThrow(() ->
            new RuntimeException("Event not found with id: " + eventId));

        event.setLeftCapacity(event.getLeftCapacity() - capacityCount);

        eventRepository.saveAndFlush(event);
        log.info("Updated event capacity for event ID {}: new capacity is {}",
                eventId, event.getLeftCapacity());
    }
}
