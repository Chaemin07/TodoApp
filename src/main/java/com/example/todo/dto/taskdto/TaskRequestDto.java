package com.example.todo.dto.taskdto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class TaskRequestDto {
    @Setter
    private Long userId;               // 작성자 ID
    private String title;              // 제목
    private String contents;           // 내용
    private LocalDateTime startTime;   // 시작 시간
    private LocalDateTime endTime;     // 종료 시간


    public TaskRequestDto(Long userId, String title, String contents) {
        this.userId = userId;
        this.title = title;
        this.contents = contents;
    }

}