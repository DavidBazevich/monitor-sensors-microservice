package org.senla.dto;

import jakarta.validation.constraints.Size;
import lombok.*;
import org.senla.entity.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String name;
    @Size(min = 8)
    @EqualsAndHashCode.Exclude
    private String password;
    private Role role;
}
