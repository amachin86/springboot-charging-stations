package com.example.demotest.repository;

import com.example.demotest.entity.ChargingStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ChargingStationRepository extends JpaRepository<ChargingStation, UUID> {
    Optional<ChargingStation> findChargingStationById(UUID id);
}
