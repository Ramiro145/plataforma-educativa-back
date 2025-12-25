package com.ejercicio.plataformaeducativa.DTO.professor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorRequestDTO {

    private String name;
    private String email;
    private Long userId;

}
