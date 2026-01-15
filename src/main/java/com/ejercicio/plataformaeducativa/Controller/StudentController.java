package com.ejercicio.plataformaeducativa.Controller;


import com.ejercicio.plataformaeducativa.DTO.student.StudentRequestDTO;
import com.ejercicio.plataformaeducativa.DTO.student.StudentResponseDTO;
import com.ejercicio.plataformaeducativa.DTO.student.StudentUpdateDTO;
import com.ejercicio.plataformaeducativa.Service.StudentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class StudentController {

    private final StudentService studentService;

    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT', 'PROFESSOR')")
    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
        return ResponseEntity.ok(studentService.getStudentAllDTO());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.studentFindByIdDTO(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<StudentResponseDTO> saveStudent(@RequestBody StudentRequestDTO dto) {
        StudentResponseDTO studentResponse = studentService.createStudent(dto);
        return ResponseEntity.status(201).body(studentResponse);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable Long id, @RequestBody StudentUpdateDTO dto){
        StudentResponseDTO studentResponse = studentService.updateStudent(id,dto);
        return ResponseEntity.ok(studentResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

}
