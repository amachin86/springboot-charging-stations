package com.example.demotest.controller;

import com.example.demotest.dto.APIResponse;
import com.example.demotest.dto.request.ChargingStationRequest;
import com.example.demotest.dto.response.ChargingStationResponse;
import com.example.demotest.entity.ChargingStation;
import com.example.demotest.service.ChargingStationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/charging-stations")
public class ChargingStationController {

    @Autowired
    private ChargingStationService service;

    @GetMapping
    public List<ChargingStation> getAllChargingStations() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChargingStation> getChargingStationById(@PathVariable UUID id) {
        Optional<ChargingStation> chargingStation = service.findById(id);
        return chargingStation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<APIResponse> createChargingStation(@RequestBody ChargingStationRequest chargingStation) {

        ChargingStationResponse chargingStationResponse = service.save(chargingStation);
        log.info("ChargingStationController:createChargingStation request body {}", chargingStation);

        APIResponse<ChargingStationResponse> responseDTO = APIResponse.<ChargingStationResponse>builder()
                .status("Success")
                .results(chargingStationResponse)
                .build();

        log.info("ChargingStationController:createChargingStation response {}", responseDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse> updateChargingStation(@PathVariable UUID id, @RequestBody ChargingStationRequest chargingStation) {

        ChargingStationResponse chargingStationResponse = service.update(id, chargingStation);

        log.info("ChargingStationController:updateChargingStation request body {}", chargingStation);

        APIResponse<ChargingStationResponse> responseDTO = APIResponse.<ChargingStationResponse>builder()
                .status("Success")
                .results(chargingStationResponse)
                .build();

        log.info("ChargingStationController:updateChargingStation response {}", responseDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteChargingStation(@PathVariable UUID id) {

        log.info("ChargingStationController:deleteChargingStation Drone id {} response {}", id);

        ChargingStationResponse cahnStationResponse = service.deleteById(id);
        if (cahnStationResponse == null) {
            return ResponseEntity.notFound().build();
        }
        APIResponse<ChargingStationResponse> responseDTO = APIResponse.<ChargingStationResponse>builder()
                .status("Success")
                .results(cahnStationResponse)
                .build();
        log.info("ChargingStationController:deleteChargingStation Charging Station id {} response {}", id, responseDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
