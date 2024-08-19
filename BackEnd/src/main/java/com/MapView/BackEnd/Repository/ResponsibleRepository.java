package com.MapView.BackEnd.Repository;

import com.MapView.BackEnd.entities.Responsible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponsibleRepository extends JpaRepository<Responsible,Long> {
}
