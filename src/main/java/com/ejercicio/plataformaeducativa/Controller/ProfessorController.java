package com.ejercicio.plataformaeducativa.Controller;

import com.ejercicio.plataformaeducativa.DTO.professor.ProfessorRequestDTO;
import com.ejercicio.plataformaeducativa.DTO.professor.ProfessorResponseDTO;
import com.ejercicio.plataformaeducativa.DTO.professor.ProfessorUpdateDTO;
import com.ejercicio.plataformaeducativa.Model.Professor;
import com.ejercicio.plataformaeducativa.Service.IProfessorService;
import com.ejercicio.plataformaeducativa.Service.ProfessorService;
import com.ejercicio.plataformaeducativa.mapper.ProfessorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professor")
@RequiredArgsConstructor
public class ProfessorController {

    private final IProfessorService professorService;
    private final ProfessorMapper professorMapper;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ProfessorResponseDTO>> getAllProfessors() {
        List<Professor> professors = professorService.findAll();
        List<ProfessorResponseDTO> professorResponse = professors.stream().map(professorMapper::toDTO).toList();
        return ResponseEntity.ok(professorResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> getProfessorById(@PathVariable Long id) {
        Professor professor = professorService.findById(id);
        ProfessorResponseDTO professorResponse = professorMapper.toDTO(professor);
        return ResponseEntity.ok(professorResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProfessorResponseDTO> createProfessor(@RequestBody ProfessorRequestDTO dto) {
        ProfessorResponseDTO professorResponse = professorService.createProfessor(dto);
        return ResponseEntity.status(201).body(professorResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> updateProfessor(@PathVariable Long id, @RequestBody ProfessorUpdateDTO dto){
        ProfessorResponseDTO professorResponse = professorService.updateProfessor(id, dto);
        return ResponseEntity.ok(professorResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable Long id) {
        professorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
