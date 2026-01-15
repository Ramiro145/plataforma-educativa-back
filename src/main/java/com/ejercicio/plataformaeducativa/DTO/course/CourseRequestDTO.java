package com.ejercicio.plataformaeducativa.DTO.course;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseRequestDTO {

    private String courseName;
    private Long professorId;
    private List<Long> studentsIds;


}
