package org.senla.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.senla.dto.creators.SensorCreateDto;
import org.senla.dto.SensorDto;
import org.senla.entity.Sensor;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SensorMapper {
    @Mapping(source = "type.name", target = "type")
    @Mapping(source = "unit.name", target = "unit")
    SensorDto toSensorDto(Sensor sensor);
    @Mapping(source = "type", target = "type.name")
    @Mapping(source = "unit", target = "unit.name")
    Sensor toSensor(SensorCreateDto sensorDto);
    SensorDto toSensorDto(SensorCreateDto sensorDto);
}
