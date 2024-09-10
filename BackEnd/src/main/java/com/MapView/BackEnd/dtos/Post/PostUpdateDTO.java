package com.MapView.BackEnd.dtos.Post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostUpdateDTO (
        @NotBlank(message = "Post cannot be blank.")
        @NotNull
        String post) {

}
