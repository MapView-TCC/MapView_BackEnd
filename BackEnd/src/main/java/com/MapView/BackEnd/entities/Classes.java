package com.MapView.BackEnd.entities;

import com.MapView.BackEnd.dtos.Classes.ClassesCreateDTO;
import com.MapView.BackEnd.enums.EnumCourse;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "classes")
@Entity(name = "classes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id_classes")


public class Classes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_classes;
    @Enumerated(EnumType.STRING)
    @Column(name = "course_name")
    private EnumCourse enumCourse;
    private String classes;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
    private Users user;
    private LocalDate creation_date;
    private boolean operative;


    public Classes(ClassesCreateDTO data, Users user){
        this.enumCourse = data.enumCourse();
        this.classes = data.classes();
        this.user = user;
        this.creation_date = data.criation_date();
        this.operative = true;
    }
}
