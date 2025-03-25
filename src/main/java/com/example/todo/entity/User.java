package com.example.todo.entity;

import com.example.todo.dto.userdto.UserRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long userId;      // 사용자 식별자
    private String userName;    // 사용자 이름
    private String password;    // 사용자 비밀번호
    private String userEmail;       // 사용자 이메일

    public User(String name, String password, String email) {
        this.userName = name;
        this.password = password;
        this.userEmail = email;
    }

    public User(Long userId, String name, String email) {
        this.userId = userId;
        this.userName = name;
        this.userEmail = email;
    }
    // DTO 기반 생성자
    public User(Long userId, UserRequestDto dto) {
        this.userId = userId;
        this.userName = dto.getUserName();
        this.password = dto.getPassword();
        this.userEmail = dto.getUserEmail();
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateEmail(String email) {
        this.userEmail = email;
    }
}
