package com.ejercicio.plataformaeducativa.DTO.professor;


import com.ejercicio.plataformaeducativa.DTO.course.CourseSummaryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProfessorResponseDTO {

    private Long id;
    private String name;
    private String email;
    private Long userId;

    //podriamos agregar una lista de cursos
    private List<CourseSummaryDTO> courseSummaryList;

}
