package com.app.vms.dto;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ReservationRequest {
    private Long vehicleId;
    private Long userId;
    private String pickupDate;
    private String returnDate;
}