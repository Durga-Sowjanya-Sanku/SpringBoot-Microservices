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
public class InventoryResponse {

    private long event_id;
    private String event;
    private long capacity;
    private VenueResponse venue;
    private BigDecimal ticketPrice;
}
