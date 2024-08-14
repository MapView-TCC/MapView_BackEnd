package com.MapView.BackEnd.Repository;

import com.MapView.BackEnd.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
