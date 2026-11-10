package com.mabe.todo_app.dtos;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateTaskDto(

        @Pattern(regexp = ".*\\S.*", message = "Title cannot be blank") 
        @Size(max = 120, message = "Title must be less than 120 characters") 
        String title,

        @Size(max = 500, message = "Description must be less than 500 characters") String description,

        TaskStatus status) {

}
