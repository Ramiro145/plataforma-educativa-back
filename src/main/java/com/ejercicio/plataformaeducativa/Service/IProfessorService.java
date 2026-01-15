package com.ejercicio.plataformaeducativa.Service;

import com.ejercicio.plataformaeducativa.DTO.professor.ProfessorRequestDTO;
import com.ejercicio.plataformaeducativa.DTO.professor.ProfessorResponseDTO;
import com.ejercicio.plataformaeducativa.DTO.professor.ProfessorUpdateDTO;
import com.ejercicio.plataformaeducativa.Model.Professor;

import java.util.List;

public interface IProfessorService extends BaseService<Professor, Long>{


    ProfessorResponseDTO createProfessor(ProfessorRequestDTO dto);
    ProfessorResponseDTO updateProfessor(Long id, ProfessorUpdateDTO dto);
    void  deleteProfessor(Long id);

}
