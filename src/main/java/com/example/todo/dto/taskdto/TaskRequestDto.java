package com.example.todo.dto.taskdto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class TaskRequestDto {
    private Long userId;               // 작성자 ID
    private String password;            // 작성자 PW
    private String title;              // 제목
    private String contents;           // 내용
    private LocalDateTime startTime;   // 시작 시간
    private LocalDateTime endTime;     // 종료 시간



    public TaskRequestDto(Long userId, String title, String contents) {
        this.userId = userId;
        this.title = title;
        this.contents = contents;
    }
    public TaskRequestDto(String password, String title, String contents) {
        this.password = password;
        this.title = title;
        this.contents = contents;
    }

    public TaskRequestDto(Long userId, String password,
                          String title, String contents) {
        this.userId = userId;
        this.password = password;
        this.title = title;
        this.contents = contents;
    }

    public TaskRequestDto(String password, String title, String contents,
                          LocalDateTime startTime, LocalDateTime endTime) {
        this.password = password;
        this.title = title;
        this.contents = contents;
        this.startTime=startTime;
        this.endTime = endTime;
    }
}