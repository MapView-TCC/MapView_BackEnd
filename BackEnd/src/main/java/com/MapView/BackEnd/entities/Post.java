package com.MapView.BackEnd.entities;

import com.MapView.BackEnd.dtos.Post.PostCreateDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Table(name = "post")
@Entity(name = "post")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id_post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_post;
    private String post;
    @JsonIgnore
    private boolean operative;
    @ManyToMany(mappedBy = "posts")
    @JsonBackReference
    private Set<Environment> posts;

    public Post(PostCreateDTO data){
        this.post = data.post();
        this.operative = true;

    }
    public Post(String post_name){
        this.post = post_name;
        this.operative = true;

    }
}
