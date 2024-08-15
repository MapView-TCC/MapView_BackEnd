package com.MapView.BackEnd.Service;

public interface UserService {
    void getUserService(Long id_UserService);
    void getAllUserService(String UserService);
    void createUserService(String UserService);
    void updateUserService(String UserService);
    void activeUserService(Long id_UserService);
    void inactivateEnviroment(Long id_UserService);
}
