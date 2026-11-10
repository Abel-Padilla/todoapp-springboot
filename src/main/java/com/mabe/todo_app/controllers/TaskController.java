package com.mabe.todo_app.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mabe.todo_app.common.ApiResponse;
import com.mabe.todo_app.dtos.CreateTaskDto;
import com.mabe.todo_app.dtos.PageResponse;
import com.mabe.todo_app.dtos.TaskResponse;
import com.mabe.todo_app.dtos.TaskStatus;
import com.mabe.todo_app.dtos.UpdateTaskDto;
import com.mabe.todo_app.services.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<PageResponse<TaskResponse>> getAllTasks(@RequestParam(required = false) TaskStatus status,
            @PageableDefault(size = 100, page = 0) Pageable pageable) {
        return ResponseEntity.ok(taskService.getAllTasks(pageable, status));
    }

    @PostMapping(path = "")
    public ResponseEntity<ApiResponse<TaskResponse>> createTask(@Valid @RequestBody CreateTaskDto taskDto) {
        return ResponseEntity.ok().body(taskService.createTask(taskDto));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> getTaskById(@Valid @PathVariable String id) {
        return ResponseEntity.ok().body(taskService.getTaskById(id));
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> updateTask(
            @PathVariable String id,
            @Valid @RequestBody UpdateTaskDto updateTaskDto) {
        return ResponseEntity.ok().body(taskService.updateTask(id, updateTaskDto));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteTask(@PathVariable String id) {
        taskService.delete(id);
        return ResponseEntity.ok().body(true);
    }

}
