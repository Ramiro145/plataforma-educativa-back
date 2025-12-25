package com.ejercicio.plataformaeducativa.Controller;

import com.ejercicio.plataformaeducativa.DTO.user.UserSecRequestDTO;
import com.ejercicio.plataformaeducativa.DTO.user.UserSecResponseDTO;
import com.ejercicio.plataformaeducativa.Model.UserSec;
import com.ejercicio.plataformaeducativa.Service.IRoleService;
import com.ejercicio.plataformaeducativa.Service.IUserService;
import com.ejercicio.plataformaeducativa.mapper.UserSecMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/users")
@PreAuthorize("denyAll()")
@RequiredArgsConstructor
public class UserController {


    private final IUserService userService;
    private final UserSecMapper userSecMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<UserSecResponseDTO>> getAllUsers(){
        List<UserSec> users = userService.findAll();
        List<UserSecResponseDTO> usersResponse = users.stream().map(userSec -> userSecMapper.toDTO(userSec)).toList();
        return ResponseEntity.ok(usersResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserSecResponseDTO> getUserById(@PathVariable Long id){

        UserSec user = userService.findById(id);
        UserSecResponseDTO userResponse = userSecMapper.toDTO(user);
        return ResponseEntity.ok(userResponse);

    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserSecResponseDTO> createUser(@RequestBody UserSecRequestDTO dto){

        UserSecResponseDTO newUser = userService.createUser(dto);
        return ResponseEntity.status(201).body(newUser);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserSecResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserSecRequestDTO dto){

        UserSecResponseDTO newUser = userService.updateUser(id,dto);

        return ResponseEntity.ok(newUser);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> disableUser(@PathVariable Long id){
        userService.disableUser(id);
        return ResponseEntity.noContent().build();
    }
}
