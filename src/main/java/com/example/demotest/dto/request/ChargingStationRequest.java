package com.example.demotest.dto.request;

import com.example.demotest.dto.response.StationChargerTypeResponse;
import com.example.demotest.entity.ChargerType;
import com.example.demotest.entity.Location;
import com.example.demotest.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
//@ToString
@Builder
public class ChargingStationRequest {
    private ChargerType chargerType;
    private Location location;

    /*Set<StationChargerTypeResponse> stationChargerTypes;
    private int numberOfChargingPoints;
    public ChargingStationRequest(){
        stationChargerTypes = new HashSet<>();
    }*/

    @Override
    public String toString() {
        return "ChargingStationRequest{" +
                "chargerType=" + chargerType +
                ", location=" + location +
                '}';
    }
}
