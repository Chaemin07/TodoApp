package com.example.todo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    private Long taskId;            // 일정 ID
    private Long userId;            // 작성자 ID
    private String title;           // 제목
    private String contents;        // 내용
    private LocalDateTime startTime;    // 시작 시간
    private LocalDateTime endTime;      // 종료 시간

    private LocalDateTime createdAt;    // 작성일
    private LocalDateTime updatedAt;    // 수정일

    public Task(Long userId, String title, String contents,LocalDateTime startTime,LocalDateTime endTime) {
        this.userId = userId;
        this.title = title;
        this.contents = contents;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
