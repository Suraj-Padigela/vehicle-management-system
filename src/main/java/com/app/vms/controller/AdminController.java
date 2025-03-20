package com.app.vms.controller;

import com.app.vms.Service.AdminService;
import com.app.vms.entity.Admin;
import com.app.vms.entity.Reservation;
import com.app.vms.entity.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {
    private static final Logger log = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    AdminService adminService;



    @GetMapping(value = "/all")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminService.getAllAdmins();
        if (admins.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(admins);
    }

    @PostMapping(value = "/addVehicle", consumes = "application/json")
    public ResponseEntity<String> addVehicle(@RequestBody Vehicle vehicle) {
        vehicle.setAvailable(true);
        String result = adminService.addOrUpdateVehicle(vehicle);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/reservations/vehicle/{vehicleId}")
    public ResponseEntity<List<Reservation>> getReservationsByVehicle(@PathVariable Long vehicleId) {
        List<Reservation> reservations = adminService.getReservationsByVehicle(vehicleId);
        if (reservations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reservations);
    }

    @GetMapping(value = "/reservations/user/{userId}")
    public ResponseEntity<List<Reservation>> getReservationsByUser(@PathVariable Long userId) {
        List<Reservation> reservations = adminService.getReservationsByUser(userId);
        if (reservations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reservations);
    }


}
