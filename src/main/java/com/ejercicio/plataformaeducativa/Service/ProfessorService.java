package com.ejercicio.plataformaeducativa.Service;

import com.ejercicio.plataformaeducativa.DTO.professor.ProfessorRequestDTO;
import com.ejercicio.plataformaeducativa.DTO.professor.ProfessorResponseDTO;
import com.ejercicio.plataformaeducativa.DTO.professor.ProfessorUpdateDTO;
import com.ejercicio.plataformaeducativa.Model.Course;
import com.ejercicio.plataformaeducativa.Model.Professor;
import com.ejercicio.plataformaeducativa.Model.UserSec;
import com.ejercicio.plataformaeducativa.Repository.ICourseRepository;
import com.ejercicio.plataformaeducativa.Repository.IProfessorRepository;
import com.ejercicio.plataformaeducativa.Repository.IStudentRepository;
import com.ejercicio.plataformaeducativa.Repository.IUserRepository;
import com.ejercicio.plataformaeducativa.mapper.ProfessorMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfessorService extends BaseServiceImp<Professor,Long> implements  IProfessorService{


    private final ICourseRepository courseRepository;

    private final IProfessorRepository professorRepository;

    private final IStudentRepository studentRepository;

    private final IUserRepository userRepository;

    private final ProfessorMapper professorMapper;

    public ProfessorService(
            IProfessorRepository professorRepository,
            IStudentRepository studentRepository,
            IUserRepository userRepository,
            ICourseRepository courseRepository,
            ProfessorMapper professorMapper
    ){
        super(professorRepository);
        this.professorRepository = professorRepository;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.professorMapper = professorMapper;
    }


    @Override
    @Transactional
    public ProfessorResponseDTO createProfessor(ProfessorRequestDTO dto) {

        //professor sin user ni cursos
        Professor professor = professorMapper.toEntity(dto);

        UserSec userSec = userRepository.findById(dto.getUserId()).orElseThrow(() -> new UsernameNotFoundException("User not found id: " + dto.getUserId()));

        if(studentRepository.existsByUser(userSec)){
            throw new IllegalStateException("This user is already assigned to another student");
        }

        if(professorRepository.existsByUser(userSec)){
            throw new IllegalStateException("This user is already assigned to another professor");
        }

        professor.setUser(userSec);

        professor.setCourseList(new ArrayList<>());

        return professorMapper.toDTO(professorRepository.save(professor));

    }

    @Override
    @Transactional
    public ProfessorResponseDTO updateProfessor(Long id, ProfessorUpdateDTO dto){

        Professor professor = professorRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Professor not found, id: " + id));

        if(dto.getName() != null && !dto.getName().isBlank()){
            professor.setName(dto.getName());
        }

        if(dto.getEmail() != null && !dto.getEmail().isBlank()){
            professor.setEmail(dto.getEmail());
        }

        //actualizar cursos

        if(dto.getCourseIds() != null){

            //limpiar cursos si esta vacia la lista
            if(dto.getCourseIds().isEmpty()){

                //desasignar los cursos que tenga
                for (Course course : professor.getCourseList()){
                    course.setProfessor(null);
                }
                professor.setCourseList(new ArrayList<>());

            }else {

                //buscar cursos
                List<Course> courses = courseRepository.findAllById(dto.getCourseIds());

                if(courses.size() != dto.getCourseIds().size()){
                    throw new EntityNotFoundException("Some courses do not exist");
                }

                //validar que no tengan algun profesor

                for (Course course : courses){

                    Professor assigned = course.getProfessor();

                    // si el curso ya tiene profesor y NO es el profesor actual
                    if (assigned != null && !assigned.getId().equals(id)) {
                        throw new IllegalStateException(
                                "Course " + course.getId() + " is already assigned to professor " + assigned.getId()
                        );
                    }

                }


                //limpiar relaciones anteriores de los cursos del profesor sin actualizar
                for (Course previous : professor.getCourseList()){
                    previous.setProfessor(null);
                }

                //asignar a los nuevos cursos

                for (Course course : courses){
                    course.setProfessor(professor);
                }

                professor.setCourseList(courses);

            }


        }

        Professor saved = professorRepository.save(professor);

        return professorMapper.toDTO(saved);

    }

    @Override
    @Transactional
    public void deleteProfessor(Long id){

        Professor professor = professorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Professor not found"));

        //validacion de cursos que siguen asignados
        if(!professor.getCourseList().isEmpty()){
            throw new IllegalStateException("Cannot delete professor with assigned courses, reassign or remove courses first.");
        }

        professorRepository.delete(professor);

    }


}
