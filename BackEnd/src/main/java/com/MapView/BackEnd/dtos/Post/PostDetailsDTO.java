package com.MapView.BackEnd.dtos.Post;

import com.MapView.BackEnd.entities.Post;

public record PostDetailsDTO(Long id_post, String post) {
    public PostDetailsDTO(Post post){
        this(post.getId_post(), post.getPost());
    }
}
    