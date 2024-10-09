package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.serviceImp.PostServiceImp;
import com.MapView.BackEnd.dtos.Post.PostCreateDTO;
import com.MapView.BackEnd.dtos.Post.PostDetailDTO;
import com.MapView.BackEnd.dtos.Post.PostUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
@Tag(name = "Post", description = "Operations related to post management")
public class PostController {

    private final PostServiceImp postServiceImp;

    public PostController(PostServiceImp postServiceImp) {
        this.postServiceImp = postServiceImp;
    }

    @Operation(summary = "Create a new post", description = "Endpoint to create a new post in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Post successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostDetailDTO.class))),
            @ApiResponse(responseCode = "400", description = "Data validation error"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    @Transactional
    public ResponseEntity<PostDetailDTO> createPost(
            @Parameter(description = "Data transfer object for creating a new post", required = true)
            @RequestBody @Valid PostCreateDTO data,
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id,
            UriComponentsBuilder uriBuilder) {
        PostDetailDTO post = postServiceImp.createPost(data, userLog_id);
        var uri = uriBuilder.path("/api/v1/post/{id}").buildAndExpand(post.id_post()).toUri();
        return ResponseEntity.created(uri).body(new PostDetailDTO(post.id_post(), post.post()));
    }

    @Operation(summary = "Retrieve a post by ID", description = "Get the details of a post by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @GetMapping("/{id_post}")
    public ResponseEntity<PostDetailDTO> getPost(
            @Parameter(description = "The ID of the post to retrieve", required = true)
            @PathVariable("id_post") Long id_post,
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id) {
        var post = postServiceImp.getPost(id_post, userLog_id);
        return ResponseEntity.ok(post);
    }

    @Operation(summary = "Retrieve all posts", description = "Get a paginated list of all posts in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posts list successfully retrieved")
    })
    @GetMapping
    public ResponseEntity<List<PostDetailDTO>> getAllPost(
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id) {
        var post = postServiceImp.getAllPost(userLog_id);
        return ResponseEntity.ok(post);
    }

    @Operation(summary = "Update a post", description = "Update the details of a post by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post successfully updated"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @PutMapping("/{id_post}")
    @Transactional
    public ResponseEntity<PostDetailDTO> updatePost(
            @Parameter(description = "The ID of the post to update", required = true)
            @PathVariable("id_post") Long id_post,
            @Parameter(description = "Data transfer object for updating the post", required = true)
            @RequestBody @Valid PostUpdateDTO data,
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id) {
        var post = postServiceImp.updatePost(id_post, data, userLog_id);
        return ResponseEntity.ok(post);
    }

    @Operation(summary = "Activate a post", description = "Activate a post by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post successfully activated"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @PutMapping("/active/{id_post}")
    @Transactional
    public ResponseEntity<Void> activePost(
            @Parameter(description = "The ID of the post to activate", required = true)
            @PathVariable("id_post") Long id_post,
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id) {
        postServiceImp.activePost(id_post, userLog_id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Inactivate a post", description = "Inactivate a post by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post successfully inactivated"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @PutMapping("/inactivate/{id_post}")
    @Transactional
    public ResponseEntity<Void> inactivatePost(
            @Parameter(description = "The ID of the post to inactivate", required = true)
            @PathVariable("id_post") Long id_post,
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id) {
        postServiceImp.inactivatePost(id_post, userLog_id);
        return ResponseEntity.ok().build();
    }
}
