package net.adem.universitymanagement.web;

import net.adem.universitymanagement.entities.Student;
import net.adem.universitymanagement.repositories.StudentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class StudentRestController {
    private final StudentRepository studentRepository;

    public StudentRestController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/students/{uuid}")
    public Student getStudentById(@PathVariable("uuid") String uuid) {
        if (studentRepository.findById(uuid).isPresent()) {
            return studentRepository.findById(uuid).get();
        } else {
            return null;
        }
    }

    @PostMapping(value = "/students/create", consumes = "application/json", produces = "application/json")
    public Student createStudent(@RequestBody Student newStudent) {
        Student student = Student.builder()
                .firstName(newStudent.getFirstName())
                .lastName(newStudent.getLastName())
                .dateOfBirth(newStudent.getDateOfBirth())
                .classRoom(newStudent.getClassRoom())
                .build();
        return studentRepository.save(student);
    }

    @PutMapping("/students/{uuid}")
    public Student updateStudent(@PathVariable("uuid") String uuid, @RequestBody Student newStudent) {
        if (studentRepository.findById(uuid).isPresent()) {
            Student existingStudent = studentRepository.findById(uuid).get();
            existingStudent.setFirstName(newStudent.getFirstName());
            existingStudent.setLastName(newStudent.getLastName());
            existingStudent.setDateOfBirth(newStudent.getDateOfBirth());
            existingStudent.setClassRoom(newStudent.getClassRoom());
            return studentRepository.save(existingStudent);
        } else {
            return null;
        }
    }

    @DeleteMapping("/students/{uuid}")
    public String deleteStudent(@PathVariable("uuid") String uuid) {
        if (studentRepository.findById(uuid).isPresent()) {
            studentRepository.deleteById(uuid);
            return "Student deleted successfully";
        } else {
            return null;
        }
    }
}
