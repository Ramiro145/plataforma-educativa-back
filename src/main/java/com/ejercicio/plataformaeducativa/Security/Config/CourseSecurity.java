package com.ejercicio.plataformaeducativa.Security.Config;

import com.ejercicio.plataformaeducativa.Model.Course;
import com.ejercicio.plataformaeducativa.Model.UserSec;
import com.ejercicio.plataformaeducativa.Repository.ICourseRepository;

import com.ejercicio.plataformaeducativa.Repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("courseSecurity")
public class CourseSecurity {

    @Autowired
    private ICourseRepository courseRepository;

    @Autowired
    private IUserRepository userRepository;

    public boolean isProfessorOfCourse(Long courseId, String username) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        UserSec user = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return course.getProfessor().getUser().getId().equals(user.getId());
    }

}
