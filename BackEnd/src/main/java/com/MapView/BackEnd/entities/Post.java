package com.MapView.BackEnd.entities;

import com.MapView.BackEnd.dtos.Post.PostCreateDTO;
import jakarta.persistence.*;
import lombok.*;

import java.security.PublicKey;

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



    public Post(PostCreateDTO data){
        this.post = data.post();
        this.operative = true;

    }



    public boolean post_status_check(){
        return this.operative;
    }

}
