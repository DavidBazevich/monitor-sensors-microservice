package org.senla.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.senla.dto.SensorDto;
import org.senla.dto.creators.SensorCreateDto;
import org.senla.service.Impl.SensorServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sensor")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class SensorController {

    private final SensorServiceImp sensorService;

    @GetMapping("/{id}")
    @Operation(summary = "Find by id")
    public ResponseEntity<SensorDto> findById(@PathVariable Integer id){
       return new ResponseEntity<>(sensorService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SensorDto>> findAllSensor(){
        return new ResponseEntity<>(sensorService.findAllSensor(), HttpStatus.OK);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<SensorDto> addSensor(@RequestBody  @Validated SensorCreateDto sensor){
        return new ResponseEntity<>(sensorService.saveSensor(sensor), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<SensorDto> updateSensor(@PathVariable Integer id,
                                              @RequestBody SensorCreateDto newSensor){
        return ResponseEntity.ok(sensorService.updateSensor(id, newSensor));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<Void> deleteSensor(@PathVariable Integer id){
        sensorService.deleteSensorById(id);
        return ResponseEntity.noContent().build();
    }

}
