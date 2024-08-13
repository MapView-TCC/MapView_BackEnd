package com.MapView.BackEnd.entities;

import com.MapView.BackEnd.enums.EnumClasses;
import jakarta.persistence.*;
import lombok.*;

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
    private EnumClasses enumClasses;
    private String classes;
    private User id_user;
    private LocalDateTime creation_date;
    private boolean operative;

}
