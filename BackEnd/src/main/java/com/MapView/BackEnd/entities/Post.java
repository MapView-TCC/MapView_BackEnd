package com.MapView.BackEnd.entities;

import com.MapView.BackEnd.dtos.Post.PostCreateDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.security.PublicKey;
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
    private boolean operative;
    @ManyToMany(mappedBy = "environments")
    @JsonBackReference
    private Set<Enviroment> posts;

    public Post(PostCreateDTO data){
        this.post = data.post();
        this.operative = true;

    }
}
