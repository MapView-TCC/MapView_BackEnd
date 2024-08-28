package com.MapView.BackEnd.entities;

import com.MapView.BackEnd.enums.EnumAction;
import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.User;
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
    private Users user;
    private String altered_table;
    private Long id_altered;
    private String field;
    private String description;
    @CreationTimestamp
    private Instant datetime;
    @Enumerated
    private EnumAction action;


    public UserLog (Users user, String altered_table, Long id_altered, String field, String description, EnumAction action){
        this.user = user;
        this.altered_table = altered_table;
        this.id_altered = id_altered;
        this.field = field;
        this.datetime = Instant.now();
        this.action = action;
    }
    public UserLog (Users user, String altered_table, Long id_altered, String description, EnumAction action){
        this.user = user;
        this.altered_table = altered_table;
        this.id_altered = id_altered;
        this.field = null;
        this.datetime = Instant.now();
        this.action = action;
    }


}
