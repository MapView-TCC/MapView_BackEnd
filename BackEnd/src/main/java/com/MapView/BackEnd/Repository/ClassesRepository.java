package com.MapView.BackEnd.Repository;

import com.MapView.BackEnd.entities.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ClassesRepository extends JpaRepository<Classes,Long> {
}
