package com.MapView.BackEnd.repository;

import com.MapView.BackEnd.entities.Permission;
import com.MapView.BackEnd.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<List<Permission>> findByRole (Role role);
}
