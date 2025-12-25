package com.ejercicio.plataformaeducativa.Service;


import com.ejercicio.plataformaeducativa.DTO.student.StudentRequestDTO;
import com.ejercicio.plataformaeducativa.DTO.student.StudentResponseDTO;
import com.ejercicio.plataformaeducativa.DTO.student.StudentUpdateDTO;
import com.ejercicio.plataformaeducativa.Model.Student;
import com.ejercicio.plataformaeducativa.Model.UserSec;
import com.ejercicio.plataformaeducativa.Repository.IProfessorRepository;
import com.ejercicio.plataformaeducativa.Repository.IStudentRepository;
import com.ejercicio.plataformaeducativa.Repository.IUserRepository;
import com.ejercicio.plataformaeducativa.mapper.StudentMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService extends BaseServiceImp<Student, Long> implements IStudentService{

    private final IStudentRepository studentRepository;

    private final IUserRepository userRepository;

    private final IProfessorRepository professorRepository;

    private final StudentMapper studentMapper;


    public StudentService (
        IStudentRepository studentRepository,
        IProfessorRepository professorRepository,
        IUserRepository userRepository,
        StudentMapper studentMapper
    ){
        super(studentRepository);
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
        this.userRepository = userRepository;
        this.studentMapper = studentMapper;
    }

    @Override
    public StudentResponseDTO studentFindByIdDTO(Long id){
        Student student = studentRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Student not found: " + id));
        return studentMapper.toDTO(student);
    }

    @Override
    public List<StudentResponseDTO> getStudentAllDTO(){
        List<Student> students = studentRepository.findAll();
        return students.stream().map(studentMapper::toDTO).toList();
    }



    @Override
    @Transactional
    public StudentResponseDTO createStudent(StudentRequestDTO dto) {

        Student student = studentMapper.toEntity(dto);

        UserSec userSec = userRepository.findById(dto.getUserId()).orElseThrow(()->new EntityNotFoundException("User not found, id: "+dto.getUserId()));

        if(studentRepository.existsByUser(userSec)){
            throw new IllegalStateException("This user is already assigned to another student");
        }

        if(professorRepository.existsByUser(userSec)){
            throw new IllegalStateException("This user is already assigned to another professor");
        }

        student.setUser(userSec);

        student.setCourses(new ArrayList<>());

        Student saved = studentRepository.save(student);

        return studentMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public StudentResponseDTO updateStudent(Long id, StudentUpdateDTO dto){

        Student student = studentRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Student not found: " + id));

        if(dto.getName() != null && !dto.getName().isBlank()){
            student.setName(dto.getName());
        }

        if(dto.getEmail() != null && !dto.getEmail().isBlank()){
            student.setEmail(dto.getEmail());
        }

        Student saved = studentRepository.save(student);

        return studentMapper.toDTO(saved);

    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        Student student =studentRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Student not found: " + id));

        //no eliminar si tiene cursos

        if(!student.getCourses().isEmpty()){
            throw new IllegalStateException(
                    "Cannot delete a student who is enrolled in one or more courses"
            );
        }

        studentRepository.delete(student);

    }

}
