package org.senla.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.senla.dto.UnitDto;
import org.senla.dto.creators.UnitCreateDto;
import org.senla.dto.mapper.UnitMapper;
import org.senla.entity.Units;
import org.senla.exception.ResourceNotFoundException;
import org.senla.repository.UnitRepository;
import org.senla.service.UnitServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тесты для UnitService")
public class UnitServiceImplTest {

    @Mock
    private UnitRepository unitRepository;
    @Mock
    private UnitMapper unitMapper;

    @InjectMocks
    private UnitServiceImpl unitServiceImpl;

    private Units unit;
    private UnitDto unitDto;
    private UnitCreateDto unitCreateDto;

    @BeforeEach
    void setUp() {
        unit = new Units();
        unit.setId(1);
        unit.setName("bar");

        unitDto = new UnitDto(1, "bar");

        unitCreateDto = new UnitCreateDto("bar");
    }

    @Test
    void findAllTypes() {
        when(unitRepository.findAll()).thenReturn(Collections.singletonList(unit));
        when(unitMapper.toUnitDto(unit)).thenReturn(unitDto);

        List<UnitDto> result = unitServiceImpl.findAllUnits();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(unitDto, result.get(0));

        verify(unitRepository, times(1)).findAll();
        verify(unitMapper, times(1)).toUnitDto(unit);
    }

    @Test
    void saveType() {
        when(unitMapper.toUnit(unitCreateDto)).thenReturn(unit);
        when(unitRepository.save(unit)).thenReturn(unit);
        when(unitMapper.toUnitDto(unit)).thenReturn(unitDto);

        UnitDto result = unitServiceImpl.saveUnit(unitCreateDto);

        assertNotNull(result);
        assertEquals(unitDto, result);

        verify(unitMapper, times(1)).toUnit(unitCreateDto);
        verify(unitRepository, times(1)).save(unit);
        verify(unitMapper, times(1)).toUnitDto(unit);
    }

    @Test
    void updateType() {
        when(unitRepository.findById(1)).thenReturn(Optional.of(unit));
        when(unitRepository.save(unit)).thenReturn(unit);
        when(unitMapper.toUnitDto(unit)).thenReturn(unitDto);

        UnitDto result = unitServiceImpl.updateUnit(1, unitCreateDto);

        assertNotNull(result);
        assertEquals(unitDto, result);

        verify(unitRepository, times(1)).findById(1);
        verify(unitRepository, times(1)).save(unit);
        verify(unitMapper, times(1)).toUnitDto(unit);
    }

    @Test
    void findById() {
        when(unitRepository.findById(1)).thenReturn(Optional.of(unit));
        when(unitMapper.toUnitDto(unit)).thenReturn(unitDto);

        UnitDto result = unitServiceImpl.findById(1);

        assertNotNull(result);
        assertEquals(unitDto, result);

        verify(unitRepository, times(1)).findById(1);
        verify(unitMapper, times(1)).toUnitDto(unit);
    }

    @Test
    void deleteTypeById() {
        when(unitRepository.findById(1)).thenReturn(Optional.of(unit));

        unitServiceImpl.deleteUnitById(1);

        verify(unitRepository, times(1)).findById(1);
        verify(unitRepository, times(1)).delete(unit);
    }

    @Nested
    @DisplayName("Проброс исключений")
    class TypeResourceNotFoundExceptionTests{
        @Test
        void updateType() {
            when(unitRepository.findById(1)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> unitServiceImpl.updateUnit(1, unitCreateDto));

            verify(unitRepository, times(1)).findById(1);
        }

        @Test
        void findById() {
            when(unitRepository.findById(1)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> unitServiceImpl.findById(1));

            verify(unitRepository, times(1)).findById(1);
        }
        @Test
        void deleteTypeById() {
            when(unitRepository.findById(1)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> unitServiceImpl.deleteUnitById(1));

            verify(unitRepository, times(1)).findById(1);
        }
    }

}
