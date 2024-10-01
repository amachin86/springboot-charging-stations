package com.example.demotest.service;

import com.example.demotest.dto.request.ChargingStationRequest;
import com.example.demotest.dto.response.ChargingStationResponse;
import com.example.demotest.entity.ChargerType;
import com.example.demotest.entity.ChargingStation;
import com.example.demotest.entity.Status;
import com.example.demotest.repository.ChargingStationRepository;
import com.example.demotest.utils.MapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //injection dependencies for constructor
@Slf4j
public class ChargingStationService {
    @Autowired
    private ChargingStationRepository repository;
    @Autowired
    private final Mapper dozerMapper;


    //@Transactional
    public ChargingStationResponse save(ChargingStation chargingStation) {
        ChargingStationResponse chargingStationResponse = null;
        log.info("ChargingStationService::save execution started.");
        try {
            log.debug("ChargingStationService::save request parameters {}", chargingStation);
           // ChargingStation saveChargingStation = dozerMapper.map(chargingStation, ChargingStation.class);

            ChargingStation chanChargingStationResp = repository.saveAndFlush(chargingStation);

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

        ChargingStation chanStation = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Charging Station with id " + id + " not found"));
        try {
            log.debug("ChargingStationService::update request parameters id {} and Charging Station {}", id, chargingStation);
            ChargingStation saveStation = dozerMapper.map(chargingStation, ChargingStation.class);
            saveStation.setId(chanStation.getId());
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

        ChargingStation ChargingStationRes = repository.findById(id)
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

    @Cacheable("chargingStation")
    public ChargingStationResponse ChargingStationById(UUID id) {

        ChargingStationResponse chargingStationResponse = null;
        log.info("ChargingStationService::ChargingStationById execution started.");


        ChargingStation ChargingStationRes = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Charging Station with id " + id + " not found"));

        chargingStationResponse = dozerMapper.map(ChargingStationRes, ChargingStationResponse.class);
        log.debug("ChargingStationService:ChargingStationById retrieving drones from the Database {}", chargingStationResponse);

        log.info("ChargingStationService:ChargingStationById execution ended.");
        return chargingStationResponse;

    }

    @Cacheable("chargingStation")
    public List<ChargingStationResponse> findAll() {
        return repository.findAll().stream().map(MapperUtils::convertDroneToChargingStationResponse).collect(Collectors.toList());
    }

    @Cacheable("chargingStation")
    public Page<ChargingStationResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(MapperUtils::convertDroneToChargingStationResponse);
    }
}

/*
 * This will cache the results of the findAll() and findById() methods in the ChargingStationService class.
 * The cache will be stored in memory and will be invalidated when the data is updated.
 */
