package com.example.todo.service;

import com.example.todo.dto.taskdto.TaskRequestDto;
import com.example.todo.dto.taskdto.TaskResponseDto;

import java.util.List;

public interface TaskService {
    TaskResponseDto saveTask(TaskRequestDto dto);

    List<TaskResponseDto> findAllTasksByUserId(Long userId);

    TaskResponseDto findTaskByUserIdAndTaskId(Long userId, Long taskId);

    void deleteTaskByUserId(Long userId);

    void deleteTask(Long userId,Long taskId);
}
