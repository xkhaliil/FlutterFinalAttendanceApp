package net.adem.universitymanagement.web;

import net.adem.universitymanagement.entities.User;
import net.adem.universitymanagement.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserRestController {
    public final UserRepository userRepository;

    public UserRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/users/sign-up")
    public ResponseEntity<User> signUp(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PostMapping("/users/sign-in")
    public ResponseEntity<User> signIn(@RequestBody User user) {
        User savedUser = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if (savedUser != null) {
            return ResponseEntity.status(HttpStatus.OK).body(savedUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/users/sign-out")
    public ResponseEntity<User> signOut(@RequestBody User user) {
        User savedUser = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if (savedUser != null) {
            return ResponseEntity.status(HttpStatus.OK).body(savedUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
