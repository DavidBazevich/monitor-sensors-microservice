package org.senla.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.senla.dto.TypeDto;
import org.senla.dto.creators.TypeCreateDto;
import org.senla.entity.Type;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TypeMapper {
    @Mapping(source = "name", target = "name")
    TypeDto toTypeDto(Type type);
    @Mapping(source = "name", target = "name")
    @Mapping(target = "sensorsList", ignore = true)
    @Mapping(target = "id", ignore = true)
    Type toType(TypeCreateDto typeCreateDto);
}
