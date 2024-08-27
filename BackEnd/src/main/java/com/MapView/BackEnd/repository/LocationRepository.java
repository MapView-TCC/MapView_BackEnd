package com.MapView.BackEnd.repository;

import com.MapView.BackEnd.entities.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location,Long> {
    Page<Location> findAll(Pageable pageable);
}
