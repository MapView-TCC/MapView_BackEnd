package com.MapView.BackEnd.entities;

import com.MapView.BackEnd.enums.RoleUser;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "user")
@Entity(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id_user")


public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_user;
    @Enumerated
    private RoleUser roleUser;
    private boolean operative;

}
