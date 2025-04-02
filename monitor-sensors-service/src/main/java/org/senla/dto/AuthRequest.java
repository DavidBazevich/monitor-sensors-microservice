package org.senla.dto;

import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class AuthRequest {
    private String name;
    @Size(min = 8)
    @EqualsAndHashCode.Exclude
    private String password;
}
