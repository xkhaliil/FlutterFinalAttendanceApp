package net.adem.universitymanagement.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Attendance {
    @Id
    @UuidGenerator
    private String id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Subject subject;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private Double numberOfHours;
}
