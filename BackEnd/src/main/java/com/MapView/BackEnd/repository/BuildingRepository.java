package com.MapView.BackEnd.repository;

import com.MapView.BackEnd.entities.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface BuildingRepository extends JpaRepository<Building,Long> {
    List<Building> findAllByOperativeTrue();
}
