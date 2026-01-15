package com.ejercicio.plataformaeducativa.DTO.user;

import java.util.Set;

public record RoleResponseDTO(Long id, String roleName, Set<PermissionResponseDTO> permissions) {
}
