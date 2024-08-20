package com.MapView.BackEnd.dtos.Post;

import jakarta.validation.constraints.NotBlank;

public record PostUpdateDTO (
        @NotBlank
        String post) {

}
