package com.example.bookingservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Customer {


    @Id
    @Column(name = "id")
    private long id;

    @Column
    private String name;

    private String email;

    private String address;
}
