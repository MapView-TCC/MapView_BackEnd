package com.MapView.BackEnd.Repository;

import com.MapView.BackEnd.entities.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AreaRepository extends JpaRepository<Area,Long> {
}
