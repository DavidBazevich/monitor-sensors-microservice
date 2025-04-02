package org.senla.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.senla.dto.SensorDto;
import org.senla.dto.creators.SensorCreateDto;
import org.senla.dto.mapper.SensorMapper;
import org.senla.entity.Range;
import org.senla.entity.Sensor;
import org.senla.entity.Type;
import org.senla.entity.Units;
import org.senla.repository.SensorsRepository;
import org.senla.repository.TypeRepository;
import org.senla.repository.UnitRepository;
import org.senla.service.SensorService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SensorServiceTest {

    @Mock
    private SensorsRepository sensorsRepository;
    @Mock
    private TypeRepository typeRepository;
    @Mock
    private UnitRepository unitRepository;
    @Mock
    private SensorMapper sensorMapper;

    @InjectMocks
    private SensorService sensorService;

    private Sensor sensor;
    private SensorDto sensorDto;
    private SensorCreateDto sensorCreateDto;

    @BeforeEach
    void setUp() {
        Range range = new Range(2, 15);

        sensor = Sensor.builder()
                .id(1)
                .name("Barometer")
                .model("DKV12H4")
                .range(range)
                .location("kitchen")
                .description("Use plz outside")
                .build();

        sensorDto = SensorDto.builder()
                .id(1)
                .name("Barometer")
                .model("DKV12H4")
                .range(range)
                .location("kitchen")
                .description("Use plz outside")
                .type("Pressure")
                .unit("bar")
                .build();

        sensorCreateDto = SensorCreateDto.builder()
                .name("Barometer")
                .model("DKV12H4")
                .range(range)
                .location("kitchen")
                .description("Use plz outside")
                .type("Pressure")
                .unit("bar")
                .build();
    }

    @Test
    void findAllSensors() {
        when(sensorsRepository.findAll()).thenReturn(Collections.singletonList(sensor));
        when(sensorMapper.toSensorDto(sensor)).thenReturn(sensorDto);

        List<SensorDto> result = sensorService.findAllSensor();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(sensorDto, result.get(0));

        verify(sensorsRepository, times(1)).findAll();
        verify(sensorMapper, times(1)).toSensorDto(sensor);
    }

    @Test
    void saveSensor() {
        when(unitRepository.findByName(sensorCreateDto.getUnit())).thenReturn(Optional.of(Units.builder().id(1).name("bar").build()));
        when(typeRepository.findByName(sensorCreateDto.getType())).thenReturn(Optional.of(Type.builder().id(1).name("Pressure").build()));
        when(sensorsRepository.save(sensor)).thenReturn(sensor);
        when(sensorMapper.toSensorDto(sensor)).thenReturn(sensorDto);

        SensorDto result = sensorService.saveSensor(sensorCreateDto);

        assertNotNull(result);
        assertEquals(sensorDto, result);

        verify(sensorsRepository, times(1)).save(sensor);
        verify(sensorMapper, times(1)).toSensorDto(sensor);
    }

    @Test
    void updateSensor() {
        when(sensorsRepository.findById(1)).thenReturn(Optional.of(sensor));
        when(unitRepository.findByName(sensorCreateDto.getUnit())).thenReturn(Optional.of(Units.builder().id(1).name("bar").build()));
        when(typeRepository.findByName(sensorCreateDto.getType())).thenReturn(Optional.of(Type.builder().id(1).name("Pressure").build()));
        when(sensorsRepository.save(sensor)).thenReturn(sensor);
        when(sensorMapper.toSensorDto(sensor)).thenReturn(sensorDto);

        SensorDto result = sensorService.updateSensor(1, sensorCreateDto);

        assertNotNull(result);
        assertEquals(sensorDto, result);

        verify(sensorsRepository, times(1)).findById(1);
        verify(sensorsRepository, times(1)).save(sensor);
        verify(sensorMapper, times(1)).toSensorDto(sensor);
    }

    @Test
    void findSensorById() {
        when(sensorsRepository.findById(1)).thenReturn(Optional.of(sensor));
        when(sensorMapper.toSensorDto(sensor)).thenReturn(sensorDto);

        SensorDto result = sensorService.findById(1);

        assertNotNull(result);
        assertEquals(sensorDto, result);

        verify(sensorsRepository, times(1)).findById(1);
        verify(sensorMapper, times(1)).toSensorDto(sensor);
    }

    @Test
    void deleteSensorById() {
        when(sensorsRepository.findById(1)).thenReturn(Optional.of(sensor));

        sensorService.deleteSensorById(1);

        verify(sensorsRepository, times(1)).findById(1);
        verify(sensorsRepository, times(1)).delete(sensor);
    }


}
