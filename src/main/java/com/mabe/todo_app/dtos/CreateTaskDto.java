package com.mabe.todo_app.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTaskDto(

        @NotBlank(message = "Title is required") 
        @Size(max = 120, message = "Title must be less than 120 characters") 
        String title,

        @Size(max = 500, message = "Description must be less than 500 characters") 
        String description

) {

}
