package com.MapView.BackEnd.Controller;

import com.MapView.BackEnd.Service.PostService;
import com.MapView.BackEnd.ServiceImp.PostServiceImp;
import com.MapView.BackEnd.dtos.Post.PostCreateDTO;
import com.MapView.BackEnd.dtos.Post.PostDetailDTO;
import com.MapView.BackEnd.dtos.Post.PostUpdateDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/ap1/v1/post")

public class PostController {

    private final PostServiceImp postServiceImp;

    public PostController(PostServiceImp postServiceImp) {
        this.postServiceImp = postServiceImp;
    }


    @PostMapping
    @Transactional
    public ResponseEntity<PostDetailDTO> createPost(@RequestBody PostCreateDTO data, UriComponentsBuilder uriBuilder){
        PostDetailDTO post = postServiceImp.createPost(data);

        var uri = uriBuilder.path("/api/v1/post/{id}").buildAndExpand(post.id_post()).toUri();
        return ResponseEntity.created(uri).body(new PostDetailDTO(post.id_post(), post.post()));



    }
    @GetMapping("/{id_post}")
    public ResponseEntity<PostDetailDTO> getPost(@PathVariable("id_post") Long id_post){
        var post = postServiceImp.getPost(id_post);
        return ResponseEntity.ok(post);


    }
    @GetMapping
    public ResponseEntity<List<PostDetailDTO>> getAllPost(){
        var post = postServiceImp.getAllPost();
        return ResponseEntity.ok(post);

    }

    @PutMapping("/{id_post}")
    @Transactional
    public ResponseEntity<PostDetailDTO> updatePost (@PathVariable("id_post") Long id_post, @RequestBody PostUpdateDTO data){
         var post =  postServiceImp.updatePost(id_post,data);
         return ResponseEntity.ok(post);

    }
    @PutMapping("/active/{id_post}")
    @Transactional
    public ResponseEntity<Void>activePost(@PathVariable("id_post") Long id_post) {
         postServiceImp.activePost(id_post);
         return ResponseEntity.ok().build();


    }
    @PutMapping("/deactive/{id_post}")
    @Transactional
    public ResponseEntity<Void> inactivateEnviroment(@PathVariable("id_post") Long id_Post) {
        postServiceImp.inactivatePost(id_Post);
        return ResponseEntity.ok().build();


    }
}
