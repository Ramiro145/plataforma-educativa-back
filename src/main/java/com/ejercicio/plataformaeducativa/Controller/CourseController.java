package com.ejercicio.plataformaeducativa.Controller;

import com.ejercicio.plataformaeducativa.DTO.course.CourseRequestDTO;
import com.ejercicio.plataformaeducativa.DTO.course.CourseResponseDTO;
import com.ejercicio.plataformaeducativa.DTO.course.CourseUpdateDTO;
import com.ejercicio.plataformaeducativa.DTO.student.StudentSummaryDTO;
import com.ejercicio.plataformaeducativa.Model.Course;
import com.ejercicio.plataformaeducativa.Service.CourseService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/courses")
@RequiredArgsConstructor
public class CourseController {


    private final CourseService courseService;

    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT', 'PROFESSOR')")
    @GetMapping
    public ResponseEntity<List<CourseResponseDTO>> getAllCourses() {
        return ResponseEntity.ok(courseService.findAllCoursesDTO());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.findCourseByIdDTO(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CourseResponseDTO> saveCourse(@RequestBody CourseRequestDTO dto) {
        CourseResponseDTO courseResponse = courseService.createCourse(dto);
        return ResponseEntity.status(201).body(courseResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> updateCourse(@RequestBody CourseUpdateDTO dto, @PathVariable Long id) {
        CourseResponseDTO courseResponse = courseService.updateCourse(id, dto);
        return ResponseEntity.ok(courseResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/professor/{professorId}")
    public ResponseEntity<CourseResponseDTO> assignProfessor(@PathVariable Long id, @PathVariable Long professorId){

        CourseResponseDTO courseResponse = courseService.assignProfessor(id, professorId);

        return ResponseEntity.ok(courseResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}/professor")
    public ResponseEntity<CourseResponseDTO> removeProfessor(@PathVariable Long id){
        return ResponseEntity.ok(courseService.removeProfessor(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/students")
    public ResponseEntity<CourseResponseDTO> assignStudents(@PathVariable Long id, @RequestBody List<Long> studentsIds){
        CourseResponseDTO courseResponse = courseService.assignStudents(id, studentsIds);
        return ResponseEntity.ok(courseResponse);
    }


    @GetMapping("/{id}/available-students")
    public ResponseEntity<List<StudentSummaryDTO>> getAllAvailableStudents(@PathVariable Long id){
        List<StudentSummaryDTO> students = courseService.getAvailableStudents(id);
        return ResponseEntity.ok(students);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}/students")
    public ResponseEntity<CourseResponseDTO> removeStudents(@PathVariable Long id, @RequestBody List<Long> studentsIds){
        CourseResponseDTO courseResponse = courseService.removeStudents(id, studentsIds);
        return ResponseEntity.ok(courseResponse);
    }




}
