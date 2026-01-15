package com.ejercicio.plataformaeducativa.DTO.student;


import com.ejercicio.plataformaeducativa.DTO.course.CourseSummaryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponseDTO {

    private Long id;
    private String name;
    private String email;
    private Long userId;

    // lista de cursos puede agregarse más adelante

    List<CourseSummaryDTO> courseSummaryList;

}
