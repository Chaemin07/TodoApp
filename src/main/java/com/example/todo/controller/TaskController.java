package com.example.todo.controller;

import com.example.todo.dto.taskdto.TaskRequestDto;
import com.example.todo.dto.taskdto.TaskResponseDto;
import com.example.todo.entity.Task;
import com.example.todo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@PathVariable Long userId, @RequestBody TaskRequestDto dto) {
        dto.setUserId(userId);
        return new ResponseEntity<>(taskService.saveTask(dto), HttpStatus.OK);
    }

    @GetMapping
    public List<TaskResponseDto> findAllTasksByUserId(@PathVariable Long userId) {
        return taskService.findAllTasksByUserId(userId);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> findTaskById(@PathVariable Long userId, @PathVariable Long taskId) {
        TaskResponseDto task = taskService.findTaskByUserIdAndTaskId(userId, taskId);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllTasksByUserId(@PathVariable Long userId) {
        taskService.deleteTaskByUserId(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteByUserIdAndTaskId(@PathVariable Long userId, @PathVariable Long taskId) {
        taskService.deleteTask(userId,taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
