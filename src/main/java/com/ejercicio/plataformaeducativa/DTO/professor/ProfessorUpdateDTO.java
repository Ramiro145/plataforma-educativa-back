package com.ejercicio.plataformaeducativa.DTO.professor;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorUpdateDTO {

    private String name;
    private String email;
    private List<Long> courseIds;

}
