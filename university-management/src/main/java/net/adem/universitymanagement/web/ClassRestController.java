package net.adem.universitymanagement.web;

import net.adem.universitymanagement.entities.Class;
import net.adem.universitymanagement.entities.Student;
import net.adem.universitymanagement.repositories.ClassRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ClassRestController {
    private final ClassRepository classRepository;

    public ClassRestController(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    @GetMapping("/classes")
    public List<Class> getAllClasses() {
        return classRepository.findAll();
    }

    @GetMapping("/classes/{uuid}")
    public Class getClassById(@PathVariable("uuid") String uuid) {
        if (classRepository.findById(uuid).isPresent()) {
            return classRepository.findById(uuid).get();
        } else {
            return null;
        }
    }

    @PostMapping("/classes/create")
    public Class createClass(@RequestBody Class newClass) {
        return classRepository.save(newClass);
    }

    @PutMapping("/classes/{uuid}")
    public Class updateClass(@PathVariable("uuid") String uuid, @RequestBody Class newClass) {
        if (classRepository.findById(uuid).isPresent()) {
            Class existingClass = classRepository.findById(uuid).get();
            existingClass.setName(newClass.getName());
            existingClass.setDepartment(newClass.getDepartment());
            existingClass.setStudents(newClass.getStudents());
            return classRepository.save(existingClass);
        } else {
            return null;
        }
    }

    @DeleteMapping("/classes/{uuid}")
    public String deleteClass(@PathVariable("uuid") String uuid) {
        if (classRepository.findById(uuid).isPresent()) {
            classRepository.deleteById(uuid);
            return "Class deleted successfully";
        } else {
            return null;
        }
    }

    @GetMapping("/classes/{uuid}/students")
    public List<Student> getStudentsByClassId(@PathVariable("uuid") String uuid) {
        if (classRepository.findById(uuid).isPresent()) {
            Class existingClass = classRepository.findById(uuid).get();
            return existingClass.getStudents();
        } else {
            return null;
        }
    }
}
