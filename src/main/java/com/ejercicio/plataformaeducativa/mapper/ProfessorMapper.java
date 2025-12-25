package com.ejercicio.plataformaeducativa.mapper;

import com.ejercicio.plataformaeducativa.DTO.professor.ProfessorRequestDTO;
import com.ejercicio.plataformaeducativa.DTO.professor.ProfessorResponseDTO;
import com.ejercicio.plataformaeducativa.DTO.professor.ProfessorSummaryDTO;
import com.ejercicio.plataformaeducativa.DTO.professor.ProfessorUpdateDTO;
import com.ejercicio.plataformaeducativa.Model.Professor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class,
        uses = {CourseMapper.class})
public interface ProfessorMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "courseSummaryList", source = "courseList")
    ProfessorResponseDTO toDTO(Professor professor);

    ProfessorSummaryDTO toSummary(Professor professor);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "courseList", ignore = true)
    @Mapping(target = "user", ignore = true)
    Professor toEntity(ProfessorRequestDTO professorRequestDTO);


}
