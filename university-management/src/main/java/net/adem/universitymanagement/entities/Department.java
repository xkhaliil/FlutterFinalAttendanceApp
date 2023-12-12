package net.adem.universitymanagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    @Id
    @UuidGenerator
    private String id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "department")
    @JsonManagedReference
    @JsonIgnore
    private List<Class> classes;
}
