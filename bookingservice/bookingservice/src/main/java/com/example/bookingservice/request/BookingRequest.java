package com.example.bookingservice.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class BookingRequest {

    private long userId;
    private long eventId;
    private long ticketCount;

}
