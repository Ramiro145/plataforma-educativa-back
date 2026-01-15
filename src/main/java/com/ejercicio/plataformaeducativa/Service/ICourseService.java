package com.ejercicio.plataformaeducativa.Service;


import com.ejercicio.plataformaeducativa.DTO.course.CourseRequestDTO;
import com.ejercicio.plataformaeducativa.DTO.course.CourseResponseDTO;
import com.ejercicio.plataformaeducativa.DTO.course.CourseUpdateDTO;
import com.ejercicio.plataformaeducativa.DTO.student.StudentSummaryDTO;
import com.ejercicio.plataformaeducativa.Model.Course;

import java.util.List;

public interface ICourseService extends BaseService<Course,Long>{

    public List<CourseResponseDTO> findAllCoursesDTO();
    public CourseResponseDTO findCourseByIdDTO(Long id);

    public CourseResponseDTO createCourse(CourseRequestDTO dto);
    public void deleteCourse(Long id);
    public CourseResponseDTO updateCourse(Long id, CourseUpdateDTO dto);

    public CourseResponseDTO assignProfessor(Long id, Long professorId);
    public CourseResponseDTO removeProfessor(Long id);

    public CourseResponseDTO assignStudents(Long id, List<Long> studentsIds);
    public List<StudentSummaryDTO> getAvailableStudents(Long id);
    public CourseResponseDTO removeStudents(Long id, List<Long> studentsIds);
}
