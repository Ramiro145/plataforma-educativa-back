package com.ejercicio.plataformaeducativa.Repository;

import com.ejercicio.plataformaeducativa.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICourseRepository extends JpaRepository<Course,Long> {
}
