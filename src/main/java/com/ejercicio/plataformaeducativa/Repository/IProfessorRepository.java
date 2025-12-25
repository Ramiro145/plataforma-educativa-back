package com.ejercicio.plataformaeducativa.Repository;


import com.ejercicio.plataformaeducativa.Model.Professor;
import com.ejercicio.plataformaeducativa.Model.UserSec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProfessorRepository extends JpaRepository<Professor,Long> {

    boolean existsByUser(UserSec userSec);


}
