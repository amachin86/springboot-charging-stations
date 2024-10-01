package com.example.demotest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Data
@AllArgsConstructor
@ToString
@Builder
public class ChargingStationResponse {
    private UUID id;
    private String chargerType;
    private int numberOfChargingPoints;
    private String status;

    Set<StationChargerTypeResponse> stationChargerTypes;


    public ChargingStationResponse(){
        stationChargerTypes = new HashSet<>();
    }
}
