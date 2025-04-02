package org.senla.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.senla.entity.Range;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SensorDto{
    private Integer id;
    @Size(min = 3)
    private String name;
    private String model;
    private Range range;
    private String type;
    private String unit;
    private String location;
    private String description;

}
