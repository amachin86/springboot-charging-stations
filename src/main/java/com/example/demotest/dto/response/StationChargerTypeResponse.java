package com.example.demotest.dto.response;

import com.example.demotest.entity.ChargerType;
import com.example.demotest.entity.ChargingStation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;


@Data
@AllArgsConstructor
@ToString
@Builder
public class StationChargerTypeResponse {

    private Long id;
    private double power_levels;
    private String status;
    //private ChargingStationResponse chargingStation;
}
