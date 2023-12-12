package net.adem.universitymanagement.repositories;

import net.adem.universitymanagement.entities.Class;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<Class, String> {
}
