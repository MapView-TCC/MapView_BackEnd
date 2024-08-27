package com.MapView.BackEnd.repository;

import com.MapView.BackEnd.entities.Responsible;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResponsibleRepository extends JpaRepository<Responsible,Long> {
    List<Responsible> findByOperativeTrue(Pageable pageable);
}
