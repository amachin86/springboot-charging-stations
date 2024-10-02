package com.example.demotest.service;

import com.example.demotest.dto.request.StationChargerTypeRequest;
import com.example.demotest.dto.response.StationChargerTypeResponse;
import com.example.demotest.entity.ChargingStation;
import com.example.demotest.entity.StationChargerType;
import com.example.demotest.repository.ChargingStationRepository;
import com.example.demotest.repository.StationChargerTypeRepository;
import com.example.demotest.utils.MapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor //injection dependencies for constructor
@Slf4j
public class StationChargerTypeService {
    @Autowired
    private ChargingStationRepository chargingStationRepository;
    @Autowired
    private StationChargerTypeRepository stationChargerTypeRepository;
    @Autowired
    private final Mapper dozerMapper;

    @Transactional
    public StationChargerTypeResponse save(StationChargerTypeRequest chargingStationRequest) {
        UUID chargingStationId = chargingStationRequest.getChargingStation().getId();
        Optional<ChargingStation> changingStation = chargingStationRepository.findById(chargingStationId);
                //.orElseThrow(() -> new RuntimeException("Charging Station with id " + chargingStationId + " not found"));
        if (!changingStation.isPresent())
            return null;

        StationChargerType stationChargerType = dozerMapper.map(chargingStationRequest, StationChargerType.class);
        stationChargerType.setChargingStation(changingStation.get());
        StationChargerType stationChargerTypeResp = stationChargerTypeRepository.saveAndFlush(stationChargerType);
        StationChargerTypeResponse stationChargerTypeResponse = dozerMapper.map(stationChargerTypeResp, StationChargerTypeResponse.class);

        return stationChargerTypeResponse;

    }

    @Transactional
    public StationChargerTypeResponse update(Long id, StationChargerTypeRequest chargingStationRequest) {
        //search chargingStation
        UUID chargingStationId = chargingStationRequest.getChargingStation().getId();
        Optional<ChargingStation> changingStation = chargingStationRepository.findById(chargingStationId);
        //.orElseThrow(() -> new RuntimeException("Charging Station with id " + chargingStationId + " not found"));
        if (!changingStation.isPresent())
            return null;
        //search changingStationtype
        Optional<StationChargerType> changingStationtype = stationChargerTypeRepository.findById(id);
               // .orElseThrow(() -> new RuntimeException("Charging Station with id " + chargingStationId + " not found"));
        if (!changingStationtype.isPresent()) return null;

        StationChargerType stationChargerType = dozerMapper.map(chargingStationRequest, StationChargerType.class);

        stationChargerType.setChargingStation(changingStation.get());
        stationChargerType.setId(changingStationtype.get().getId());

        StationChargerType stationChargerTypeResp = stationChargerTypeRepository.saveAndFlush(stationChargerType);
        StationChargerTypeResponse stationChargerTypeResponse = dozerMapper.map(stationChargerTypeResp, StationChargerTypeResponse.class);

        return stationChargerTypeResponse;

    }

    public StationChargerTypeResponse deleteById(Long id){

        log.info("StationChargerTypeService::deleteById execution started.");
        Optional<StationChargerType> stationChargerType = stationChargerTypeRepository.findById(id);
        //.orElseThrow(() -> new RuntimeException("Charging Station with id " + chargingStationId + " not found"));
        if (!stationChargerType.isPresent())
            return null;
        stationChargerTypeRepository.delete(stationChargerType.get());
        StationChargerTypeResponse stationChargerTypeResponse = dozerMapper.map(stationChargerType, StationChargerTypeResponse.class);

        log.info("StationChargerTypeService::deleteById execution ended.");
        return stationChargerTypeResponse;

    }

    public StationChargerTypeResponse StationChargerTypeById(Long id){

        log.info("StationChargerTypeService::StationChargerTypeById execution started.");
        Optional<StationChargerType> stationChargerType = stationChargerTypeRepository.findById(id);
        //.orElseThrow(() -> new RuntimeException("Charging Station with id " + chargingStationId + " not found"));
        if (!stationChargerType.isPresent())
            return null;

        StationChargerTypeResponse stationChargerTypeResponse = dozerMapper.map(stationChargerType, StationChargerTypeResponse.class);

        log.info("StationChargerTypeService::StationChargerTypeById execution ended.");
        return stationChargerTypeResponse;


    }


    public List<StationChargerTypeResponse> findAll() {
        return stationChargerTypeRepository.findAll().stream().map(MapperUtils::convertStationChargerTypeToStationChargerTypeResponse).collect(Collectors.toList());
    }


    public Page<StationChargerTypeResponse> findAll(Pageable pageable) {
        return stationChargerTypeRepository.findAll(pageable).map(MapperUtils::convertStationChargerTypeToStationChargerTypeResponse);
    }

}
