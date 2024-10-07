package com.example.demotest.utils;

import com.example.demotest.dto.request.ChargingStationRequest;
import com.example.demotest.dto.request.StationChargerTypeRequest;
import com.example.demotest.dto.response.ChargingStationResponse;
import com.example.demotest.dto.response.StationChargerTypeResponse;
import com.example.demotest.entity.ChargingStation;
import com.example.demotest.entity.Location;
import com.example.demotest.entity.StationChargerType;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
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
                .numberOfChargingPoints(chargingStation.getAvailableNumberOfChargingPoints())
                .chargingCapacity(chargingStation.getChargingCapacity())
                .build();
    }

    public static StationChargerTypeResponse convertStationChargerTypeToStationChargerTypeResponse(StationChargerType stationChargerType){

            return StationChargerTypeResponse.builder()
                .id(stationChargerType.getId())
                .power_levels(stationChargerType.getPower_levels())
                .status(stationChargerType.getStatus().toString())
                .build();
    }

    public static ChargingStation DTOToChangingStationModel(ChargingStationRequest chargingStationRequest){
        Location location = Location.builder()
                .address(chargingStationRequest.getLocation().getAddress())
                .latitude(chargingStationRequest.getLocation().getLatitude())
                .longitude(chargingStationRequest.getLocation().getLongitude())
                .build();
        return ChargingStation.builder()
                .chargerType(chargingStationRequest.getChargerType())
                .location(location)
                .stationChargerTypes(new HashSet<>())
                .build();
    }

    public static StationChargerType DTOToStationChargerType(StationChargerTypeRequest stationChargerTypeRequest){
        ChargingStation chanStation =  DTOToChangingStationModel(stationChargerTypeRequest.getChargingStation());
        return StationChargerType.builder()
                .chargingStation(chanStation)
                .power_levels(stationChargerTypeRequest.getPower_levels())
                .status(stationChargerTypeRequest.getStatus())
                .build();
    }


}
