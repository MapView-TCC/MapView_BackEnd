package com.MapView.BackEnd.repository;

import com.MapView.BackEnd.entities.Raspberry;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RaspberryRepository extends JpaRepository<Raspberry,String> {
    List<Raspberry> findAllByOperativeTrue();
    Optional<Raspberry> findById(String name);
}
