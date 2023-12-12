package net.adem.universitymanagement.repositories;

import net.adem.universitymanagement.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByEmailAndPassword(String email, String password);
}
