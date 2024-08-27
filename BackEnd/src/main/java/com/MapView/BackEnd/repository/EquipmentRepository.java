package com.MapView.BackEnd.repository;

import com.MapView.BackEnd.entities.Equipment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment,String> {
    List<Equipment> findAllByOperativeTrue(Pageable pageable);

}
