package com.ejercicio.plataformaeducativa.Controller;

import com.ejercicio.plataformaeducativa.DTO.user.PermissionRequestDTO;
import com.ejercicio.plataformaeducativa.DTO.user.PermissionResponseDTO;
import com.ejercicio.plataformaeducativa.Model.Permission;
import com.ejercicio.plataformaeducativa.Service.IPermissionService;
import com.ejercicio.plataformaeducativa.mapper.PermissionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping ("/api/permissions")
@PreAuthorize("denyAll()")
@RequiredArgsConstructor
public class PermissionController {

    private final IPermissionService permissionService;
    private final PermissionMapper permissionMapper;

    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<PermissionResponseDTO>> getAllPermissions() {
        List<Permission> permissions = permissionService.findAll();
        return ResponseEntity.ok(permissionMapper.toDTOList(permissions));
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<PermissionResponseDTO> getPermissionById(@PathVariable Long id) {
        Permission permission = permissionService.findById(id);
        return ResponseEntity.ok(permissionMapper.toDTO(permission));

    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PermissionResponseDTO> createPermission(@RequestBody PermissionRequestDTO permission) {
        Permission newPermission = permissionMapper.toEntity(permission);
        Permission saved = permissionService.save(newPermission);
        return ResponseEntity.ok(permissionMapper.toDTO(saved));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PermissionResponseDTO> updatePermission(@PathVariable Long id, @RequestBody PermissionRequestDTO permissionRequestDTO){
        Permission toUpdate = permissionMapper.toEntity(permissionRequestDTO);
        Permission updated = permissionService.update(toUpdate, id);
        return ResponseEntity.ok(permissionMapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id){
        permissionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
