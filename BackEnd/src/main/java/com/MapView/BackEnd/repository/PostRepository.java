package com.MapView.BackEnd.repository;

import com.MapView.BackEnd.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByOperativeTrue();

    Post save (Post data);

    Optional<Post> findByPost(String postname);
}
