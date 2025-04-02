package org.senla.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.senla.dto.TypeDto;
import org.senla.dto.creators.TypeCreateDto;
import org.senla.dto.mapper.TypeMapper;
import org.senla.entity.Type;
import org.senla.exception.ResourceNotFoundException;
import org.senla.repository.TypeRepository;
import org.senla.service.TypeService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TypeServiceTest {

    @Mock
    private TypeRepository typeRepository;
    @Mock
    private TypeMapper typeMapper;

    @InjectMocks
    private TypeService typeService;

    private Type type;
    private TypeDto typeDto;
    private TypeCreateDto typeCreateDto;

    @BeforeEach
    void setUp() {
        type = new Type();
        type.setId(1);
        type.setName("Pressure");

        typeDto = new TypeDto(1, "Pressure");

        typeCreateDto = new TypeCreateDto("Pressure");
    }

    @Test
    void findAllTypes() {
        when(typeRepository.findAll()).thenReturn(Collections.singletonList(type));
        when(typeMapper.toTypeDto(type)).thenReturn(typeDto);

        List<TypeDto> result = typeService.findAllTypes();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(typeDto, result.get(0));

        verify(typeRepository, times(1)).findAll();
        verify(typeMapper, times(1)).toTypeDto(type);
    }

    @Test
    void saveType() {
        when(typeMapper.toType(typeCreateDto)).thenReturn(type);
        when(typeRepository.save(type)).thenReturn(type);
        when(typeMapper.toTypeDto(type)).thenReturn(typeDto);

        TypeDto result = typeService.saveType(typeCreateDto);

        assertNotNull(result);
        assertEquals(typeDto, result);

        verify(typeMapper, times(1)).toType(typeCreateDto);
        verify(typeRepository, times(1)).save(type);
        verify(typeMapper, times(1)).toTypeDto(type);
    }

    @Test
    void updateType() {
        when(typeRepository.findById(1)).thenReturn(Optional.of(type));
        when(typeRepository.save(type)).thenReturn(type);
        when(typeMapper.toTypeDto(type)).thenReturn(typeDto);

        TypeDto result = typeService.updateType(1, typeCreateDto);

        assertNotNull(result);
        assertEquals(typeDto, result);

        verify(typeRepository, times(1)).findById(1);
        verify(typeRepository, times(1)).save(type);
        verify(typeMapper, times(1)).toTypeDto(type);
    }

    @Test
    void findById() {
        when(typeRepository.findById(1)).thenReturn(Optional.of(type));
        when(typeMapper.toTypeDto(type)).thenReturn(typeDto);

        TypeDto result = typeService.findById(1);

        assertNotNull(result);
        assertEquals(typeDto, result);

        verify(typeRepository, times(1)).findById(1);
        verify(typeMapper, times(1)).toTypeDto(type);
    }

    @Test
    void deleteTypeById() {
        when(typeRepository.findById(1)).thenReturn(Optional.of(type));

        typeService.deleteTypeById(1);

        verify(typeRepository, times(1)).findById(1);
        verify(typeRepository, times(1)).delete(type);
    }

    @Nested
    class TypeResourceNotFoundExceptionTests{
        @Test
        void updateType() {
            when(typeRepository.findById(1)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> typeService.updateType(1, typeCreateDto));

            verify(typeRepository, times(1)).findById(1);
        }

        @Test
        void findById() {
            when(typeRepository.findById(1)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> typeService.findById(1));

            verify(typeRepository, times(1)).findById(1);
        }
        @Test
        void deleteTypeById() {
            when(typeRepository.findById(1)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> typeService.deleteTypeById(1));

            verify(typeRepository, times(1)).findById(1);
        }
    }
}
