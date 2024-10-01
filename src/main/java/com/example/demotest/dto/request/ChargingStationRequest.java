package com.example.demotest.dto.request;

import com.example.demotest.dto.response.StationChargerTypeResponse;
import com.example.demotest.entity.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@ToString
@Builder
public class ChargingStationRequest {
    private String chargerType;
    private int numberOfChargingPoints;
    private String status;
    private Location location;

    Set<StationChargerTypeResponse> stationChargerTypes;


    public ChargingStationRequest(){
        stationChargerTypes = new HashSet<>();
    }

}
