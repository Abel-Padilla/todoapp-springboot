package com.mabe.todo_app.dtos;

public record TaskResponse(

    String id,
    String title,
    String description, 
    TaskStatus status,
    String createdAt,
    String updatedAt

) {

}
