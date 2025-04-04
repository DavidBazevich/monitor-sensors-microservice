package org.senla.statisticsservice.dto;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.senla.statisticsservice.entity.Sensor;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StatisticMapper {
    Sensor toSensor(SensorStatisticSender sensorStatisticSender);
}
