package com.MapView.BackEnd.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
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
    //cascade = CascadeType.ALL
    @OneToOne()
    @JoinColumn(name = "id_user")
    private Users id_user;
    @CreationTimestamp
    private Instant login_datetime;
    private LocalDateTime logout_datetime;

}
