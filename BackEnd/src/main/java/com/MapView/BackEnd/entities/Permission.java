package com.MapView.BackEnd.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "permission")
@Table(name = "permission")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Permission {


    private Long id_permission;
    @Column(name = "id_user")
    @OneToOne
    private Users users;
    @Column(name = "id_role")
    @ManyToOne
    private Role role;
    private LocalDate date;

    public Permission(Users users,Role role){
        this.users = users;
        this.role = role;
        this.date = LocalDate.now();

    }


}
