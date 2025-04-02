package org.senla.dto.creators;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.senla.entity.Range;
import org.senla.validation.RangeVal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SensorCreateDto {
    @Size(min = 3, max = 30)
    private String name;
    @Size(max = 15)
    private String model;
    @RangeVal
    private Range range;
    private String type;
    private String unit;
    @Size(max = 40)
    private String location;
    @Size(max = 200)
    private String description;
}
