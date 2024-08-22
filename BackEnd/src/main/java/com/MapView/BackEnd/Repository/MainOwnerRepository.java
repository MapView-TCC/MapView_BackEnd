package com.MapView.BackEnd.Repository;

import com.MapView.BackEnd.entities.CostCenter;
import com.MapView.BackEnd.entities.MainOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MainOwnerRepository extends JpaRepository<MainOwner,String> {
    List<MainOwner> findAllByOperativeTrue();
}
