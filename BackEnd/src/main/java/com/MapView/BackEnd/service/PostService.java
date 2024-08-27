package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.Post.PostCreateDTO;
import com.MapView.BackEnd.dtos.Post.PostDetailDTO;
import com.MapView.BackEnd.dtos.Post.PostUpdateDTO;

import java.util.List;

public interface PostService {

    PostDetailDTO getPost(Long id_post);
    List<PostDetailDTO> getAllPost(int page, int itens);
    PostDetailDTO createPost(PostCreateDTO data);
    PostDetailDTO updatePost(Long id, PostUpdateDTO data);
    void activePost(Long id_post);
    void inactivatePost(Long id_Post);

}
