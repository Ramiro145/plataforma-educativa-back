package com.ejercicio.plataformaeducativa.mapper;


import com.ejercicio.plataformaeducativa.DTO.user.UserSecRequestDTO;
import com.ejercicio.plataformaeducativa.DTO.user.UserSecResponseDTO;
import com.ejercicio.plataformaeducativa.Model.UserSec;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        config = MapStructConfig.class, uses = {RoleMapper.class})
public interface UserSecMapper {


    //todos los flags necesarios para spring security se establecen al pasarse a entidad
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rolesSet", ignore = true)
    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "accountNotExpired", constant = "true")
    @Mapping(target = "accountNotLocked", constant = "true")
    @Mapping(target = "credentialNotExpired", constant = "true")
    UserSec toEntity(UserSecRequestDTO dto);

    @Mapping(target = "roles", source = "rolesSet")
    UserSecResponseDTO toDTO(UserSec userSec);

}
