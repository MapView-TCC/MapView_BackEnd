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
    private Long id_user;
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleUser role;

    @Column(nullable = false)
    @JsonIgnore
    private boolean operative;



    public Users(String email){
        this.email = email;
        this.role = RoleUser.USER;
        this.operative = true;
    }

    public boolean status_check(){
        return this.operative;
    }

}
