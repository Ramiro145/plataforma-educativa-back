package com.ejercicio.plataformaeducativa.mapper;

import com.ejercicio.plataformaeducativa.DTO.user.RoleRequestDTO;
import com.ejercicio.plataformaeducativa.DTO.user.RoleResponseDTO;
import com.ejercicio.plataformaeducativa.Model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapStructConfig.class,
        uses = {PermissionMapper.class})
public interface RoleMapper {


    @Mapping(target = "permissions", source = "permissionsSet")
    RoleResponseDTO toDTO(Role role);

    List<RoleResponseDTO> toDTOList(List<Role> roles);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "permissionsSet", ignore = true) // se llaman en el servicio con las ids
    Role toEntity(RoleRequestDTO roleRequestDTO);

}
