package com.MapView.BackEnd.Repository;

import com.MapView.BackEnd.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
