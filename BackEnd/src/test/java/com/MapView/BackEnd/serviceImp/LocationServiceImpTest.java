package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.Location.LocationCreateDTO;
import com.MapView.BackEnd.dtos.Location.LocationDetailsDTO;
import com.MapView.BackEnd.dtos.Location.LocationUpdateDTO;
import com.MapView.BackEnd.entities.Environment;
import com.MapView.BackEnd.entities.Location;
import com.MapView.BackEnd.entities.Post;
import com.MapView.BackEnd.repository.*;
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
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test") // configurar o banco de dados H2
@Transactional
public class LocationServiceImpTest {

    @InjectMocks
    private LocationServiceImp locationServiceImp;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private EnvironmentRepository environmentRepository;

    @Mock
    private PostRepository postRepository;

    @Test
    void testGetLocation() {
        Long postId = 1L;
        Long environmentId = 1L;
        Long locationId = 1L;

        Environment environment = new Environment();
        environment.setId_environment(environmentId);
        environment.setEnvironment_name("Sala 2");
        when(environmentRepository.findById(environmentId)).thenReturn(Optional.of(environment));

        Post post = new Post();
        post.setId_post(postId);
        post.setPost("Mesa 21");
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        Location location = new Location();
        location.setId_location(locationId);
        location.setEnvironment(environment);
        location.setPost(post);
        when(locationRepository.findById(locationId)).thenReturn(Optional.of(location));

        LocationDetailsDTO result = locationServiceImp.getLocation(locationId);

        assertNotNull(result);
        assertEquals(locationId, result.id_location());
    }

    @Test
    void testGetAllLocation() {
        Long postId = 1L;
        Long environmentId = 1L;
        Long locationId = 1L;

        Environment environment = new Environment();
        environment.setId_environment(environmentId);
        environment.setEnvironment_name("Sala 2");
        when(environmentRepository.findById(environmentId)).thenReturn(Optional.of(environment));

        Post post = new Post();
        post.setId_post(postId);
        post.setPost("Mesa 21");
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        Location location = new Location();
        location.setId_location(locationId);
        location.setEnvironment(environment);
        location.setPost(post);
        when(locationRepository.findAll()).thenReturn(Collections.singletonList(location));

        List<LocationDetailsDTO> result = locationServiceImp.getAllLocation();

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testCreateLocation() {
        Long postId = 1L;
        Long environmentId = 1L;
        Long locationId = 1L;

        Environment environment = new Environment();
        environment.setId_environment(environmentId);
        environment.setEnvironment_name("Sala 2");
        environment.setOperative(true);
        when(environmentRepository.findById(environmentId)).thenReturn(Optional.of(environment));

        Post post = new Post();
        post.setId_post(postId);
        post.setPost("Mesa 21");
        post.setOperative(true);
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        LocationCreateDTO createDTO = new LocationCreateDTO(postId, environmentId);

        Location location = new Location();
        location.setId_location(locationId);
        location.setEnvironment(environment);
        location.setPost(post);
        when(locationRepository.save(any(Location.class))).thenReturn(location);

        LocationDetailsDTO result = locationServiceImp.createLocation(createDTO);
        System.out.println(result);

        // Asserções
        assertNotNull(result);
        assertEquals(locationId, result.id_location());
        assertEquals("Sala 2", result.environment().getEnvironment_name());
        assertEquals("Mesa 21", result.post().getPost());
    }

    @Test
    void testUpdateLocation() {
        Long postId = 1L;
        Long environmentId = 1L;
        Long locationId = 1L;
        Long postId1 = 2L;
        Long environmentId1 = 2L;

        // Setup do ambiente
        Environment environment = new Environment();
        environment.setId_environment(environmentId);
        environment.setEnvironment_name("Sala 2");
        environment.setOperative(true);
        when(environmentRepository.findById(environmentId)).thenReturn(Optional.of(environment));

        // Setup do posto
        Post post = new Post();
        post.setId_post(postId);
        post.setPost("Mesa 21");
        post.setOperative(true);
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // Setup do ambiente atualizado
        Environment updatedEnvironment = new Environment();
        updatedEnvironment.setId_environment(environmentId1);
        updatedEnvironment.setEnvironment_name("Sala 3");
        updatedEnvironment.setOperative(true);
        when(environmentRepository.findById(environmentId1)).thenReturn(Optional.of(updatedEnvironment));

        // Setup do posto atualizado
        Post updatedPost = new Post();
        updatedPost.setId_post(postId1);
        updatedPost.setPost("Mesa 22");
        updatedPost.setOperative(true);
        when(postRepository.findById(postId1)).thenReturn(Optional.of(updatedPost));

        // Setup da localização existente
        Location location = new Location();
        location.setId_location(locationId);
        location.setEnvironment(environment);
        location.setPost(post);
        when(locationRepository.findById(locationId)).thenReturn(Optional.of(location));

        // Criando o DTO de atualização
        LocationUpdateDTO updateDTO = new LocationUpdateDTO(postId1, environmentId1);

        // Chamada ao método de atualização
        LocationDetailsDTO result = locationServiceImp.updateLocation(locationId, updateDTO);
        System.out.println(result);

        // Asserções
        assertNotNull(result);
        assertEquals(locationId, result.id_location());
        assertEquals(postId1, result.post().getId_post());
        assertEquals(environmentId1, result.environment().getId_environment());
    }

}
