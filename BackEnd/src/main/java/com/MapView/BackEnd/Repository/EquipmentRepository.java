package com.MapView.BackEnd.Repository;

import com.MapView.BackEnd.entities.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment,String> {
}
