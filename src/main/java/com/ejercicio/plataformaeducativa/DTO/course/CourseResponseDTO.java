package com.ejercicio.plataformaeducativa.DTO.course;

import com.ejercicio.plataformaeducativa.DTO.professor.ProfessorSummaryDTO;
import com.ejercicio.plataformaeducativa.DTO.student.StudentSummaryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseResponseDTO {

    private Long id;
    private String courseName;
    private ProfessorSummaryDTO professor;
    private List<StudentSummaryDTO> students;

}
