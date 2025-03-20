package com.app.vms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String make;
    private String model;
    private int year;
    private String type; // Car, Truck, SUV, etc.
    private String licensePlate;
    private double pricePerDay;
    @Column(name = "is_available")
    private boolean isAvailable;

    @OneToMany(mappedBy = "vehicle")
    @JsonBackReference
    private List<Reservation> reservations;
}
