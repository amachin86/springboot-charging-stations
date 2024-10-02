package com.example.demotest.controller;

import com.example.demotest.dto.APIResponse;
import com.example.demotest.dto.request.ChargingStationRequest;
import com.example.demotest.dto.request.StationChargerTypeRequest;
import com.example.demotest.dto.response.ChargingStationResponse;
import com.example.demotest.dto.response.StationChargerTypeResponse;
import com.example.demotest.entity.ChargingStation;
import com.example.demotest.entity.StationChargerType;
import com.example.demotest.repository.ChargingStationRepository;
import com.example.demotest.repository.StationChargerTypeRepository;
import com.example.demotest.service.ChargingStationService;
import com.example.demotest.service.StationChargerTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/type/charging-stations")
public class StationChargeTypeController {

    @Autowired
    private StationChargerTypeService service;
    @Autowired
    private StationChargerTypeRepository repository;

    @GetMapping
    public ResponseEntity<APIResponse> getAllStationChargerTypes() {

        List<StationChargerTypeResponse> response = service.findAll();

        APIResponse<List<StationChargerTypeResponse>> responseDTO = APIResponse.<List<StationChargerTypeResponse>>builder()
                .status("Success")
                .results(response)
                .build();


        return ResponseEntity.ok(responseDTO);

    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getStationChargerTypeById(@PathVariable Long id) {
        StationChargerTypeResponse StationChargerType = service.StationChargerTypeById(id);


       if (StationChargerType == null) {
            return ResponseEntity.notFound().build();
        }
        APIResponse<StationChargerTypeResponse> responseDTO = APIResponse.<StationChargerTypeResponse>builder()
                .status("Success")
                .results(StationChargerType)
                .build();
        log.info("StationChargerTypeController:getStationChargerTypeById by Drone id {} response {}", id, responseDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping
    public ResponseEntity<APIResponse> createStationChargerType(@RequestBody StationChargerType StationChargerType) {

        //StationChargerTypeResponse StationChargerTypeResponse = service.save(StationChargerType);
        StationChargerType StationChargerTypeResponse = repository.saveAndFlush(StationChargerType);
        log.info("StationChargerTypeController:createStationChargerType request body {}", StationChargerType);

        APIResponse<StationChargerType> responseDTO = APIResponse.<StationChargerType>builder()
                .status("Success")
                .results(StationChargerTypeResponse)
                .build();

        log.info("StationChargerTypeController:createStationChargerType response {}", responseDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse> updateStationChargerType(@PathVariable Long id, @RequestBody StationChargerTypeRequest StationChargerType) {

        StationChargerTypeResponse StationChargerTypeResponse = service.update(id, StationChargerType);
        if (StationChargerTypeResponse == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            //return ResponseEntity.notFound().build();
        }
        log.info("StationChargerTypeController:updateStationChargerType request body {}", StationChargerType);

        APIResponse<StationChargerTypeResponse> responseDTO = APIResponse.<StationChargerTypeResponse>builder()
                .status("Success")
                .results(StationChargerTypeResponse)
                .build();

        log.info("StationChargerTypeController:updateStationChargerType response {}", responseDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteStationChargerType(@PathVariable Long id) {

        log.info("StationChargerTypeController:deleteStationChargerType Drone id {} response {}", id);

        StationChargerTypeResponse cahnStationResponse = service.deleteById(id);
        if (cahnStationResponse == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        APIResponse<StationChargerTypeResponse> responseDTO = APIResponse.<StationChargerTypeResponse>builder()
                .status("Success")
                .results(cahnStationResponse)
                .build();

        log.info("StationChargerTypeController:deleteStationChargerType Charging Station id {} response {}", id, responseDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
