package com.MapView.BackEnd.Repository;

import com.MapView.BackEnd.dtos.Responsible.ResponsibleDetailsDTO;
import com.MapView.BackEnd.entities.Responsible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResponsibleRepository extends JpaRepository<Responsible,Long> {
    List<Responsible> findByOperativeTrue();
}
