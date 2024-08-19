package com.MapView.BackEnd.entities;

import com.MapView.BackEnd.enums.EnumAction;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;

@Table(name = "user_log")
@Entity(name = "user_log")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id_log")
public class UserLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_log;
    @OneToOne
    @JoinColumn(name = "id_user")
    private Users id_user;
    private String altered_table;
    private String id_altered;
    private String field;
    private String description;
    @CreationTimestamp
    private Instant datetime;
    @Enumerated
    private EnumAction action;
}
