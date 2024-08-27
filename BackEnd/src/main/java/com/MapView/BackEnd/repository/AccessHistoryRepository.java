package com.MapView.BackEnd.repository;

import com.MapView.BackEnd.entities.AccessHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AccessHistoryRepository extends JpaRepository<AccessHistory,Long> {
    Page<AccessHistory> findAll(Pageable pageable);
}
