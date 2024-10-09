package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.Post.PostCreateDTO;
import com.MapView.BackEnd.dtos.Post.PostDetailDTO;
import com.MapView.BackEnd.dtos.Post.PostUpdateDTO;

import java.util.List;

public interface PostService {

    PostDetailDTO getPost(Long id_post,Long userLog_id);
    List<PostDetailDTO> getAllPost(Long userLog_id);
    PostDetailDTO createPost(PostCreateDTO data,Long userLog_id);
    PostDetailDTO updatePost(Long id, PostUpdateDTO data,Long userLog_id);
    void activePost(Long id_post,Long userLog_id);
    void inactivatePost(Long id_Post,Long userLog_id);

}
