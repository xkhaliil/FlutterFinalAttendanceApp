package net.adem.universitymanagement.repositories;

import net.adem.universitymanagement.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, String> {
}
