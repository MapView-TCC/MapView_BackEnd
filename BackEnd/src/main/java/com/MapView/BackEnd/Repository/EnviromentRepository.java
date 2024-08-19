package com.MapView.BackEnd.Repository;

import com.MapView.BackEnd.entities.Enviroment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnviromentRepository extends JpaRepository<Enviroment,Long> {
}
