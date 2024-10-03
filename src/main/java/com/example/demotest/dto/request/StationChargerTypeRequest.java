package com.example.demotest.dto.request;

import com.example.demotest.entity.ChargingStation;
import com.example.demotest.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@Builder
public class StationChargerTypeRequest {
    private double power_levels;
    private Status status;
    private ChargingStation chargingStation;
}
