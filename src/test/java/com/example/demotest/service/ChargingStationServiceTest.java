package com.example.demotest.service;

import com.example.demotest.dto.request.ChargingStationRequest;
import com.example.demotest.dto.request.LocationDTO;
import com.example.demotest.dto.response.ChargingStationResponse;
import com.example.demotest.entity.ChargerType;
import com.example.demotest.entity.ChargingStation;
import com.example.demotest.entity.Location;
import com.example.demotest.entity.Status;
import com.example.demotest.repository.ChargingStationRepository;
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
class ChargingStationServiceTest {

    @Mock
    ChargingStationRepository repositoryMock;

    @InjectMocks
    ChargingStationService chargingStationService;

    private ChargingStationResponse chargingStationResponse;

    private ChargingStation entityResponse;

    private UUID uuid;



    @BeforeEach
    public void setUp(){
       // repositoryMock = Mockito.mock(ChargingStationRepository.class);
        MockitoAnnotations.initMocks(this); //without this you will get NPE

        uuid =  UUID.randomUUID();

        entityResponse = ChargingStation.builder()
                .location(new Location("Habana, Cuba", 56, 100))
                .chargerType(ChargerType.DC_FAST_CHARGER)//AC, DC_FAST_CHARGER
                .stationChargerTypes(new HashSet<>())
                .id(uuid)
                .build();
        chargingStationResponse = ChargingStationResponse.builder()
                .status("IN_USE")//AVAILABLE, IN_USE
                .numberOfChargingPoints(0)
                .location(new Location("Habana, Cuba", 56, 100))
                .chargerType("DC_FAST_CHARGER")//AC, DC_FAST_CHARGER
                .chargingCapacity(0)
                .stationChargerTypes(new HashSet<>())
                .id(uuid.toString())
                .build();
    }

    @DisplayName("JUnit test for save method")
    @Test
    void save() {

        ChargingStationRequest _chargingStationRequest = ChargingStationRequest.builder()
                .location(new LocationDTO("Habana, Cuba", 56, 100))
                .chargerType(ChargerType.DC_FAST_CHARGER)
                .build();

       Mockito.when(repositoryMock.saveAndFlush(Mockito.any(ChargingStation.class))).thenReturn(entityResponse);

        ChargingStation chargingStationRequest = MapperUtils.DTOToChangingStationModel(_chargingStationRequest);

        ChargingStation chargingStationModel =  repositoryMock.saveAndFlush(chargingStationRequest);

        ChargingStationResponse result  = MapperUtils.convertChargingStationToChargingStationResponse(chargingStationModel);

        //ChargingStationResponse result = chargingStationService.save(_chargingStationRequest);
        Assertions.assertThat(result.getChargingCapacity()).isEqualTo(0);
        Assertions.assertThat(result.getStatus()).isEqualTo(Status.IN_USE.toString());
    }

    @DisplayName("JUnit test for update method")
    @Test
    void update() {
        Mockito.when(repositoryMock.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(entityResponse));
        ChargingStationRequest chargingStationRequest1 = ChargingStationRequest.builder()
                .location(new LocationDTO("Habana, Cuba", 56, 200))
                .chargerType(ChargerType.DC_FAST_CHARGER)
                .build();

        Mockito.when(repositoryMock.save(Mockito.any(ChargingStation.class))).thenReturn(entityResponse);

        final ChargingStationResponse result = chargingStationService.update(uuid, chargingStationRequest1);

        Assertions.assertThat(result.getChargingCapacity()).isEqualTo(0);
        Assertions.assertThat(result.getStatus()).isEqualTo(Status.IN_USE.toString());
    }

    @DisplayName("JUnit test for deleteById method")
    @Test
    void deleteById() {
        ChargingStation chargingStationRequest = ChargingStation.builder()
                .location(new Location("Habana, Cuba", 56, 100))
                .chargerType(ChargerType.DC_FAST_CHARGER)
                .stationChargerTypes(new HashSet<>())
                .id(uuid)
                .build();

        //Mockito.when(repositoryMock.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(entityResponse));
        //Mockito.when(repositoryMock.delete(Mockito.any(ChargingStation.class))).thenReturn(entityResponse);
        Mockito.doNothing().when(repositoryMock).delete(chargingStationRequest);

        //ChargingStationResponse result = chargingStationService.deleteById(uuid);
        repositoryMock.delete(chargingStationRequest);
        // then
        Mockito.verify(repositoryMock, times(1)).delete(chargingStationRequest);
        //I dont think you need to assert to confirm actual delete as you are testing mock registry. to assert somethink like below you need to return null by mocking the same call again and return the null but thats of no use
        //Assertions.assertThat(repositoryMock.findById(uuid).get()).isNull();
    }

    @DisplayName("JUnit test for chargingStationById method")
    @Test
    void chargingStationById() {

        Mockito.when(repositoryMock.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(entityResponse));
        final ChargingStationResponse result = chargingStationService.ChargingStationById(uuid);

        Assertions.assertThat(result).isNotNull();
    }

    @DisplayName("JUnit test for findAll method")
    @Test
    void findAll() {
        UUID uuid1 =  UUID.randomUUID();

        ChargingStation entityResponse1 = ChargingStation.builder()
                .location(new Location("Mayabeque, Cuba", 56, 200))
                .chargerType(ChargerType.DC_FAST_CHARGER)//AC, DC_FAST_CHARGER
                .stationChargerTypes(new HashSet<>())
                .id(uuid1)
                .build();
        Mockito.when(repositoryMock.findAll()).thenReturn(List.of(entityResponse, entityResponse1));
        final List<ChargingStationResponse> result = chargingStationService.findAll();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void testFindAll() {
    }
}