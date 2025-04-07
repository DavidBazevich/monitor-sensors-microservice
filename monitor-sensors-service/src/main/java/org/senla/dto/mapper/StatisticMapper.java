package org.senla.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.senla.dto.SensorDto;
import org.senla.dto.SensorStatisticSender;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StatisticMapper {
    SensorStatisticSender toSensorStatisticSenderList(SensorDto sensorDto);
}
