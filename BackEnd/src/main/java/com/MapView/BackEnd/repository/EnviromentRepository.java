package com.MapView.BackEnd.repository;

import com.MapView.BackEnd.entities.Enviroment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnviromentRepository extends JpaRepository<Enviroment,Long> {
    List<Enviroment> findEnviromentByOperativeTrue(Pageable pageable);
}