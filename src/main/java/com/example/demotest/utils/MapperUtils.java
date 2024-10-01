package com.example.demotest.utils;

import com.example.demotest.dto.response.ChargingStationResponse;
import com.example.demotest.dto.response.StationChargerTypeResponse;
import com.example.demotest.entity.ChargingStation;
import com.example.demotest.entity.StationChargerType;

import java.util.Set;
import java.util.stream.Collectors;

public class MapperUtils {

    public static ChargingStationResponse convertDroneToChargingStationResponse(ChargingStation chargingStation) {

        Set<StationChargerTypeResponse> chargingStationList  = chargingStation.getStationChargerTypes().stream().map(MapperUtils::convertDroneToStationChargerTypeResponse).collect(Collectors.toSet());
        return ChargingStationResponse.builder()
                .stationChargerTypes(chargingStationList)
                .chargerType(chargingStation.getChargerType().toString())
                .status(chargingStation.getStatus().toString())
                .location(chargingStation.getLocation())
                .numberOfChargingPoints(chargingStation.getNumberOfChargingPoints())
                .build();
    }

    public static StationChargerTypeResponse convertDroneToStationChargerTypeResponse(StationChargerType stationChargerType){
        return StationChargerTypeResponse.builder()
                .id(stationChargerType.getId())
                .type(stationChargerType.getType())
                .chargingStation(stationChargerType.getChargingStation())
                .power_levels(stationChargerType.getPower_levels())
                .build();
    }
}
