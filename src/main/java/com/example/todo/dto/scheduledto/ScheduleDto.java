package com.example.todo.dto.scheduledto;

import java.time.LocalDateTime;

public class ScheduleDto {

    private Long postId;            // 일정 Id
    private Long userId;            // 작성자 Id
    private String title;           // 제목   할 일?
    private String contents;        // 내용

    // currentDateTime = 2025-03-25T14:45:22.369327800
    // yyyy-mm-dd, 시분초 정보
    private LocalDateTime startTime;    // 시작 시간
    private LocalDateTime endTime;      // 종료 시간
    private LocalDateTime createdAt;    // 작성일
    private LocalDateTime updatedAt;    // 수정일
}
