package com.MapView.BackEnd.Repository;

import com.MapView.BackEnd.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location,Long> {
}
