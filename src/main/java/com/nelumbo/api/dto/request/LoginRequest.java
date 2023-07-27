package com.nelumbo.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "No puede estar vacio")
    String email;
    @NotBlank(message = "No puede estar vacio")
    String password;
}