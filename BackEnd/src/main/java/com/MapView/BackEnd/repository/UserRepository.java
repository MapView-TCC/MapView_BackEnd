package com.MapView.BackEnd.repository;

import com.MapView.BackEnd.dtos.User.UserCreateDTO;
import com.MapView.BackEnd.entities.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    List<Users>findByOperativeTrue();
    Optional<Users> findByEmail(String email);
    void save(UserCreateDTO data);


}

