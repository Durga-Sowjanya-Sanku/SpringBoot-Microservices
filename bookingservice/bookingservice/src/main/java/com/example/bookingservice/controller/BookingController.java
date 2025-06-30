package com.example.bookingservice.controller;

import com.example.bookingservice.service.BookingService;
import com.example.bookingservice.request.BookingRequest;
import com.example.bookingservice.response.BookingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // The consumes means that this endpoint will accept JSON data in the request body
    // The produces means that this endpoint will return JSON data in the response body
    @PostMapping(consumes = "application/json", produces = "application/json", path = "/booking")
    public BookingResponse createBooking(@RequestBody BookingRequest bookingRequest){
        return bookingService.createBooking(bookingRequest);
    }
}
