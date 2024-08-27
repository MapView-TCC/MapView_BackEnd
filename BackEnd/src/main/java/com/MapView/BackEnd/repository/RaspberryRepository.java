package com.MapView.BackEnd.repository;

import com.MapView.BackEnd.entities.Raspberry;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RaspberryRepository extends JpaRepository<Raspberry,Long> {
    List<Raspberry> findAllByOperativeTrue(Pageable pageable);
}
