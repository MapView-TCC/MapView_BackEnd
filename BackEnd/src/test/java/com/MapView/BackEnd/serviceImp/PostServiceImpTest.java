package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.Post.PostCreateDTO;
import com.MapView.BackEnd.dtos.Post.PostDetailsDTO;
import com.MapView.BackEnd.dtos.Post.PostUpdateDTO;
import com.MapView.BackEnd.entities.Post;
import com.MapView.BackEnd.entities.UserLog;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.infra.Exception.NotFoundException;
import com.MapView.BackEnd.repository.PostRepository;
import com.MapView.BackEnd.repository.UserLogRepository;
import com.MapView.BackEnd.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test") // configurar o banco de dados H2
@Transactional
public class PostServiceImpTest {

    @InjectMocks
    private PostServiceImp postServiceImp;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserLogRepository userLogRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void testGetPost() {
        Long userLogId = 1L;
        Long postId = 1L;

        Users users = new Users();
        users.setId_user(userLogId);
        users.setOperative(true);
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(users));

        Post post = new Post();
        post.setId_post(postId);
        post.setPost("Mesa 26");
        post.setOperative(true);
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        PostDetailsDTO result = postServiceImp.getPost(post.getId_post(), userLogId);
        System.out.println(result);

        assertNotNull(result);
        assertEquals(postId, result.id_post());
        assertEquals("Mesa 26", result.post());

        verify(userLogRepository).save(any(UserLog.class));

    }

    @Test
    void testGetPostByPost() {
        String postName = "TestPost";
        Long userLogId = 1L;

        // Criando um usuário mock
        Users user = new Users();
        user.setId_user(userLogId);
        user.setOperative(true);

        // Criando um post mock
        Post post = new Post();
        post.setId_post(1L);
        post.setPost(postName);
        post.setOperative(true);

        // Configurando os mocks
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));
        when(postRepository.findByPost(postName)).thenReturn(Optional.of(post));

        PostDetailsDTO result = postServiceImp.getPostByPost(postName, userLogId);

        assertNotNull(result, "The result should not be null");
        assertEquals(post.getId_post(), result.id_post(), "The post ID should match");
        assertEquals(postName, result.post(), "The post name should match");

        verify(userLogRepository).save(any(UserLog.class));
    }

    @Test
    void testGetAllPost() {
        Long userLogId = 1L;
        Long postId = 1L;

        Users users = new Users();
        users.setId_user(userLogId);
        users.setOperative(true);
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(users));

        Post post = new Post();
        post.setId_post(postId);
        post.setPost("Mesa 26");
        post.setOperative(true);
        when(postRepository.findAllByOperativeTrue()).thenReturn(Collections.singletonList(post));

        List<PostDetailsDTO> result = postServiceImp.getAllPost(userLogId);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Mesa 26", result.get(0).post());

        verify(userLogRepository).save(any(UserLog.class));
    }

    @Test
    void testCreatePost() {
        Long userLogId = 1L;
        Long postId = 1L;

        Users users = new Users();
        users.setId_user(userLogId);
        users.setOperative(true);
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(users));

        PostCreateDTO createDTO = new PostCreateDTO("Mesa 26");

        Post post = new Post();
        post.setId_post(postId);
        post.setPost(createDTO.post());
        post.setOperative(true);
        when(postRepository.save(any(Post.class))).thenReturn(post);

        PostDetailsDTO result = postServiceImp.createPost(createDTO, userLogId);

        assertNotNull(result);
        assertEquals(postId, result.id_post());
        assertEquals("Mesa 26", result.post());

        verify(userLogRepository).save(any(UserLog.class));
    }

    @Test
    void testUpdatePost() {
        Long userLogId = 1L;
        Long postId = 1L;

        Users users = new Users();
        users.setId_user(userLogId);
        users.setOperative(true);
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(users));

        Post post = new Post();
        post.setId_post(postId);
        post.setPost("Mesa 26");
        post.setOperative(true);
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        PostUpdateDTO updateDTO = new PostUpdateDTO("Mesa 5");

        PostDetailsDTO result = postServiceImp.updatePost(postId, updateDTO, userLogId);

        assertNotNull(result);
        assertEquals("Mesa 5", result.post());

        verify(userLogRepository).save(any(UserLog.class));
    }

    @Test
    void testActivePost() {
        Long userLogId = 1L;
        Long postId = 1L;

        // Criação de um usuário mock
        Users user = new Users();
        user.setId_user(userLogId);
        user.setOperative(true);
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        // Criação de um post mock
        Post post = new Post();
        post.setId_post(postId);
        post.setOperative(false); // Post inicialmente inativo
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // Executando o método a ser testado
        postServiceImp.activePost(postId, userLogId);

        assertTrue(post.isOperative(), "Post should be active");
        verify(postRepository).save(post);
        verify(userLogRepository).save(any(UserLog.class));
    }

    @Test
    void testInactivatePost() {
        Long userLogId = 1L;
        Long postId = 1L;

        // Criação de um usuário mock
        Users user = new Users();
        user.setId_user(userLogId);
        user.setOperative(true);
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        // Criação de um post mock
        Post post = new Post();
        post.setId_post(postId);
        post.setOperative(true); // Post inicialmente ativo
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // Executando o método a ser testado
        postServiceImp.inactivatePost(postId, userLogId);

        assertFalse(post.isOperative(), "Post should be inactive");
        verify(postRepository).save(post);
        verify(userLogRepository).save(any(UserLog.class));
    }

    @Test
    void testActivePost_UserNotFound() {
        Long userLogId = 1L;
        Long postId = 1L;

        // Simula o caso em que o usuário não é encontrado
        when(userRepository.findById(userLogId)).thenReturn(Optional.empty());

        // Verifica se uma NotFoundException é lançada
        assertThrows(NotFoundException.class, () -> {
            postServiceImp.activePost(postId, userLogId);
        });
    }

    @Test
    void testInactivatePost_PostNotFound() {
        Long userLogId = 1L;
        Long postId = 1L;

        // Criação de um usuário mock
        Users user = new Users();
        user.setId_user(userLogId);
        user.setOperative(true);
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        // Simula o caso em que o post não é encontrado
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // Verifica se uma NotFoundException é lançada
        assertThrows(NotFoundException.class, () -> {
            postServiceImp.inactivatePost(postId, userLogId);
        });
    }
}