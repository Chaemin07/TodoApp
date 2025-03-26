package com.example.todo.repository;

import com.example.todo.dto.taskdto.TaskResponseDto;
import com.example.todo.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    TaskResponseDto saveTask(Task task);

    List<TaskResponseDto> findAllTasksByUserId(Long userId);

    Optional<Task> findTaskByUserIdAndTaskId(Long userId, Long taskId);

    int deleteTaskByUserId(Long userId);

    int deleteTask(Long userId, Long taskId);
}
