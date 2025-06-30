package com.example.bookingservice.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VenueResponse {

    private long id;
    private String name;
    private String address;
    private long totalCapacity;

}
