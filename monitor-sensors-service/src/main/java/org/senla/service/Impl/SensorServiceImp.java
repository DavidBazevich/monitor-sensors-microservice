package org.senla.service.Impl;

import org.senla.dto.creators.SensorCreateDto;
import org.senla.dto.SensorDto;

import java.util.List;

public interface SensorServiceImp {
    List<SensorDto> findAllSensor();
    SensorDto saveSensor(SensorCreateDto sensor);
    SensorDto updateSensor(Integer id, SensorCreateDto sensor);
    SensorDto findById(Integer id);
    void deleteSensorById(Integer id);
}
