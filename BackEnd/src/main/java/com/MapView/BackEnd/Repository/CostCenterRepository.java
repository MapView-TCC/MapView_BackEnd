package com.MapView.BackEnd.Repository;

import com.MapView.BackEnd.entities.CostCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CostCenterRepository extends JpaRepository<CostCenter,Long> {
    List<CostCenter> findAllByOperativeTrue();
}
