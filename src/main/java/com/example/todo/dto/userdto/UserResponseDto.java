package com.example.todo.dto.userdto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private Long userId;        // 사용자 식별자
    private String userName;    // 사용자 이름
    private String userEmail;   // 사용자 이메일
}
