package net.adem.universitymanagement.web;

import net.adem.universitymanagement.entities.Subject;
import net.adem.universitymanagement.repositories.SubjectRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SubjectController {
    private final SubjectRepository subjectRepository;

    public SubjectController(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @GetMapping("/subjects")
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    @GetMapping("/subjects/{uuid}")
    public Subject getSubjectById(@PathVariable("uuid") String uuid) {
        if (subjectRepository.findById(uuid).isPresent()) {
            return subjectRepository.findById(uuid).get();
        } else {
            return null;
        }
    }

    @PostMapping("/subjects/create")
    public Subject createSubject(@RequestBody Subject newSubject) {
        return subjectRepository.save(newSubject);
    }

    @PutMapping("/subjects/{uuid}")
    public Subject updateSubject(@PathVariable("uuid") String uuid, @RequestBody Subject newSubject) {
        if (subjectRepository.findById(uuid).isPresent()) {
            Subject existingSubject = subjectRepository.findById(uuid).get();
            existingSubject.setName(newSubject.getName());
            existingSubject.setDescription(newSubject.getDescription());
            return subjectRepository.save(existingSubject);
        } else {
            return null;
        }
    }

    @DeleteMapping("/subjects/{uuid}")
    public String deleteSubject(@PathVariable("uuid") String uuid) {
        if (subjectRepository.findById(uuid).isPresent()) {
            subjectRepository.deleteById(uuid);
            return "Training deleted successfully";
        } else {
            return null;
        }
    }
}
