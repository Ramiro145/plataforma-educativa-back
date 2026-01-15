package com.ejercicio.plataformaeducativa.Service;


import com.ejercicio.plataformaeducativa.DTO.student.StudentRequestDTO;
import com.ejercicio.plataformaeducativa.DTO.student.StudentResponseDTO;
import com.ejercicio.plataformaeducativa.DTO.student.StudentUpdateDTO;
import com.ejercicio.plataformaeducativa.Model.Student;

import java.util.List;

public interface IStudentService extends BaseService<Student, Long>{

    public StudentResponseDTO studentFindByIdDTO(Long id);
    public List<StudentResponseDTO> getStudentAllDTO();
    public StudentResponseDTO createStudent(StudentRequestDTO dto);
    public void deleteStudent(Long id);
    StudentResponseDTO updateStudent(Long id, StudentUpdateDTO dto);

}
