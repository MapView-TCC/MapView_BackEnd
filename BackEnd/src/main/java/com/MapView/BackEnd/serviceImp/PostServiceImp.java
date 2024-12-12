package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.entities.UserLog;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.enums.EnumAction;
import com.MapView.BackEnd.infra.Exception.ExistingEntityException;
import com.MapView.BackEnd.infra.Exception.OperativeFalseException;
import com.MapView.BackEnd.repository.PostRepository;
import com.MapView.BackEnd.repository.UserLogRepository;
import com.MapView.BackEnd.repository.UserRepository;
import com.MapView.BackEnd.service.PostService;
import com.MapView.BackEnd.dtos.Post.PostCreateDTO;
import com.MapView.BackEnd.dtos.Post.PostDetailsDTO;
import com.MapView.BackEnd.dtos.Post.PostUpdateDTO;
import com.MapView.BackEnd.entities.Post;
import com.MapView.BackEnd.infra.Exception.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImp implements PostService {
    private final PostRepository postRepository;
    private final UserLogRepository userLogRepository;
    private final UserRepository userRepository;


    public PostServiceImp(PostRepository postRepository, UserLogRepository userLogRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userLogRepository = userLogRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PostDetailsDTO getPost(Long id_post, Long userLog_id) {
        Post post = this.postRepository.findById(id_post)
                .orElseThrow(() -> new NotFoundException("Post with ID " + id_post + " not found."));

        if (!post.isOperative()) {
            throw new OperativeFalseException("The post with ID " + id_post + " is inactive and cannot be accessed.");
        }

        Users user = this.userRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found."));

        var userLog = new UserLog(user,"Post",id_post.toString(),"Read Post", EnumAction.READ);
        userLogRepository.save(userLog);

        return new PostDetailsDTO(post);
    }


    public PostDetailsDTO getPostByPost(String postname, Long userLog_id) {
        Post post = this.postRepository.findByPost(postname)
                .orElseThrow(() -> new NotFoundException("Post with the name '" + postname + "' not found."));

        if (!post.isOperative()) {
            throw new OperativeFalseException("The post '" + postname + "' is inactive and cannot be accessed.");
        }

        Users user = this.userRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found."));

        var userLog = new UserLog(user,"Post",post.getId_post().toString(),"Read Post", EnumAction.READ);
        userLogRepository.save(userLog);

        return new PostDetailsDTO(post);
    }

    @Override
    public List<PostDetailsDTO> getAllPost(Long userLog_id) {
        Users user = this.userRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found."));

        var userLog = new UserLog(user,"Post","Read All Post", EnumAction.READ);
        userLogRepository.save(userLog);

        return this.postRepository.findAllByOperativeTrue().stream().map(PostDetailsDTO::new).toList();
    }

    @Override
    public PostDetailsDTO createPost(PostCreateDTO data, Long userLog_id) {
        Users user = this.userRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found."));

        Post verifyPost = postRepository.findByPost(data.post()).orElse(null);
        if (verifyPost == null) {
            try {
                var post = new Post(data);
                Post returnPost = postRepository.save(post);
                Long post_id = returnPost.getId_post();

                var userLog = new UserLog(user,"Area",post_id.toString(),"Create new Area", EnumAction.CREATE);
                userLogRepository.save(userLog);

                return new PostDetailsDTO(returnPost);

            } catch (DataIntegrityViolationException e) {
                throw new ExistingEntityException("Post with the name '" + data.post() + "' already exists.");
            }
        }
        return new PostDetailsDTO(verifyPost);
    }

    @Override
    public PostDetailsDTO updatePost(Long id_post, PostUpdateDTO data, Long userLog_id) {
        var post = this.postRepository.findById(id_post)
                .orElseThrow(() -> new NotFoundException("Post with ID " + id_post + " not found."));

        if (!post.isOperative()) {
            throw new OperativeFalseException("The post with ID " + id_post + " is inactive and cannot be updated.");
        }

        Users user = this.userRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found."));

        var userlog = new UserLog(user, "Area", id_post.toString(), "Updated Info", EnumAction.UPDATE);

        if(data.post() != null){
            post.setPost(data.post());
            userlog.setField("Post name to: " + data.post());
        }

        postRepository.save(post);
        userLogRepository.save(userlog);

        return new PostDetailsDTO(post);
    }

    @Override
    public  void activePost(Long id_post,Long userLog_id) {
        Users user = this.userRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found."));

        Post post = this.postRepository.findById(id_post)
                .orElseThrow(() -> new NotFoundException("Post with ID " + id_post + " not found."));

        post.setOperative(true);
        postRepository.save(post);

        var userLog = new UserLog(user,"Post",id_post.toString(),"Operative","Activated Post",EnumAction.UPDATE);
        userLogRepository.save(userLog);
    }

    @Override
    public void inactivatePost(Long post_id,Long userLog_id) {
        Users user = this.userRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found."));

        Post post = this.postRepository.findById(post_id)
                .orElseThrow(() -> new NotFoundException("Post with ID " + post_id + " not found."));

        post.setOperative(false);
        postRepository.save(post);

        var userLog = new UserLog(user,"Post",post_id.toString(),"Operative","Activated Post",EnumAction.UPDATE);
        userLogRepository.save(userLog);
    }
}
