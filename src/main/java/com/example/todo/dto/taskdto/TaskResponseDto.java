package com.example.todo.dto.taskdto;


import com.example.todo.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TaskResponseDto {

    private Long taskId;              // 일정 ID
    private Long userId;              // 작성자 ID
    private String title;             // 제목
    private String contents;          // 내용
    private LocalDateTime startTime;  // 시작 시간
    private LocalDateTime endTime;    // 종료 시간
    private LocalDateTime createdAt;  // 작성일
    private LocalDateTime updatedAt;  // 수정일

    public TaskResponseDto(Task task) {
        this.taskId = task.getTaskId();
        this.userId = task.getUserId();
        this.title = task.getTitle();
        this.contents = task.getContents();
        this.startTime = task.getStartTime();
        this.endTime = task.getEndTime();
        this.createdAt = task.getCreatedAt();
        this.updatedAt = task.getUpdatedAt();
    }



}