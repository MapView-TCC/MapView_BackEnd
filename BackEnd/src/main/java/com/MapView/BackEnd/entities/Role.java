package com.MapView.BackEnd.entities;

import com.MapView.BackEnd.dtos.Role.RoleCreateDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "role",fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Users> users;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Permission> permissoes;



    public Role(RoleCreateDTO data) {
        this.name = data.role_name();
    }
}