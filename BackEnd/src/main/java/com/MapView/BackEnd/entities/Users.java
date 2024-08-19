package com.MapView.BackEnd.entities;

import com.MapView.BackEnd.dtos.User.UserCreateDTO;
import com.MapView.BackEnd.enums.RoleUser;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "Users")
@Entity(name = "Users")
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
    private boolean operative;



    public Users(String email){
        this.email = email;
        this.role = RoleUser.ADMIN;
        this.operative = true;
    }

    public boolean status_check(){
        return this.operative;
    }

}
