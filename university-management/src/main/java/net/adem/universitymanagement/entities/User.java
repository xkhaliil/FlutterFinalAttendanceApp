package net.adem.universitymanagement.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @UuidGenerator
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
