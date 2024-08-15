package com.MapView.BackEnd.Service;

public interface ResposibleService {
    void getResposibleService(Long id_ResposibleService);
    void getAllResposibleService(String ResposibleService);
    void createResposibleService(String ResposibleService);
    void updateResposibleService(String ResposibleService);
    void activeResposibleService(Long id_ResposibleService);
    void inactivateEnviroment(Long id_ResposibleService);
}
