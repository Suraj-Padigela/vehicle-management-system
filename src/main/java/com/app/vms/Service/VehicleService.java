package com.app.vms.Service;

import com.app.vms.entity.Invoice;
import com.app.vms.entity.Reservation;
import com.app.vms.entity.Vehicle;
import com.app.vms.repository.InvoiceRepository;
import com.app.vms.repository.ReservationRepository;
import com.app.vms.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {
    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    InvoiceRepository invoiceRepository;

    private static final double LATE_FEE_PER_DAY = 20.0;

    public List<Vehicle> getAllVehicles(){
        return vehicleRepository.findAll();
    }

    public Optional<Vehicle> getById(Long id){
        return vehicleRepository.findById(id);
    }

    public String addOrUpdateVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
        return "Vehicle added successfully!";
    }

    public List<Vehicle> getAvailableVehicles() {
        return vehicleRepository.findByIsAvailableTrue();
    }

    public List<Vehicle> searchVehiclesByType(String type){
        return vehicleRepository.findByTypeAndIsAvailableTrue(type);
    }

    public String returnVehicle(Long vehicleId){
        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(vehicleId);
        if(vehicleOptional.isPresent()){
            Vehicle vehicle = vehicleOptional.get();
            Optional<Reservation> reservationOptional = reservationRepository.findByVehicleAndIsReturnedFalse(vehicle);
            if(reservationOptional.isPresent()){
                Reservation reservation = reservationOptional.get();

                LocalDateTime actualReturnDate = LocalDateTime.now();
                long rentalDays = ChronoUnit.DAYS.between(reservation.getPickupDate(), actualReturnDate);
                rentalDays = Math.max(rentalDays, 1);

                double basePrice = rentalDays * vehicle.getPricePerDay();

                double lateFee = 0;
                if(actualReturnDate.isAfter(reservation.getReturnDate())){
                    long lateDays = ChronoUnit.DAYS.between(reservation.getReturnDate(), actualReturnDate);
                    lateFee = lateDays * LATE_FEE_PER_DAY;
                }

                Invoice invoice = new Invoice(reservation, basePrice, lateFee);
                invoiceRepository.save(invoice);

                reservation.setReturned(true);
                reservationRepository.save(reservation);

                vehicle.setAvailable(true);
                vehicleRepository.save(vehicle);

                return "Vehicle returned successfully. Invoice ID: " + invoice.getId();
            }
            return "No active reservation found for this vehicle";
        }
        return "Vehicle is not present";
    }


}
