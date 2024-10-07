package com.example.demotest.dto.request;

import com.example.demotest.dto.response.StationChargerTypeResponse;
import com.example.demotest.entity.ChargerType;
import com.example.demotest.entity.Location;
import com.example.demotest.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Data
@AllArgsConstructor
//@ToString
@Builder
public class ChargingStationRequest {
    private UUID id;
    private ChargerType chargerType;

    @Valid
    private LocationDTO location;

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
