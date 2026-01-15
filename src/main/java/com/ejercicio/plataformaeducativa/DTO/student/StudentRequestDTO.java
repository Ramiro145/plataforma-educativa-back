package com.ejercicio.plataformaeducativa.DTO.student;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequestDTO {

    private String name;

    private String email;

    private Long userId; // opcional

}
