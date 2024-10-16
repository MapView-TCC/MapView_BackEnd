package com.MapView.BackEnd.entities;

import com.MapView.BackEnd.dtos.User.UserCreateDTO;
import com.MapView.BackEnd.enums.RoleUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "users")
@Entity(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id_user")


public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id_user;
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private boolean operative;



    public Users(String email){
        this.email = email;
        this.operative = true;
    }

    public boolean status_check(){
        return this.operative;
    }

}
