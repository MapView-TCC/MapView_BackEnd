package com.MapView.BackEnd.Service;

import com.MapView.BackEnd.dtos.Post.PostCreateDTO;
import com.MapView.BackEnd.dtos.Post.PostDetailDTO;
import com.MapView.BackEnd.dtos.Post.PostUpdateDTO;
import com.MapView.BackEnd.entities.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public interface PostService {

    PostDetailDTO getPost(Long id_post);
    List<PostDetailDTO> getAllPost();
    PostDetailDTO createPost(PostCreateDTO data);
    PostDetailDTO updatePost(Long id, PostUpdateDTO data);
    void activePost(Long id_post);
    void inactivatePost(Long id_Post);

}
