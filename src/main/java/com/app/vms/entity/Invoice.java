package com.app.vms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    private double totalAmount;
    private boolean isPaid;
    private double lateFee;
    private LocalDateTime generatedAt;

    public Invoice(Reservation reservation, double totalAmount, double lateFee) {
        this.reservation = reservation;
        this.totalAmount = totalAmount;
        this.lateFee = lateFee;
        this.isPaid = false; // Default to unpaid
        this.generatedAt = LocalDateTime.now();
    }

}