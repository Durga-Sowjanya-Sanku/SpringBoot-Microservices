package com.example.bookingservice.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {
    private long userId;
    private long eventId;
    private long ticketCount;
    private BigDecimal totalPrice;
}
