package com.app.vms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    private double amount;
    private LocalDateTime paymentDate;
    private String paymentMethod; // Card, UPI, etc.
}
