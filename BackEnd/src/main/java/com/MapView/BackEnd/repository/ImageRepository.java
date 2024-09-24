package com.MapView.BackEnd.repository;

import com.MapView.BackEnd.entities.Image;
import com.MapView.BackEnd.enums.EnumModelEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByModel(EnumModelEquipment image);
}
