package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.repository.PostRepository;
import com.MapView.BackEnd.service.PostService;
import com.MapView.BackEnd.dtos.Post.PostCreateDTO;
import com.MapView.BackEnd.dtos.Post.PostDetailDTO;
import com.MapView.BackEnd.dtos.Post.PostUpdateDTO;
import com.MapView.BackEnd.entities.Post;
import com.MapView.BackEnd.infra.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImp implements PostService {
    private final PostRepository postRepository;

    public PostServiceImp(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDetailDTO getPost(Long id_post) {
        Post post = this.postRepository.findById(id_post).orElseThrow(() -> new NotFoundException("Post id Not Found"));
        if(!post.post_status_check()){
            return null;
        }
        return new PostDetailDTO(post);
    }

    @Override
    public List<PostDetailDTO> getAllPost(int page, int itens) {
        return this.postRepository.findByOperativeTrue(PageRequest.of(page, itens)).stream().map(PostDetailDTO::new).toList();


    }

    @Override
    public PostDetailDTO createPost(PostCreateDTO data) {
        var post = new Post(data);
        postRepository.save(post);
        return new PostDetailDTO(post);
    }

    @Override
    public PostDetailDTO updatePost(Long id_post, PostUpdateDTO data) {
        var post = this.postRepository.findById(id_post).orElseThrow(() ->new NotFoundException("Post Id Not Found"));
        if (!post.post_status_check()){
            return null;
        }
        post.setPost(data.post());
        postRepository.save(post);
        return new PostDetailDTO(post);
    }

    @Override
    public  void activePost(Long id_post) {
        Post post = this.postRepository.findById(id_post).orElseThrow(() -> new NotFoundException("Post Id Not Found"));
        post.setOperative(true);
        postRepository.save(post);

    }

    @Override
    public void inactivatePost(Long id_Post) {
        Post post = this.postRepository.findById(id_Post).orElseThrow(() -> new NotFoundException("Post Id Not Found"));
        post.setOperative(false);
        postRepository.save(post);

    }
}
