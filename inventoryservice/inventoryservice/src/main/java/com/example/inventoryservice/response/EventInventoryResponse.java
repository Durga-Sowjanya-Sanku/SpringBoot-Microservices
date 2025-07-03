package com.example.inventoryservice.response;


import com.example.inventoryservice.entity.Venue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventInventoryResponse {
    private long event_id;
    private String event;
    private long capacity;
    private Venue venue;
    private BigDecimal ticketPrice;
}
