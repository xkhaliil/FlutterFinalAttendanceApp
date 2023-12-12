package net.adem.universitymanagement.repositories;

import net.adem.universitymanagement.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
}
