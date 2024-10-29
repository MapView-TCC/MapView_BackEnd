package com.MapView.BackEnd.repository;

import com.MapView.BackEnd.entities.Environment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnvironmentRepository extends JpaRepository<Environment,Long> {
    List<Environment> findEnvironmentByOperativeTrue();

}
