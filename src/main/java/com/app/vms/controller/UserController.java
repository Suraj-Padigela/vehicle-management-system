package com.app.vms.controller;

import com.app.vms.Service.PaymentService;
import com.app.vms.Service.ReservationService;
import com.app.vms.Service.UserService;
import com.app.vms.Service.VehicleService;
import com.app.vms.dto.ReservationRequest;
import com.app.vms.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    ReservationService reservationService;
    @Autowired
    VehicleService vehicleService;
    @Autowired
    PaymentService paymentService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @PostMapping(value = "/reserve", consumes = "application/json")
    public ResponseEntity<String> reserveVehicle(@RequestBody ReservationRequest reservationRequest) {
        LocalDateTime pickup = LocalDateTime.parse(reservationRequest.getPickupDate());
        LocalDateTime returnDt = LocalDateTime.parse(reservationRequest.getReturnDate());

        String response = reservationService.reserveVehicle(reservationRequest.getVehicleId(), reservationRequest.getUserId(), pickup, returnDt);

        return ResponseEntity.ok(response);
    }

    // Return a vehicle
    @PostMapping("/return")
    public ResponseEntity<String> returnVehicle(@RequestParam Long vehicleId) {
        String response = vehicleService.returnVehicle(vehicleId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/pay")
    public ResponseEntity<String> makePayment(@RequestParam Long invoiceId,
                                              @RequestParam double amount,
                                              @RequestParam String paymentMethod) {
        String response = paymentService.makePayment(invoiceId, amount, paymentMethod);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        try {
            User user = userService.updateUser(id, updatedUser);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
