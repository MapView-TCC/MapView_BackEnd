package com.MapView.BackEnd.repository;

import com.MapView.BackEnd.entities.Permission;
import com.MapView.BackEnd.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<List<Permission>> findByRole (Role role);
}
