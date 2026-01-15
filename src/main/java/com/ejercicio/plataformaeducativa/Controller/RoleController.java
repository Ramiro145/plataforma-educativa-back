package com.ejercicio.plataformaeducativa.Controller;

import com.ejercicio.plataformaeducativa.DTO.user.RoleRequestDTO;
import com.ejercicio.plataformaeducativa.DTO.user.RoleResponseDTO;
import com.ejercicio.plataformaeducativa.Model.Role;
import com.ejercicio.plataformaeducativa.Service.IPermissionService;
import com.ejercicio.plataformaeducativa.Service.IRoleService;
import com.ejercicio.plataformaeducativa.mapper.RoleMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping ("/api/roles")
@PreAuthorize("denyAll()")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class RoleController {

    private final IRoleService roleService;
    private final RoleMapper roleMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<RoleResponseDTO>> getAllRoles(){
        List<Role> roles = roleService.findAll();
        return ResponseEntity.ok(roleMapper.toDTOList(roles));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<RoleResponseDTO> getRoleById(@PathVariable Long id){
        Role role = roleService.findById(id);
        RoleResponseDTO roleResponse = roleMapper.toDTO(role);
        return ResponseEntity.ok(roleResponse);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<RoleResponseDTO> createRole(@RequestBody RoleRequestDTO roleRequestDTO){

        RoleResponseDTO newRole = roleService.create(roleRequestDTO);

        return ResponseEntity.ok(newRole);

    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<RoleResponseDTO> patchRole(@RequestBody RoleRequestDTO roleRequestDTO,@PathVariable Long id){

        RoleResponseDTO updated = roleService.updateRole(id, roleRequestDTO);

        return ResponseEntity.ok(updated);

    }
}
