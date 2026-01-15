package com.ejercicio.plataformaeducativa.Service;


import com.ejercicio.plataformaeducativa.DTO.AuthLoginRequestDTO;
import com.ejercicio.plataformaeducativa.DTO.AuthResponseDTO;
import com.ejercicio.plataformaeducativa.Model.UserSec;
import com.ejercicio.plataformaeducativa.Repository.IUserRepository;
import com.ejercicio.plataformaeducativa.utils.JWTUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    //implementar JwtUtils
    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException {

        //traer usuarios de BD
        UserSec userSec = userRepository.findUserEntityByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Usuario " + username + " no encontrado"));

        //lista para los permisos para el contexto de seguridad

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        //traer roles y convertirlos para el contexto de seguridad, necesario diferenciar roles con "ROLE_" o lo vera como permiso
        userSec.getRolesSet().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleName()))));

        //traer permisos y convertir
        //aplana todos los permisos de todos los roles, es como "dame un stream de todos los permisos de cada rol"
        userSec.getRolesSet().stream()
                .flatMap(role ->  role.getPermissionsSet().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getPermissionName())));


        return new User(
                userSec.getUsername(),
                userSec.getPassword(),
                userSec.isEnabled(),
                userSec.isAccountNotExpired(),
                userSec.isCredentialNotExpired(),
                userSec.isAccountNotLocked(),
                authorityList
        );

    }


    //login

    public AuthResponseDTO loginUser (@Valid AuthLoginRequestDTO authLoginRequestDTO){

        //recuperar user y pass
        String username = authLoginRequestDTO.username();
        String password = authLoginRequestDTO.password();

        Authentication authentication = this.authenticate (username, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUtils.createToken(authentication);
        AuthResponseDTO authResponseDTO = new AuthResponseDTO(username, "login successful", accessToken, true);
        return authResponseDTO;

    }

    public Authentication authenticate (String username, String password){

        UserDetails userDetails = this.loadUserByUsername(username);

        if(userDetails == null) {
            throw new BadCredentialsException("Invalid Username or password");
        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());

    }



}
