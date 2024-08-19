package com.MapView.BackEnd.Service;


import com.MapView.BackEnd.entities.Users;

import java.time.LocalDateTime;

public interface AccessHistoryService {

    String createAcess(String valo, Double number);

    void post(Users id_user, LocalDateTime login_datetime);

}
