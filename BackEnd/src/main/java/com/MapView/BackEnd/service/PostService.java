package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.Post.PostCreateDTO;
import com.MapView.BackEnd.dtos.Post.PostDetailDTO;
import com.MapView.BackEnd.dtos.Post.PostUpdateDTO;

import java.util.List;

public interface PostService {

    PostDetailDTO getPost(Long id_post,Long user_id);
    List<PostDetailDTO> getAllPost(int page, int itens,Long user_id);
    PostDetailDTO createPost(PostCreateDTO data,Long user_id);
    PostDetailDTO updatePost(Long id, PostUpdateDTO data,Long user_id);
    void activePost(Long id_post,Long user_id);
    void inactivatePost(Long id_Post,Long user_id);

}
