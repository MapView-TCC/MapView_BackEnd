package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.serviceImp.PostServiceImp;
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
    public ResponseEntity<PostDetailDTO> createPost(@RequestBody PostCreateDTO data,@RequestParam Long userLog_id, UriComponentsBuilder uriBuilder){
        PostDetailDTO post = postServiceImp.createPost(data,userLog_id);

        var uri = uriBuilder.path("/api/v1/post/{id}").buildAndExpand(post.id_post()).toUri();
        return ResponseEntity.created(uri).body(new PostDetailDTO(post.id_post(), post.post()));
    }

    @GetMapping("/{id_post}")
    public ResponseEntity<PostDetailDTO> getPost(@PathVariable("id_post") Long id_post,@RequestParam Long userLog_id){
        var post = postServiceImp.getPost(id_post,userLog_id);
        return ResponseEntity.ok(post);
    }
    @GetMapping
    public ResponseEntity<List<PostDetailDTO>> getAllPost(@RequestParam int page, @RequestParam int itens,@RequestParam Long userLog_id){
        var post = postServiceImp.getAllPost(page, itens,userLog_id);
        return ResponseEntity.ok(post);

    }

    @PutMapping("/{id_post}")
    @Transactional
    public ResponseEntity<PostDetailDTO> updatePost (@PathVariable("id_post") Long id_post, @RequestBody @Valid PostUpdateDTO data, @RequestParam Long userLog_id){
         var post =  postServiceImp.updatePost(id_post,data,userLog_id);
         return ResponseEntity.ok(post);

    }
    @PutMapping("/active/{id_post}")
    @Transactional
    public ResponseEntity<Void>activePost(@PathVariable("id_post") Long id_post,@RequestParam Long userLog_id) {
         postServiceImp.activePost(id_post,userLog_id);
         return ResponseEntity.ok().build();
    }
    @PutMapping("/inactivate/{id_post}")
    @Transactional
    public ResponseEntity<Void> inactivateEnviroment(@PathVariable("id_post") Long id_Post,@RequestParam Long userLog_id) {
        postServiceImp.inactivatePost(id_Post,userLog_id);
        return ResponseEntity.ok().build();


    }
}
