package com.MapView.BackEnd.Service;

import com.MapView.BackEnd.entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface AccessHistoryService {

    String cadastroalgo(String valo, Double number);

    void post(User id_user, LocalDateTime login_datetime);

}
