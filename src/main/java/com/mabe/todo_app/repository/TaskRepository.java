package com.mabe.todo_app.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.mabe.todo_app.dtos.TaskStatus;
import com.mabe.todo_app.entities.Task;

public interface TaskRepository
                extends JpaRepository<Task, String>,
                JpaSpecificationExecutor<Task> {

        Page<Task> findByStatus(TaskStatus status, Pageable pageable);
}