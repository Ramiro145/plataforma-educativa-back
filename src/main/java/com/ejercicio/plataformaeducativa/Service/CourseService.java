package com.ejercicio.plataformaeducativa.Service;

import com.ejercicio.plataformaeducativa.DTO.course.CourseRequestDTO;
import com.ejercicio.plataformaeducativa.DTO.course.CourseResponseDTO;
import com.ejercicio.plataformaeducativa.DTO.course.CourseUpdateDTO;
import com.ejercicio.plataformaeducativa.DTO.student.StudentSummaryDTO;
import com.ejercicio.plataformaeducativa.Model.Course;
import com.ejercicio.plataformaeducativa.Model.Professor;
import com.ejercicio.plataformaeducativa.Model.Student;
import com.ejercicio.plataformaeducativa.Repository.ICourseRepository;

import com.ejercicio.plataformaeducativa.Repository.IProfessorRepository;
import com.ejercicio.plataformaeducativa.Repository.IStudentRepository;
import com.ejercicio.plataformaeducativa.mapper.CourseMapper;
import com.ejercicio.plataformaeducativa.mapper.StudentMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService extends BaseServiceImp<Course,Long> implements ICourseService{

    private final ICourseRepository courseRepository;

    private final IStudentRepository studentRepository;

    private final IProfessorRepository professorRepository;

    private final CourseMapper courseMapper;

    private final StudentMapper studentMapper;

    public CourseService(
            ICourseRepository courseRepository,
            IStudentRepository studentRepository,
            IProfessorRepository professorRepository,
            CourseMapper courseMapper,
            StudentMapper studentMapper
    ){
        super(courseRepository);
        this.courseRepository =courseRepository;
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
        this.courseMapper =courseMapper;
        this.studentMapper = studentMapper;
    }


    @Override
    public List<CourseResponseDTO> findAllCoursesDTO() {
        return courseRepository.findAll().stream().map(courseMapper::toDTO).toList();
    }

    @Override
    public CourseResponseDTO findCourseByIdDTO(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Course not found, id: "+ id));
        return courseMapper.toDTO(course);
    }

    @Override
    @Transactional
    public CourseResponseDTO createCourse(CourseRequestDTO dto) {

        Course course = courseMapper.toEntity(dto);

        if(dto.getProfessorId() == null){
            course.setProfessor(null);
        }else{

            Professor professor = professorRepository.findById(dto.getProfessorId()).orElseThrow(()->
                    new EntityNotFoundException("Professor not found, id: " + dto.getProfessorId()));

            course.setProfessor(professor);

        }

        //primero verificar null y despues si esta vacio para evitar NPEs
        if(dto.getStudentsIds() == null || dto.getStudentsIds().isEmpty() ){
            course.setStudents(new ArrayList<>());
        }else{

            List<Student> students = studentRepository.findAllById(dto.getStudentsIds());

            if(dto.getStudentsIds().size() != students.size()){
                throw new EntityNotFoundException("Some students were not found with IDs");
            }


            course.setStudents(students);

        }

        Course saved = courseRepository.save(course);

        return courseMapper.toDTO(saved);

    }


    //antes de un soft delete
    @Override
    @Transactional
    public void deleteCourse(Long id) {

        Course course = courseRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Course not found: " + id));

        if(course.getProfessor() != null){
            throw new IllegalStateException("Cannot delete course: remove assigned professor first");
        }

        if(!course.getStudents().isEmpty()){
            throw new IllegalStateException("Cannot delete course: remove enrolled students first");
        }

        courseRepository.delete(course);
    }

    @Override
    @Transactional
    public CourseResponseDTO updateCourse(Long id, CourseUpdateDTO dto) {

        Course course = courseRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Course not found: " + id));

        if(dto.getCourseName() != null && !dto.getCourseName().isBlank()){
            course.setCourseName(dto.getCourseName());
        }

        if(dto.getProfessorId() != null){

            Professor professor = professorRepository.findById(dto.getProfessorId()).orElseThrow(
                    ()-> new EntityNotFoundException("Professor not found: " + dto.getProfessorId())
            );

            course.setProfessor(professor);

        }else{
            course.setProfessor(null);
        }

        if(dto.getStudentsIds() != null){

            if(dto.getStudentsIds().isEmpty()){
                course.getStudents().clear();
            }else {
                List<Student> students = studentRepository.findAllById(dto.getStudentsIds());

                if(students.size() != dto.getStudentsIds().size()){
                    throw new EntityNotFoundException("Some students were not found with IDs");
                }

                course.getStudents().clear();

                // asignar nuevos estudiantes
                course.getStudents().addAll(students);

            }

        }


        Course saved = courseRepository.save(course);

        return courseMapper.toDTO(saved);

    }

    @Override
    @Transactional
    public CourseResponseDTO assignProfessor(Long id, Long professorId){
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found: " + id));

        Professor professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new EntityNotFoundException("Professor not found: " + professorId));

        if(course.getProfessor() != null && !course.getProfessor().getId().equals(professorId)){
            throw new IllegalStateException(
                    "Course already assigned to another professor. Remove current Professor first."
            );
        }

        course.setProfessor(professor);

        Course saved = courseRepository.save(course);

        return courseMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public CourseResponseDTO removeProfessor(Long id){

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found: " + id));

        if(course.getProfessor() == null){
            throw new IllegalStateException(
                    "Course has no assigned professor to remove"
            );
        }

        course.setProfessor(null);

        Course saved = courseRepository.save(course);

        return courseMapper.toDTO(saved);

    }

    @Override
    public List<StudentSummaryDTO> getAvailableStudents(Long id){
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found: " + id));

        List<Student> students = studentRepository.findAll();

        //filtro para encontrar estudiantes ya inscritos en el curso

        List<Student> availableStudents = students.stream()
                .filter(st -> !course.getStudents().contains(st))
                .toList();

        return availableStudents.stream().map(studentMapper::toSummary).toList();
    }


    @Override
    @Transactional
    public CourseResponseDTO assignStudents(Long id, List<Long> studentsIds){

        Course course = courseRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Course not found: " + id));

        List<Student> students = studentRepository.findAllById(studentsIds);

        if(students.size() != studentsIds.size()){
            throw new EntityNotFoundException("Some students were not found with IDs");
        }

        for (Student st : students){
            if (!course.getStudents().contains(st)) {
                course.getStudents().add(st);
            }

        }

        Course saved = courseRepository.save(course);

        return courseMapper.toDTO(saved);

    }

    @Override
    @Transactional
    public CourseResponseDTO removeStudents(Long id, List<Long> studentsIds){

        Course course = courseRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Course not found: " + id));

        List<Student> students = studentRepository.findAllById(studentsIds);

        if(students.isEmpty()){
            return courseMapper.toDTO(course);
        }

        if (students.size() != studentsIds.size()) {
            throw new EntityNotFoundException("Some students were not found with IDs");
        }


        for (Student st: students){
            //validar que pertenece al curso donde estoy eliminando
            if (!course.getStudents().contains(st)) {
                throw new IllegalStateException("Student " + st.getId() + " is not enrolled in this course");
            }

            course.getStudents().remove(st);
        }

        Course saved = courseRepository.save(course);

        return courseMapper.toDTO(saved);

    }


}
