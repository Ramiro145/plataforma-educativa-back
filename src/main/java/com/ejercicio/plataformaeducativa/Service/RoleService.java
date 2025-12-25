package com.ejercicio.plataformaeducativa.Service;

import com.ejercicio.plataformaeducativa.DTO.user.RoleRequestDTO;
import com.ejercicio.plataformaeducativa.DTO.user.RoleResponseDTO;
import com.ejercicio.plataformaeducativa.Model.Permission;
import com.ejercicio.plataformaeducativa.Model.Role;
import com.ejercicio.plataformaeducativa.Repository.IPermissionRepository;
import com.ejercicio.plataformaeducativa.Repository.IRoleRepository;
import com.ejercicio.plataformaeducativa.mapper.RoleMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService extends BaseServiceImp<Role, Long> implements IRoleService{


    private final IRoleRepository roleRepository;
    private final IPermissionRepository permissionRepository;
    private final RoleMapper roleMapper;

    public RoleService(
            IRoleRepository repository,
            IPermissionRepository permissionRepository,
            RoleMapper roleMapper
    ){
        super(repository);
        this.roleRepository = repository;
        this.permissionRepository = permissionRepository;
        this.roleMapper = roleMapper;
    }




    @Override
    @Transactional
    public RoleResponseDTO create(RoleRequestDTO roleRequestDTO) {

        Role role = roleMapper.toEntity(roleRequestDTO);
        //implementacion pasada por permiso completo
        /*Set<Permission> permissionSet = new HashSet<>();
        Permission readPermission;*/

        //recuperar permissions por su Id
        /*for (Permission per : roleRequestDTO.getPermissionsSet()){

            //la variable nos ayuda a buscar en el permissionservice si es que una id que mandamos en el cuerpo
            //existe como permiso y si si la asignamos al rol

            readPermission = permissionRepository.findById(per.getId()).orElse(null);
            if(readPermission != null){
                //si encuentra guarda en lista
                permissionSet.add(readPermission);
            }

        }*/

        //cargar permisos de una sola vez
        Set<Permission> permissions = new HashSet<>(permissionRepository.findAllById(roleRequestDTO.permissionsIds()));

        //validar si falta uno
        if(permissions.size() != roleRequestDTO.permissionsIds().size()){
            throw new EntityNotFoundException("One or more permission IDs do not exist");
        }

        role.setPermissionsSet(permissions);


        return roleMapper.toDTO(roleRepository.save(role));

    }


    @Override
    @Transactional
    public RoleResponseDTO updateRole(Long id, RoleRequestDTO roleRequestDTO) {

        Role rolExist = roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));

        if(roleRequestDTO.name() != null && !roleRequestDTO.name().isBlank()){
            rolExist.setRoleName(roleRequestDTO.name());
        }

        if(roleRequestDTO.permissionsIds() != null){


            Set<Permission> permissions = new HashSet<>(permissionRepository.findAllById(roleRequestDTO.permissionsIds()));

            if(permissions.size() != roleRequestDTO.permissionsIds().size()){
                throw  new EntityNotFoundException("One or more permissions IDs do not exist");
            }

            rolExist.setPermissionsSet(permissions);

            //implementacion pasada donde obteniamos los permisos completos directamente de la entidad sin dtos
            /*//obtenemos solo las ids de los permisos que asignamos en el cuerpo del rol
            Set<Long> permissionsIds = role.getPermissionsSet().stream()
                    .map(permission -> permission.getId()).collect(Collectors.toSet());

            //verificamos que todos los permisos existan
            Set<Permission> permissions = new HashSet<>(permissionRepository.findAllById(permissionsIds));

            rolExist.setPermissionsSet(permissions);*/


        }


        return roleMapper.toDTO(roleRepository.save(rolExist));

    }


}
