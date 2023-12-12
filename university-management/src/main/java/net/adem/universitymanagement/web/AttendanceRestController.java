package net.adem.universitymanagement.web;

import net.adem.universitymanagement.entities.Attendance;
import net.adem.universitymanagement.entities.Student;
import net.adem.universitymanagement.repositories.AttendanceRepository;
import net.adem.universitymanagement.repositories.StudentRepository;
import net.adem.universitymanagement.repositories.SubjectRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class AttendanceRestController {
    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    public AttendanceRestController(AttendanceRepository attendanceRepository, StudentRepository studentRepository, SubjectRepository subjectRepository) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    @PostMapping("/attendances/create")
    public Attendance createAttendance(@RequestBody Attendance newAttendance) {
        return attendanceRepository.save(newAttendance);
    }

    @GetMapping("/attendances/{studentId}")
    public List<Attendance> getAttendanceByStudentId(@PathVariable("studentId") String studentId) {
        Student existingStudent = studentRepository.findById(studentId).get();
        return attendanceRepository.findByStudent(existingStudent);
    }

    @GetMapping("/attendances/{studentId}/total")
    public Map<String, Double> getTotalHoursBySubjectForStudent(@PathVariable("studentId") String studentId) {
        Student existingStudent = studentRepository.findById(studentId).orElse(null);
        Map<String, Double> totalHoursBySubject = new HashMap<>();

        if (existingStudent != null) {
            List<Attendance> attendances = attendanceRepository.findByStudent(existingStudent);

            for (Attendance attendance : attendances) {
                String subjectName = attendance.getSubject().getName();
                Double totalHours = totalHoursBySubject.getOrDefault(subjectName, 0.0);
                totalHours += attendance.getNumberOfHours();
                totalHoursBySubject.put(subjectName, totalHours);
            }
        }

        return totalHoursBySubject;
    }


    @GetMapping("/attendances/{studentId}/heuretotale")
    public Double getTotalHoursByStudentId(@PathVariable("studentId") String studentId) {
        System.out.println("studentId: " + studentId);
        Student existingStudent = studentRepository.findById(studentId).get();
        List<Attendance> attendances = attendanceRepository.findByStudent(existingStudent);
        Double totalHours = 0.0;
        for (Attendance attendance : attendances) {
            totalHours += attendance.getNumberOfHours();
        }
        return totalHours;
    }


}
