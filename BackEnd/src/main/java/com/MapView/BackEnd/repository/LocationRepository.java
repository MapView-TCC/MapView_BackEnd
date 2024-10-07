package com.MapView.BackEnd.repository;

import com.MapView.BackEnd.dtos.Location.LocationCreateDTO;
import com.MapView.BackEnd.entities.Environment;
import com.MapView.BackEnd.entities.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location,Long> {
    Page<Location> findAll(Pageable pageable);
    Optional<Location> findByEnvironment(Environment environment);

    Location save (LocationCreateDTO data);
}
