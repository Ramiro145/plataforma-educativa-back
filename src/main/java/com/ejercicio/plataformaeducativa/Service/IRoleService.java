package com.ejercicio.plataformaeducativa.Service;

import com.ejercicio.plataformaeducativa.DTO.user.RoleRequestDTO;
import com.ejercicio.plataformaeducativa.DTO.user.RoleResponseDTO;
import com.ejercicio.plataformaeducativa.Model.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService extends BaseService<Role, Long>{

    RoleResponseDTO create(RoleRequestDTO dto);

    RoleResponseDTO updateRole(Long id, RoleRequestDTO dto);

}
