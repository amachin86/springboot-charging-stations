package com.example.demotest.service;

import com.example.demotest.dto.request.ChargingStationRequest;
import com.example.demotest.dto.response.ChargingStationResponse;
import com.example.demotest.entity.ChargingStation;
import com.example.demotest.repository.ChargingStationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor //injection dependencies for constructor
@Slf4j
public class ChargingStationService {

    private ChargingStationRepository repository;

    private final Mapper dozerMapper;

    @Cacheable("chargingStations")
    public List<ChargingStation> findAll() {
        return repository.findAll();
    }

    @Cacheable("chargingStation")
    public Optional<ChargingStation> findById(UUID id) {
        return repository.findById(id);
    }
    @Transactional
    public ChargingStationResponse save(ChargingStationRequest chargingStation) {
        ChargingStationResponse chargingStationResponse = null;
        log.info("ChargingStationService::save execution started.");
        try {
            log.debug("ChargingStationService::save request parameters {}", chargingStation);
            ChargingStation saveChargingStation = dozerMapper.map(chargingStation, ChargingStation.class);
            ChargingStation chanChargingStationResp = repository.saveAndFlush(saveChargingStation);
            chargingStationResponse = dozerMapper.map(chanChargingStationResp, ChargingStationResponse.class);
            log.debug("ChargingStationService::save receive response from Database {}", chargingStationResponse);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting Charging Station to Database, Exception message {}", ex.getMessage());
            throw new RuntimeException("Exception occurred while created a new Charging Station");
        }
        log.info("DroneServiceImpl::save execution ended.");
        return chargingStationResponse;
    }

    public ChargingStationResponse update(UUID id, ChargingStationRequest chargingStation) {

        ChargingStationResponse chargingStationResponse = null;
        log.info("ChargingStationService::update execution started.");

        ChargingStation chanStation = repository.findChargingStationById(id)
                .orElseThrow(() -> new RuntimeException("Charging Station with id " + id + " not found"));
        try {
            log.debug("ChargingStationService::update request parameters id {} and Charging Station {}", id, chargingStation);
            ChargingStation saveStation = dozerMapper.map(chargingStation, ChargingStation.class);
            saveStation.setId(id);
            ChargingStation droneResp = repository.save(saveStation);
            chargingStationResponse = dozerMapper.map(droneResp, ChargingStationResponse.class);
            log.debug("ChargingStationService::updateDrone receive response from Database {}", chargingStationResponse);
        } catch (Exception ex) {
            log.error("Exception occurred while updating Charging Station to Database, Exception message {}", ex.getMessage());
            throw new RuntimeException("Exception occurred while update Charging Station Service");
        }
        log.info("ChargingStationService::update execution ended.");
        return chargingStationResponse;
    }

    public ChargingStationResponse deleteById(UUID id) {

        ChargingStationResponse chargingStationResponse = null;
        log.info("ChargingStationService::deleteById execution started.");

        ChargingStation ChargingStationRes = repository.findChargingStationById(id)
                .orElseThrow(() -> new RuntimeException("Charging Station with id " + id + " not found"));
        try {
            log.debug("DroneServiceImpl::deleteById request parameters id {}", id);

            repository.deleteById(id);
            chargingStationResponse = dozerMapper.map(ChargingStationRes, ChargingStationResponse.class);
            log.debug("ChargingStationService::deleteById receive response from Database {}", chargingStationResponse);
        } catch (Exception ex) {
            log.error("Exception occurred while updating Charging Station to Database, Exception message {}", ex.getMessage());
            throw new RuntimeException("Exception occurred while update Charging Station Service");
        }
        log.info("ChargingStationService::deleteById execution ended.");
        return chargingStationResponse;

    }
}

/*
 * This will cache the results of the findAll() and findById() methods in the ChargingStationService class.
 * The cache will be stored in memory and will be invalidated when the data is updated.
 */
