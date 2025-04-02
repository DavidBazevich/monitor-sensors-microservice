package org.senla.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "name")
public class TypeDto {
    private Integer id;
    private String name;
}
