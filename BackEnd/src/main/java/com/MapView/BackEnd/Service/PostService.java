package com.MapView.BackEnd.Service;

public interface PostService {

    void getPost(Long id_post);
    void getAllPost(String post);
    void createPsot(String post);
    void updatePost(String post);
    void activePost(Long id_post);
    void inactivateEnviroment(Long id_Post);

}
