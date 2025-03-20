package com.app.vms.repository;

import com.app.vms.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByIsAvailableTrue();
    List<Vehicle> findByTypeAndIsAvailableTrue(String type);
}
