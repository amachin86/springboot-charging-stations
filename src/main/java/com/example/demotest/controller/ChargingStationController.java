package com.example.demotest.controller;

import com.example.demotest.dto.APIResponse;
import com.example.demotest.dto.request.ChargingStationRequest;
import com.example.demotest.dto.response.ChargingStationResponse;
import com.example.demotest.repository.ChargingStationRepository;
import com.example.demotest.service.ChargingStationService;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/charging-stations")
public class ChargingStationController {

    @Autowired
    private ChargingStationService service;
    @Autowired
    private ChargingStationRepository repository;

    @Cacheable("chargingStation")
    @GetMapping
    public ResponseEntity<APIResponse> getAllChargingStations() {

        List<ChargingStationResponse> response = service.findAll();

        APIResponse<List<ChargingStationResponse>> responseDTO = APIResponse.<List<ChargingStationResponse>>builder()
                .status("Success")
                .results(response)
                .build();


        return ResponseEntity.ok(responseDTO);

    }

    @Cacheable("chargingStation")
    @GetMapping("/{id}")
    public ResponseEntity<?> getChargingStationById(@PathVariable UUID id) {
        ChargingStationResponse chargingStation = service.ChargingStationById(id);


       if (chargingStation == null) {
            return new ResponseEntity<>("No Changing Station Found,", HttpStatus.NOT_FOUND);
        }
        APIResponse<ChargingStationResponse> responseDTO = APIResponse.<ChargingStationResponse>builder()
                .status("Success")
                .results(chargingStation)
                .build();
        log.info("ChargingStationController:getChargingStationById by Charging Station id {} response {}", id, responseDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @Cacheable("stationStatus")
    @GetMapping("/{id}/status")
    public ResponseEntity<?> getStatus(@PathVariable UUID id) {

        ChargingStationResponse chargingStation = service.ChargingStationById(id);

        if (chargingStation == null) {
            return new ResponseEntity<>("No Changing Station Found,", HttpStatus.NOT_FOUND);
        }
        APIResponse<String> responseDTO = APIResponse.<String>builder()
                .status("Success")
                .results(chargingStation.getStatus())
                .build();
        log.info("ChargingStationController:getStatus id {} response {}", id, responseDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping
    public ResponseEntity<APIResponse> createChargingStation(@RequestBody @Valid @NotNull ChargingStationRequest chargingStation) {

        ChargingStationResponse chargingStationResponse = service.createChangingStation(chargingStation);
        //ChargingStation chargingStationResponse = repository.saveAndFlush(chargingStation);
        log.info("ChargingStationController:createChargingStation request body {}", chargingStation);

        APIResponse<ChargingStationResponse> responseDTO = APIResponse.<ChargingStationResponse>builder()
                .status("Success")
                .results(chargingStationResponse)
                .build();

        log.info("ChargingStationController:createChargingStation response {}", responseDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateChargingStation(@PathVariable UUID id, @RequestBody @Valid @NotNull ChargingStationRequest chargingStation) {

        ChargingStationResponse chargingStationResponse = service.update(id, chargingStation);
        if (chargingStationResponse == null) {
            return new ResponseEntity<>("No Changing Station Found,", HttpStatus.NOT_FOUND);

        }
        log.info("ChargingStationController:updateChargingStation request body {}", chargingStation);

        APIResponse<ChargingStationResponse> responseDTO = APIResponse.<ChargingStationResponse>builder()
                .status("Success Update")
                .results(chargingStationResponse)
                .build();

        log.info("ChargingStationController:updateChargingStation response {}", responseDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChargingStation(@PathVariable UUID id) {

        log.info("ChargingStationController:deleteChargingStation Charging Station id {} response {}", id);

        ChargingStationResponse chargingStationResponse = service.deleteById(id);
        if (chargingStationResponse == null) {
            return new ResponseEntity<>("No Changing Station Found,", HttpStatus.NOT_FOUND);
        }
        APIResponse<ChargingStationResponse> responseDTO = APIResponse.<ChargingStationResponse>builder()
                .status("Success Delete")
                .results(chargingStationResponse)
                .build();

        log.info("ChargingStationController:deleteChargingStation Charging Station id {} response {}", id, responseDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
