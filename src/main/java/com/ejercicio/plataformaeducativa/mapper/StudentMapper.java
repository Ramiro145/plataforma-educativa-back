package com.ejercicio.plataformaeducativa.mapper;


import com.ejercicio.plataformaeducativa.DTO.student.StudentRequestDTO;
import com.ejercicio.plataformaeducativa.DTO.student.StudentResponseDTO;
import com.ejercicio.plataformaeducativa.DTO.student.StudentSummaryDTO;
import com.ejercicio.plataformaeducativa.Model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        config = MapStructConfig.class)
public interface StudentMapper {

    //Entity -> Response
    StudentResponseDTO toDTO(Student student);

    //Entity -> Summary

    StudentSummaryDTO toSummary(Student student);

    //Request -> Entity (sin relaciones)
    //se resuelven en el service las relaciones, es buena practica
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "courses", ignore = true)
    @Mapping(target = "user", ignore = true)
    Student toEntity(StudentRequestDTO studentRequestDTO);



}
