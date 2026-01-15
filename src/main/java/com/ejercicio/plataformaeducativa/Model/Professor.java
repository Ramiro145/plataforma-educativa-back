package com.ejercicio.plataformaeducativa.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "professors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Professor {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @OneToOne
    @JoinColumn(name = "user_Id")
    private UserSec user;  // relación al usuario

    @OneToMany(mappedBy = "professor")
    @JsonIgnore
    private List<Course> courseList;


}
