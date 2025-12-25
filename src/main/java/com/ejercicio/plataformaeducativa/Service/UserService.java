package com.ejercicio.plataformaeducativa.Service;

import com.ejercicio.plataformaeducativa.DTO.user.UserSecRequestDTO;
import com.ejercicio.plataformaeducativa.DTO.user.UserSecResponseDTO;
import com.ejercicio.plataformaeducativa.Model.Role;
import com.ejercicio.plataformaeducativa.Model.UserSec;
import com.ejercicio.plataformaeducativa.Repository.IRoleRepository;
import com.ejercicio.plataformaeducativa.Repository.IUserRepository;
import com.ejercicio.plataformaeducativa.mapper.UserSecMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService extends BaseServiceImp<UserSec, Long> implements  IUserService{


    private final IUserRepository userRepository;

    private final IRoleRepository roleRepository;

    private final UserSecMapper userSecMapper;

    public UserService(
            IUserRepository userRepository,
            IRoleRepository roleRepository,
            UserSecMapper userSecMapper
    ){
        super(userRepository);
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userSecMapper = userSecMapper;
    }


    @Override
    @Transactional
    public UserSecResponseDTO createUser(UserSecRequestDTO dto) {

        //convertir dto a entidad sin roles (aun)
        UserSec userSec = userSecMapper.toEntity(dto);

        //encriptar password
        userSec.setPassword(this.encriptPassword(userSec.getPassword()));


        //implementacion pasada
        /*Set<Role> roleSet = new HashSet<>();
        Role readRole;

        //recuperar roles por Id
        for (Role role : userSec.getRolesSet()){
            readRole = roleRepository.findById(role.getId()).orElse(null);
            if(readRole != null){
                //si encuentra role lo guarda en el set
                roleSet.add(readRole);
            }
        }

        if(!roleSet.isEmpty()){
            userSec.setRolesSet(roleSet);
            return userRepository.save(userSec);
        }

        return null;*/

        Set<Role> roles = new HashSet<>(roleRepository.findAllById(dto.rolesIds()));

        //validar si faltan roles

        if(roles.size() != dto.rolesIds().size()){
            throw new EntityNotFoundException("One or more role IDs do not exist");
        }

        userSec.setRolesSet(roles);

        UserSec saved = userRepository.save(userSec);

        return userSecMapper.toDTO(saved);

    }


    @Override
    @Transactional
    public UserSecResponseDTO updateUser(Long id, UserSecRequestDTO dto) {


        UserSec userSec = userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("User with id not found: " + id));

        if(dto.username() != null && !dto.username().isBlank()){
            userSec.setUsername(dto.username());
        }

        if(dto.password() != null && !dto.password().isBlank()){
            userSec.setPassword(this.encriptPassword(dto.password()));
        }

        if(dto.rolesIds() != null){
            Set<Role> roles = new HashSet<>(roleRepository.findAllById(dto.rolesIds()));
            if(roles.size() != dto.rolesIds().size()){
                throw new EntityNotFoundException("Some roles do not exist");
            }
            userSec.setRolesSet(roles);
        }

        return userSecMapper.toDTO(userRepository.save(userSec));

    }

    @Override
    public void disableUser(Long id){
        UserSec userSec = userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("User with id not found: " + id));

        if (!userSec.isEnabled()) {
            throw new IllegalStateException("User is already disabled");
        }

        userSec.setEnabled(false);

        userRepository.save(userSec);
    }

    @Override
    public String encriptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }


}
