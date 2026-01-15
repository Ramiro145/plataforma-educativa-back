package com.ejercicio.plataformaeducativa.Repository;

import com.ejercicio.plataformaeducativa.Model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPermissionRepository extends JpaRepository<Permission,Long> {
}
