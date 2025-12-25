package com.ejercicio.plataformaeducativa.Repository;


import com.ejercicio.plataformaeducativa.Model.Student;
import com.ejercicio.plataformaeducativa.Model.UserSec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStudentRepository extends JpaRepository<Student,Long> {

    boolean existsByUser(UserSec userSec);

}
