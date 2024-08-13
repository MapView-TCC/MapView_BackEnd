package com.MapView.BackEnd.Repository;

import com.MapView.BackEnd.entities.AccessHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AccessHistoryRepository extends JpaRepository<AccessHistory,Long> {
}
