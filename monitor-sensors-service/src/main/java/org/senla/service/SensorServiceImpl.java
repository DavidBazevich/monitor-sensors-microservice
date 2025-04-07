package org.senla.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.dto.SensorDto;
import org.senla.dto.SensorStatisticSender;
import org.senla.dto.creators.SensorCreateDto;
import org.senla.dto.mapper.SensorMapper;
import org.senla.dto.mapper.StatisticMapper;
import org.senla.entity.Sensor;
import org.senla.entity.Type;
import org.senla.entity.Units;
import org.senla.exception.ResourceNotFoundException;
import org.senla.repository.SensorsRepository;
import org.senla.repository.TypeRepository;
import org.senla.repository.UnitRepository;
import org.senla.service.Impl.SensorService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SensorServiceImpl implements SensorService {

    private static final String TOPIC_NAME = "statistics";

    private final SensorsRepository sensorsRepository;
    private final TypeRepository typeRepository;
    private final UnitRepository unitRepository;
    private final SensorMapper sensorMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final StatisticMapper statisticMapper;

    @Override
    public List<SensorDto> findAllSensor() {
        return sensorsRepository.findAll()
                .stream()
                .map(sensorMapper::toSensorDto)
                .toList();
    }

    @Override
    @Transactional
    public SensorDto saveSensor(SensorCreateDto sensorCreatedDto) {
       return Optional.of(updateSensorFields(new Sensor(), sensorCreatedDto))
               .map(sensorsRepository::save)
               .map(sensorMapper::toSensorDto)
               .orElseThrow(() -> new ResourceNotFoundException("Incorrect data"));
    }

    @Override
    @Transactional
    public SensorDto updateSensor(Integer id, SensorCreateDto newCreatedSensor){
        Sensor newSensor = updateSensorFields(getSensorById(id), newCreatedSensor);
        sensorsRepository.save(newSensor);
        return sensorMapper.toSensorDto(newSensor);
    }

    @Override
    public SensorDto findById(Integer id) {
        Sensor sensor = getSensorById(id);
        return sensorMapper.toSensorDto(sensor);
    }

    @Override
    @Transactional
    public void deleteSensorById(Integer id) {
        Sensor sensor = getSensorById(id);
        sensorsRepository.delete(sensor);
    }

    private Sensor getSensorById(Integer id) {
        return sensorsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor not found with id: " + id));
    }

    private Sensor updateSensorFields(Sensor sensor, SensorCreateDto newSensor) {
        Type type = findTypeByName(newSensor);
        Units unit = findUnitByName(newSensor);
        sensor.setName(newSensor.getName());
        sensor.setModel(newSensor.getModel());
        sensor.setRange(newSensor.getRange());
        sensor.setType(type);
        sensor.setUnit(unit);
        sensor.setLocation(newSensor.getLocation());
        sensor.setDescription(newSensor.getDescription());
        return sensor;
    }

    private Units findUnitByName(SensorCreateDto newSensor){
        if(newSensor.getUnit() != null){
            return unitRepository.findByName(newSensor.getUnit())
                    .orElseThrow(() -> new ResourceNotFoundException("Unit not found with name: " + newSensor.getUnit()));
        }
        return null;
    }

    private Type findTypeByName(SensorCreateDto newSensor) {
        return typeRepository.findByName(newSensor.getType())
                .orElseThrow(() -> new ResourceNotFoundException("Type not found with name: " + newSensor.getType()));
    }

    @Scheduled(cron = "0 */1 * * * *")
    public void sendMonitorStatistic() {
        kafkaTemplate.send(TOPIC_NAME, sendMessage());
    }

    private String sendMessage()  {
        List<SensorStatisticSender> send = sensorsRepository.findAll()
                .stream()
                .map(sensorMapper::toSensorDto)
                .map(statisticMapper::toSensorStatisticSenderList)
                .toList();
        try {
            return objectMapper.writeValueAsString(send);
        } catch (JsonProcessingException e){
            log.error("Serialization error of the object in the String: ".concat(e.getMessage()));
        }
        return null;
    }
}
