package com.mabe.todo_app.services;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mabe.todo_app.common.ApiResponse;
import com.mabe.todo_app.dtos.CreateTaskDto;
import com.mabe.todo_app.dtos.PageResponse;
import com.mabe.todo_app.dtos.TaskResponse;
import com.mabe.todo_app.dtos.TaskStatus;
import com.mabe.todo_app.dtos.UpdateTaskDto;
import com.mabe.todo_app.entities.Task;
import com.mabe.todo_app.repository.TaskRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;

@Service
public class TaskService {

    TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public PageResponse<TaskResponse> getAllTasks(Pageable pageable, TaskStatus status) {
        Page<Task> taskPage = status == null
                ? taskRepository.findAll(pageable)
                : taskRepository.findByStatus(status, pageable);

        Page<TaskResponse> page = taskPage.map(this::toTaskResponse);

        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast());
    }

    public ApiResponse<TaskResponse> createTask(CreateTaskDto taskDto) {
        Task task = new Task();
        task.setTitle(taskDto.title());
        task.setDescription(taskDto.description());
        task.setStatus(TaskStatus.PENDING);
        Task savedTask = taskRepository.save(task);
        return toResponse(savedTask);
    }

    public ApiResponse<TaskResponse> getTaskById(String taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        return toResponse(task);
    }

    public ApiResponse<TaskResponse> updateTask(String taskId, UpdateTaskDto request) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));

        if (request.title() != null) {
            task.setTitle(request.title());
        }

        if (request.description() != null) {
            task.setDescription(request.description());
        }
        if (request.status() != null) {
            task.setStatus(request.status());
        }

        task.setUpdatedAt(Instant.now());

        Task updatedTask = taskRepository.save(task);

        return toResponse(updatedTask);
    }

    public void delete(String taskId) {
        Task task = getTaskEntityById(taskId);
        taskRepository.delete(task);
    }

    public Task getTaskEntityById(String taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found: " + taskId));
    }

    public ApiResponse<TaskResponse> toResponse(Task task) {
        TaskResponse result = new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt().toString(),
                task.getUpdatedAt().toString());
        return new ApiResponse<>(
                null,
                1,
                result,
                0,
                "",
                null);

    }

    public ApiResponse<TaskResponse> toResponse(List<TaskResponse> tasks, int count, TaskResponse result, int page,
            String message, String error) {
        return new ApiResponse<>(
                tasks,
                count,
                result,
                page,
                message,
                error);
    }

    private TaskResponse toTaskResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt().toString(),
                task.getUpdatedAt().toString());
    }

}
