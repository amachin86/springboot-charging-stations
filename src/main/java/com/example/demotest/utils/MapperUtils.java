package com.example.demotest.utils;

import com.example.demotest.dto.response.ChargingStationResponse;
import com.example.demotest.dto.response.StationChargerTypeResponse;
import com.example.demotest.entity.ChargingStation;
import com.example.demotest.entity.StationChargerType;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class MapperUtils {

    public static ChargingStationResponse convertChargingStationToChargingStationResponse(ChargingStation chargingStation) {

        Set<StationChargerTypeResponse> chargingStationList  = chargingStation.getStationChargerTypes().stream().map(MapperUtils::convertStationChargerTypeToStationChargerTypeResponse).collect(Collectors.toSet());
        return ChargingStationResponse.builder()
                .id(chargingStation.getId().toString())
                .stationChargerTypes(chargingStationList)
                .chargerType(chargingStation.getChargerType().toString())
                .status(chargingStation.getStatus().toString())
                .location(chargingStation.getLocation())
                .numberOfChargingPoints(chargingStationList.size())
                .build();
    }

    public static StationChargerTypeResponse convertStationChargerTypeToStationChargerTypeResponse(StationChargerType stationChargerType){

        //log.info("MAPPER {}", );

        ChargingStationResponse chargingStationResponse = ChargingStationResponse.builder()
                .numberOfChargingPoints(stationChargerType.getChargingStation().getStationChargerTypes().size())
                .location(stationChargerType.getChargingStation().getLocation())
                .chargerType(stationChargerType.getChargingStation().getChargerType().toString())
                .status(stationChargerType.getChargingStation().getStatus().toString())
                .id(stationChargerType.getChargingStation().getId().toString())
                .build();

        return StationChargerTypeResponse.builder()
                .id(stationChargerType.getId())
                .power_levels(stationChargerType.getPower_levels())
                .build();
    }


}
