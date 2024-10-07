package com.example.demotest.utils;

import com.example.demotest.dto.request.ChargingStationRequest;
import com.example.demotest.dto.request.LocationDTO;
import com.example.demotest.dto.request.StationChargerTypeRequest;
import com.example.demotest.dto.response.ChargingStationResponse;
import com.example.demotest.dto.response.StationChargerTypeResponse;
import com.example.demotest.entity.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MapperUtilsTest {
    private StationChargerTypeResponse stationChargerTypeResponse;

    private StationChargerType stationChargerTypeEntity;

    private UUID uuid;

    @BeforeEach
    public void setUp(){

        MockitoAnnotations.initMocks(this); //without this you will get NPE
        uuid =  UUID.randomUUID();

        stationChargerTypeEntity = StationChargerType.builder()
                .status(Status.AVAILABLE)
                .id(1L)
                .chargingStation(ChargingStation.builder().id(uuid).build())
                .power_levels(100)
                .build();

        stationChargerTypeResponse = StationChargerTypeResponse.builder()
                .status(Status.AVAILABLE.toString())
                .id(1L)
                .power_levels(100)
                .build();
    }

    @Test
    void convertChargingStationToChargingStationResponse() {
        ChargingStation model = ChargingStation.builder()
                .location(new Location("Habana, Cuba", 56, 100))
                .chargerType(ChargerType.DC_FAST_CHARGER)//AC, DC_FAST_CHARGER
                .stationChargerTypes(new HashSet<>())
                .id(uuid)
                .build();

        ChargingStationResponse result  = MapperUtils.convertChargingStationToChargingStationResponse(model);

        //ChargingStationResponse result = chargingStationService.save(_chargingStationRequest);
        Assertions.assertThat(result.getChargingCapacity()).isEqualTo(0);
        Assertions.assertThat(result.getStatus()).isEqualTo(Status.IN_USE.toString());

    }

    @Test
    void convertStationChargerTypeToStationChargerTypeResponse() {

        StationChargerTypeResponse stationChargerTypeResponse = MapperUtils.
                convertStationChargerTypeToStationChargerTypeResponse(stationChargerTypeEntity);


        Assertions.assertThat(stationChargerTypeResponse.getId()).isEqualTo(1L);
        Assertions.assertThat(stationChargerTypeResponse.getStatus()).isEqualTo(Status.AVAILABLE.toString());

    }

    @Test
    void DTOToChangingStationModel() {

        ChargingStationRequest chargingStationRequest = ChargingStationRequest.builder()
                .location(new LocationDTO("Habana, Cuba", 56, 100))
                .chargerType(ChargerType.DC_FAST_CHARGER)
                .build();

        ChargingStation entity = MapperUtils.DTOToChangingStationModel(chargingStationRequest);
        Assertions.assertThat(entity.getLocation().getLongitude()).isEqualTo(100);
        Assertions.assertThat(entity.getChargerType()).isEqualTo(ChargerType.DC_FAST_CHARGER);
    }

    @Test
    void DTOToStationChargerType() {

        StationChargerTypeRequest stationChargerTypeRequest = StationChargerTypeRequest.builder()
                .status(Status.AVAILABLE)
                .chargingStation(ChargingStationRequest.builder().id(uuid).build())
                .power_levels(100)
                .build();

        StationChargerType stationChargerType = MapperUtils.DTOToStationChargerType(stationChargerTypeRequest);

        Assertions.assertThat(stationChargerType.getPower_levels()).isEqualTo(100);
        Assertions.assertThat(stationChargerType.getStatus().toString()).isEqualTo(Status.AVAILABLE.toString());
    }
}