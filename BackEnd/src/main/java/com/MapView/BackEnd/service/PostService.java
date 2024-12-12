package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.Post.PostCreateDTO;
import com.MapView.BackEnd.dtos.Post.PostDetailsDTO;
import com.MapView.BackEnd.dtos.Post.PostUpdateDTO;

import java.util.List;

public interface PostService {

    PostDetailsDTO getPost(Long id_post, Long userLog_id);
    List<PostDetailsDTO> getAllPost(Long userLog_id);
    PostDetailsDTO createPost(PostCreateDTO data, Long userLog_id);
    PostDetailsDTO updatePost(Long id, PostUpdateDTO data, Long userLog_id);
    void activePost(Long id_post,Long userLog_id);
    void inactivatePost(Long id_Post,Long userLog_id);

}
