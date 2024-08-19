package com.MapView.BackEnd.Repository;

import com.MapView.BackEnd.entities.CostCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostCernterRepository extends JpaRepository<CostCenter,Long> {
}
