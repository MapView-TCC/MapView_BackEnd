package com.MapView.BackEnd.entities;

import com.MapView.BackEnd.Dtos.User.UserCreateDTO;
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
    @Enumerated
    @Column(nullable = false)
    private RoleUser role;

    @Column(nullable = false)
    private boolean operative;



    public Users(UserCreateDTO data){
        this.email = data.email();
        this.role = RoleUser.USER;
        this.operative = true;
    }

}
