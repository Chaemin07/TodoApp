package com.example.todo.dto.userdto;

import lombok.Getter;

/**
 * User 요청 DTO
 */
@Getter
public class UserRequestDto {
    private String userName;    // 사용자 이름
    private String password;    // 사용자 비밀번호
    private String userEmail;       // 사용자 이메일
}
