package org.senla.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.senla.dto.UnitDto;
import org.senla.dto.creators.UnitCreateDto;
import org.senla.entity.Units;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UnitMapper {
    @Mapping(source = "name", target = "name")
    UnitDto toUnitDto(Units unit);
    @Mapping(source = "name", target = "name")
    Units toUnit(UnitCreateDto unitCreateDto);
}
