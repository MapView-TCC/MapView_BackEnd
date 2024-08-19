package com.MapView.BackEnd.Repository;

import com.MapView.BackEnd.entities.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface BuildingRepository extends JpaRepository<Building,Long> {
}
