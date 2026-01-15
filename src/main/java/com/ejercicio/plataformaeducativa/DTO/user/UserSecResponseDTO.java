package com.ejercicio.plataformaeducativa.DTO.user;


import java.util.Set;

public record UserSecResponseDTO (Long id, String username, Set<RoleResponseDTO> roles){}
