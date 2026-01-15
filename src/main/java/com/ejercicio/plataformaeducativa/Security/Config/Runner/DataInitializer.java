package com.ejercicio.plataformaeducativa.Security.Config.Runner;

import com.ejercicio.plataformaeducativa.Model.Permission;
import com.ejercicio.plataformaeducativa.Model.Role;
import com.ejercicio.plataformaeducativa.Model.UserSec;
import com.ejercicio.plataformaeducativa.Repository.IPermissionRepository;
import com.ejercicio.plataformaeducativa.Repository.IRoleRepository;
import com.ejercicio.plataformaeducativa.Repository.IUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Value("${APP_ADMIN_USER}")
    private String adminUserName;

    @Value("${APP_ADMIN_PASS}")
    private String adminPassword;

    @Bean
    CommandLineRunner initData(IUserRepository userRepository,
                               IRoleRepository roleRepository,
                               IPermissionRepository permissionRepository,
                               PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {

                // 1. Crear permisos básicos
                Permission readPermission = new Permission(null, "READ");
                Permission writePermission = new Permission(null, "WRITE");
                Permission updatePermission = new Permission(null, "UPDATE");
                Permission createPermission = new Permission(null, "CREATE");
                Permission executePermission = new Permission(null, "EXECUTE");


                permissionRepository.save(readPermission);
                permissionRepository.save(writePermission);
                permissionRepository.save(updatePermission);
                permissionRepository.save(createPermission);
                permissionRepository.save(executePermission);

                Set<Permission> adminPermissions = new HashSet<>();
                adminPermissions.add(readPermission);
                adminPermissions.add(writePermission);
                adminPermissions.add(updatePermission);
                adminPermissions.add(createPermission);
                adminPermissions.add(executePermission);


                // 2. Crear rol ADMIN con permisos
                Role adminRole = new Role(null, "ADMIN", adminPermissions);
                roleRepository.save(adminRole);

                // 3. Crear usuario admin con rol ADMIN
                UserSec admin = new UserSec();
                admin.setUsername(adminUserName);
                admin.setPassword(passwordEncoder.encode(adminPassword)); // 🔑 encriptado
                admin.setEnabled(true);
                admin.setAccountNotExpired(true);
                admin.setAccountNotLocked(true);
                admin.setCredentialNotExpired(true);

                admin.getRolesSet().add(adminRole);

                userRepository.save(admin);

                System.out.println("✅ Usuario ADMIN creado con éxito.");
            }
        };
    }
}

