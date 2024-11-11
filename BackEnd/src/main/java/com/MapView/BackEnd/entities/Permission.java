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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_permission;
    @JoinColumn(name = "id_user")
    @OneToOne
    private Users users;
    @ManyToOne
    @JoinColumn(name = "id_role")

    private Role role;
    private LocalDate date;

    public Permission(Users users,Role role){
        this.users = users;
        this.role = role;
        this.date = LocalDate.now();

    }


}
