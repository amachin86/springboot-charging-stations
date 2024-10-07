package com.example.demotest.dto.request;

import com.example.demotest.entity.ChargingStation;
import com.example.demotest.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@ToString
@Builder
public class StationChargerTypeRequest {

    @Positive(message = "The value of the power_levels must be positive")
    private double power_levels;
    private Status status;
    private ChargingStationRequest chargingStation;
}
