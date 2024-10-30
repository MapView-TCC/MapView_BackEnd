package com.MapView.BackEnd.repository;

import com.MapView.BackEnd.dtos.Equipment.EquipmentDetailsDTO;
import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.Location;
import com.MapView.BackEnd.enums.EnumModelEquipment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment,Long> {
    List<Equipment> findAllByOperativeTrue(Pageable pageable);
    List<Equipment> findAllByOperativeTrue();
    Optional<Equipment> findByCodeAndOperativeTrue(String id);
    Optional<Equipment> findByValidity(LocalDate validity);
    List<Equipment> findByModel(EnumModelEquipment equipment);
    Optional<Equipment> findByRfid(Long rfid);
    Optional<Equipment> findByCode(String code);

    boolean existsByLocation(Location location);

}
