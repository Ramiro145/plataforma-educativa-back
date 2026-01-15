package com.ejercicio.plataformaeducativa.mapper;


import com.ejercicio.plataformaeducativa.DTO.user.PermissionRequestDTO;
import com.ejercicio.plataformaeducativa.DTO.user.PermissionResponseDTO;
import com.ejercicio.plataformaeducativa.Model.Permission;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapStructConfig.class
)
public interface PermissionMapper {

    PermissionResponseDTO toDTO (Permission permission);

    Permission toEntity(PermissionRequestDTO permissionDTO);

    List<PermissionResponseDTO> toDTOList(List<Permission> permissions);

}
