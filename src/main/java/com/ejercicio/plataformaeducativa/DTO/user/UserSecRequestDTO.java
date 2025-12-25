package com.ejercicio.plataformaeducativa.DTO.user;

import java.util.Set;

public record UserSecRequestDTO (String username, String password, Set<Long> rolesIds){}