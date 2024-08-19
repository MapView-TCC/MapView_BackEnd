package com.MapView.BackEnd.Repository;

import com.MapView.BackEnd.dtos.User.UserCreateDTO;
import com.MapView.BackEnd.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    List<Users>findByOperativeTrue();
    void save(UserCreateDTO data);


}

