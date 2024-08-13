package com.MapView.BackEnd.entities;

import jakarta.persistence.*;
import lombok.*;

import java.nio.file.LinkOption;

@Table(name = "usuario")
@Entity(name = "usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")


public class user {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private
}
