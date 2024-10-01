package com.example.demotest.service;

import com.example.demotest.dto.request.ChargingStationRequest;
import com.example.demotest.dto.request.StationChargerTypeRequest;
import com.example.demotest.dto.response.ChargingStationResponse;
import com.example.demotest.dto.response.StationChargerTypeResponse;
import com.example.demotest.entity.ChargingStation;
import com.example.demotest.entity.StationChargerType;
import com.example.demotest.repository.ChargingStationRepository;
import com.example.demotest.repository.StationChargerTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;


@Service
@RequiredArgsConstructor //injection dependencies for constructor
@Slf4j
public class StationChargerTypeService {
    @Autowired
    private ChargingStationRepository chanChargingStationRepository;
    @Autowired
    private StationChargerTypeRepository stationChargerTypeRepository;
    @Autowired
    private final Mapper dozerMapper;

    @Transactional
    public StationChargerTypeResponse save(StationChargerTypeRequest chargingStationRequest) {
        UUID chargingStationId = chargingStationRequest.getChargingStation().getId();
        ChargingStation chanStation = chanChargingStationRepository.findById(chargingStationId)
                .orElseThrow(() -> new RuntimeException("Charging Station with id " + chargingStationId + " not found"));

        StationChargerType stationChargerType = dozerMapper.map(chargingStationRequest, StationChargerType.class);
        stationChargerType.setChargingStation(chanStation);
        StationChargerType stationChargerTypeResp = stationChargerTypeRepository.saveAndFlush(stationChargerType);
        StationChargerTypeResponse stationChargerTypeResponse = dozerMapper.map(stationChargerTypeResp, StationChargerTypeResponse.class);

        return stationChargerTypeResponse;

    }


}
