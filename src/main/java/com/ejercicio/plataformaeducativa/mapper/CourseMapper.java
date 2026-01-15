package com.ejercicio.plataformaeducativa.mapper;


import com.ejercicio.plataformaeducativa.DTO.course.CourseRequestDTO;
import com.ejercicio.plataformaeducativa.DTO.course.CourseResponseDTO;
import com.ejercicio.plataformaeducativa.DTO.course.CourseSummaryDTO;
import com.ejercicio.plataformaeducativa.DTO.course.CourseUpdateDTO;
import com.ejercicio.plataformaeducativa.Model.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        config = MapStructConfig.class,
        uses = { ProfessorMapper.class, StudentMapper.class }
)
public interface CourseMapper {

    //Entity -> Response
    @Mapping(target = "professor", source = "professor")
    @Mapping(target = "students", source = "students")
    CourseResponseDTO toDTO(Course course);

    //Entity -> Summary
    CourseSummaryDTO toSummary(Course course);

    List<CourseSummaryDTO> toSummaryList(List<Course> courses);

    //Request -> Entity (sin relaciones)
    //se resuelven en el service las relaciones, es buena practica
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "professor", ignore = true)
    @Mapping(target = "students", ignore = true)
    Course toEntity(CourseRequestDTO courseRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "professor", ignore = true)
    @Mapping(target = "students", ignore = true)
    Course toEntity(CourseUpdateDTO dto);

}
