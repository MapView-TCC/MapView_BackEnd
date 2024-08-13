package com.MapView.BackEnd.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "access_history")
@Entity(name = "access_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id_history")

public class AccessHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_history;
    private User id_user;
    private LocalDateTime login_datetime;
    private LocalDateTime logout_datetime;
}
