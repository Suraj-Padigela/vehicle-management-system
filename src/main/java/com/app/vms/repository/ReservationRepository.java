package com.app.vms.repository;

import com.app.vms.entity.Reservation;
import com.app.vms.entity.User;
import com.app.vms.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByVehicle(Vehicle vehicle);

    List<Reservation> findByUser(User user);

    Optional<Reservation> findByVehicleAndIsReturnedFalse(Vehicle vehicle);
}
