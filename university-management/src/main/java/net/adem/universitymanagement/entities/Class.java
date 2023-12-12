package net.adem.universitymanagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Class {
    @Id
    @UuidGenerator
    private String id;
    private String name;
    private int numberOfStudents;

    @ManyToOne
    private Department department;

    @OneToMany(mappedBy = "classRoom")
    @JsonManagedReference
    @JsonIgnore
    private List<Student> students;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "class_subject",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<Subject> subjects;
}
