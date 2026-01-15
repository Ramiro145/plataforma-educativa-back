package com.ejercicio.plataformaeducativa.DTO.user;

import java.util.List;

public record RoleRequestDTO(String roleName, List<Long> permissionsIds) {
}
