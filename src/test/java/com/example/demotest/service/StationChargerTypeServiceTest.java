package com.example.demotest.service;

import com.example.demotest.dto.request.StationChargerTypeRequest;
import com.example.demotest.dto.response.ChargingStationResponse;
import com.example.demotest.dto.response.StationChargerTypeResponse;
import com.example.demotest.entity.*;
import com.example.demotest.repository.ChargingStationRepository;
import com.example.demotest.repository.StationChargerTypeRepository;
import com.example.demotest.utils.MapperUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class StationChargerTypeServiceTest {

    @Mock
    StationChargerTypeRepository chargingStationRepository;

    @Mock
    ChargingStationRepository repositoryMock;

    @InjectMocks
     StationChargerTypeService service;

    private StationChargerTypeResponse stationChargerTypeResponse;

    private StationChargerType entityResponse;

    private ChargingStation chargingStationEntityResponse;

    private UUID uuid;

    @BeforeEach
    public void setUp(){

        MockitoAnnotations.initMocks(this); //without this you will get NPE
        uuid =  UUID.randomUUID();

        entityResponse = StationChargerType.builder()
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

        chargingStationEntityResponse = ChargingStation.builder()
                .location(new Location("Habana, Cuba", 56, 100))
                .chargerType(ChargerType.DC_FAST_CHARGER)//AC, DC_FAST_CHARGER
                .stationChargerTypes(new HashSet<>())
                .id(uuid)
                .build();

    }

    @DisplayName("JUnit test for save method")
    @Test
    void save() {
        StationChargerTypeRequest stationChargerTypeRequest = StationChargerTypeRequest.builder()
                .status(Status.AVAILABLE)
                .chargingStation(ChargingStation.builder().id(uuid).build())
                .power_levels(100)
                .build();

        Mockito.when(chargingStationRepository.saveAndFlush(Mockito.any(StationChargerType.class))).thenReturn(entityResponse);

        StationChargerType stationChargerType = MapperUtils.DTOToStationChargerType(stationChargerTypeRequest);

        StationChargerType chargingStationModel =  chargingStationRepository.saveAndFlush(stationChargerType);

        StationChargerTypeResponse stationChargerTypeResponse = MapperUtils.
                convertStationChargerTypeToStationChargerTypeResponse(chargingStationModel);


        Assertions.assertThat(stationChargerTypeResponse.getId()).isEqualTo(1L);
        Assertions.assertThat(stationChargerTypeResponse.getStatus()).isEqualTo(Status.AVAILABLE.toString());
    }

    @Test
    void update() {
        StationChargerTypeRequest stationChargerTypeRequest = StationChargerTypeRequest.builder()
                .status(Status.AVAILABLE)
                .chargingStation(ChargingStation.builder().id(uuid).build())
                .power_levels(2000)
                .build();

        Mockito.when(repositoryMock.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(chargingStationEntityResponse));
        Mockito.when(chargingStationRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of( entityResponse));

        Mockito.when(chargingStationRepository.saveAndFlush(Mockito.any(StationChargerType.class))).thenReturn(entityResponse);

        StationChargerTypeResponse stationChargerTypeResponse = service.update(1L, stationChargerTypeRequest);

        Assertions.assertThat(stationChargerTypeResponse.getId()).isEqualTo(1L);
        Assertions.assertThat(stationChargerTypeResponse.getStatus()).isEqualTo(Status.AVAILABLE.toString());
    }

    @Test
    void deleteById() {

        StationChargerType stationChargerTypeRequest = StationChargerType.builder()
                .status(Status.AVAILABLE)
                .chargingStation(ChargingStation.builder().id(uuid).build())
                .power_levels(100)
                .id(1L)
                .build();


        Mockito.doNothing().when(chargingStationRepository).delete(stationChargerTypeRequest);


        chargingStationRepository.delete(stationChargerTypeRequest);
        // then
        Mockito.verify(chargingStationRepository, times(1)).delete(stationChargerTypeRequest);
        //I dont think you need to assert to confirm actual delete as you are testing mock registry. to assert somethink like below you need to return null by mocking the same call again and return the null but thats of no use
        //Assertions.assertThat(repositoryMock.findById(uuid).get()).isNull();
    }

    @Test
    void stationChargerTypeById() {

        Mockito.when(chargingStationRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of( entityResponse));

        StationChargerTypeResponse stationChargerTypeResponse = service.StationChargerTypeById(1L);

        Assertions.assertThat(stationChargerTypeResponse.getId()).isEqualTo(1L);
        Assertions.assertThat(stationChargerTypeResponse.getStatus()).isEqualTo(Status.AVAILABLE.toString());
    }

    @Test
    void findAll() {

        StationChargerType entityResponse1 = StationChargerType.builder()
                .status(Status.AVAILABLE)
                .id(2L)
                .chargingStation(ChargingStation.builder().id(uuid).build())
                .power_levels(200)
                .build();

        Mockito.when(chargingStationRepository.findAll()).thenReturn(List.of(entityResponse, entityResponse1));
        final List<StationChargerTypeResponse> result = service.findAll();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void testFindAll() {
    }
}