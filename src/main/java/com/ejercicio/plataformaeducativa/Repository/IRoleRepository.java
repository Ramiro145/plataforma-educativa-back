package com.ejercicio.plataformaeducativa.Repository;

import com.ejercicio.plataformaeducativa.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role,Long> {
}
