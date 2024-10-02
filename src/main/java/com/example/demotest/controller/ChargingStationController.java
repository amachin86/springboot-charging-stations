package com.example.demotest.controller;

import com.example.demotest.dto.APIResponse;
import com.example.demotest.dto.request.ChargingStationRequest;
import com.example.demotest.dto.response.ChargingStationResponse;
import com.example.demotest.entity.ChargingStation;
import com.example.demotest.repository.ChargingStationRepository;
import com.example.demotest.service.ChargingStationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<APIResponse> getAllChargingStations() {

        List<ChargingStationResponse> response = service.findAll();

        APIResponse<List<ChargingStationResponse>> responseDTO = APIResponse.<List<ChargingStationResponse>>builder()
                .status("Success")
                .results(response)
                .build();


        return ResponseEntity.ok(responseDTO);

    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getChargingStationById(@PathVariable UUID id) {
        ChargingStationResponse chargingStation = service.ChargingStationById(id);


       if (chargingStation == null) {
            return ResponseEntity.notFound().build();
        }
        APIResponse<ChargingStationResponse> responseDTO = APIResponse.<ChargingStationResponse>builder()
                .status("Success")
                .results(chargingStation)
                .build();
        log.info("ChargingStationController:getChargingStationById by Drone id {} response {}", id, responseDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping
    public ResponseEntity<APIResponse> createChargingStation(@RequestBody ChargingStation chargingStation) {

        //ChargingStationResponse chargingStationResponse = service.save(chargingStation);
        ChargingStation chargingStationResponse = repository.saveAndFlush(chargingStation);
        log.info("ChargingStationController:createChargingStation request body {}", chargingStation);

        APIResponse<ChargingStation> responseDTO = APIResponse.<ChargingStation>builder()
                .status("Success")
                .results(chargingStationResponse)
                .build();

        log.info("ChargingStationController:createChargingStation response {}", responseDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse> updateChargingStation(@PathVariable UUID id, @RequestBody ChargingStationRequest chargingStation) {

        ChargingStationResponse chargingStationResponse = service.update(id, chargingStation);
        if (chargingStationResponse == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            //return ResponseEntity.notFound().build();
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
    public ResponseEntity<APIResponse> deleteChargingStation(@PathVariable UUID id) {

        log.info("ChargingStationController:deleteChargingStation Drone id {} response {}", id);

        ChargingStationResponse cahnStationResponse = service.deleteById(id);
        if (cahnStationResponse == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        APIResponse<ChargingStationResponse> responseDTO = APIResponse.<ChargingStationResponse>builder()
                .status("Success Delete")
                .results(cahnStationResponse)
                .build();

        log.info("ChargingStationController:deleteChargingStation Charging Station id {} response {}", id, responseDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
