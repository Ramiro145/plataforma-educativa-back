package com.ejercicio.plataformaeducativa.Service;

import com.ejercicio.plataformaeducativa.DTO.user.UserSecRequestDTO;
import com.ejercicio.plataformaeducativa.DTO.user.UserSecResponseDTO;
import com.ejercicio.plataformaeducativa.Model.UserSec;

import java.util.List;
import java.util.Optional;

public interface IUserService extends BaseService<UserSec, Long>{


    public UserSecResponseDTO createUser(UserSecRequestDTO dto);

    public UserSecResponseDTO updateUser(Long id, UserSecRequestDTO dto);

    public void disableUser(Long id);

    public String encriptPassword(String password);

}
