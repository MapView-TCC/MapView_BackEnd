package com.MapView.BackEnd.repository;

import com.MapView.BackEnd.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRespository extends JpaRepository<Role, Long> {
    Optional<Role> findByName (String name);
}
