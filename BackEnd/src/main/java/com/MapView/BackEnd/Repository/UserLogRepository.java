package com.MapView.BackEnd.Repository;

import com.MapView.BackEnd.entities.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLogRepository extends JpaRepository<UserLog,Long> {
}
