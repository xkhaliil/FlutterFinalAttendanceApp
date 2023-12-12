package net.adem.universitymanagement.repositories;

import net.adem.universitymanagement.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, String> {
}
