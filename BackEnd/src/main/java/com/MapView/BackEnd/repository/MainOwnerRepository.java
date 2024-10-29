package com.MapView.BackEnd.repository;

import com.MapView.BackEnd.entities.MainOwner;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MainOwnerRepository extends JpaRepository<MainOwner,Long> {
    List<MainOwner> findAllByOperativeTrue();
}
