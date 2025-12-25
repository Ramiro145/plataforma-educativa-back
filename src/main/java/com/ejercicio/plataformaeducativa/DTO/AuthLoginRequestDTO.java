package com.ejercicio.plataformaeducativa.DTO;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequestDTO (@NotBlank String username,
                                   @NotBlank String password) {
}
