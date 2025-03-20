package com.app.vms.Service;

import com.app.vms.entity.Admin;
import com.app.vms.entity.Reservation;
import com.app.vms.entity.User;
import com.app.vms.entity.Vehicle;
import com.app.vms.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    VehicleService vehicleService;
    @Autowired
    ReservationService reservationService;
    @Autowired
    UserService userService;

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public String addOrUpdateVehicle(Vehicle vehicle) {
        vehicleService.addOrUpdateVehicle(vehicle);
        return "Vehicle added successfully!";
    }

    public List<Reservation> getReservationsByVehicle(Long vehicleId) {
        Optional<Vehicle> vehicleOptional = vehicleService.getById(vehicleId);
        return vehicleOptional.map(reservationService::findByVehicle).orElse(null);
    }

    public List<Reservation> getReservationsByUser(Long userId) {
        Optional<User> userOpt = userService.getById(userId);
        return userOpt.map(reservationService::findByUser).orElse(null);
    }
}
