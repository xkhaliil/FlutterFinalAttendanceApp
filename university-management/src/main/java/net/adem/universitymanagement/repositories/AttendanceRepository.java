package net.adem.universitymanagement.repositories;

import net.adem.universitymanagement.entities.Attendance;
import net.adem.universitymanagement.entities.Student;
import net.adem.universitymanagement.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, String> {
    List<Attendance> findByStudent(Student student);
    List<Attendance> findByStudentAndSubject(Student student, Subject subject);
}
