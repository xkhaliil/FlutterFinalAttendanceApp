package net.adem.universitymanagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @UuidGenerator
    private String id;
    private String firstName;
    private String lastName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @ManyToOne
    private Class classRoom;

    @OneToMany(mappedBy = "student")
    @JsonManagedReference
    @JsonIgnore
    private List<Attendance> attendances;
}
