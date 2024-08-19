package com.MapView.BackEnd.Repository;

import com.MapView.BackEnd.entities.Raspberry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaspberryRepository extends JpaRepository<Raspberry,Long> {
}
