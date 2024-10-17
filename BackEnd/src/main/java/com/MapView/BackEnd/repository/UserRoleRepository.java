package com.MapView.BackEnd.repository;

import com.MapView.BackEnd.entities.UserRole;
import com.MapView.BackEnd.entities.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
    Optional<UserRole> findByUser(Users users);

}
