package com.MapView.BackEnd.Service;

public interface UserLogService {
    void getUserLogService(Long id_UserLogService);
    void getAllUserLogService(String UserLogService);
    void createUserLogService(String UserLogService);
    void updateUserLogService(String UserLogService);
    void activeUserLogService(Long id_UserLogService);
    void inactivateEnviroment(Long id_UserLogService);

}
