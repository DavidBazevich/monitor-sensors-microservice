package org.senla.service.Impl;

import org.senla.dto.creators.UnitCreateDto;
import org.senla.dto.UnitDto;

import java.util.List;

public interface UnitServiceImp {
    List<UnitDto> findAllUnits();
    UnitDto saveUnit(UnitCreateDto type);
    UnitDto updateUnit(Integer id, UnitCreateDto newUnit);
    UnitDto findById(Integer id);
    void deleteUnitById(Integer id);
}
