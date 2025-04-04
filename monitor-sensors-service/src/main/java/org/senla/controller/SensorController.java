package org.senla.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.senla.dto.SensorDto;
import org.senla.dto.SensorStatisticSender;
import org.senla.dto.creators.SensorCreateDto;
import org.senla.dto.mapper.StatisticMapper;
import org.senla.service.Impl.SensorServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sensor")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@EnableScheduling
public class SensorController {

    private static final String TOPIC_NAME = "statistics";

    private final StatisticMapper statisticMapper;
    private final SensorServiceImp sensorService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @GetMapping("/{id}")
    @Operation(summary = "Find by id")
    public ResponseEntity<SensorDto> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(sensorService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    //@Scheduled(cron = "0 0 2 * * ?")
    @Scheduled(cron = "0 */1 * * * *")
    public ResponseEntity<List<SensorDto>> findAllSensor() throws JsonProcessingException {
        kafkaTemplate.send(TOPIC_NAME, sendMessage());
        return new ResponseEntity<>(sensorService.findAllSensor(), HttpStatus.OK);
    }

    private String sendMessage() throws JsonProcessingException {
        List<SensorStatisticSender> sensorStatisticSenders = statisticMapper.toSensorStatisticSenderList(sensorService.findAllSensor());
        return objectMapper.writeValueAsString(sensorStatisticSenders);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<SensorDto> addSensor(@RequestBody @Validated SensorCreateDto sensor) {
        return new ResponseEntity<>(sensorService.saveSensor(sensor), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<SensorDto> updateSensor(@PathVariable Integer id,
                                                  @RequestBody SensorCreateDto newSensor) {
        return ResponseEntity.ok(sensorService.updateSensor(id, newSensor));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<Void> deleteSensor(@PathVariable Integer id) {
        sensorService.deleteSensorById(id);
        return ResponseEntity.noContent().build();
    }

}
