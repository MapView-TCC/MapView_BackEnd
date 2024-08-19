package com.MapView.BackEnd.Repository;

import com.MapView.BackEnd.entities.MainOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainOwnerRepository extends JpaRepository<MainOwner,String> {
}
