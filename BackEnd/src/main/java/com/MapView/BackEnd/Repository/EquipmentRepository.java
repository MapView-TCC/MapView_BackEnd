package com.MapView.BackEnd.Repository;

import com.MapView.BackEnd.entities.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment,String> {
}
