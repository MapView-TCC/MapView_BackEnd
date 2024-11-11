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

    private String name;
    @ManyToOne
    @JoinColumn(name = "id_role")
    private Role role;
    @Column(nullable = false)
    @JsonIgnore
    private boolean operative;



    public Users(String email){
        this.email = email;
        this.operative = true;
    }
    public Users(String email,String name,Role role){
        this.email = email;
        this.name = name;
        this.role = role;
        this.operative = true;
    }

    public Users(String email, String name) {
        this.email = email;
        this.name = name;

    }

    public boolean status_check(){
        return this.operative;
    }

}
