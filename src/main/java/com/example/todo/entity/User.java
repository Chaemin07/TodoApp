package com.example.todo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {

    private Long userId;      // 사용자 식별자
    private String password;    // 사용자 비밀번호
    private String userName;    // 사용자 이름
    private String userEmail;       // 사용자 이메일

    public User(String name, String password, String email) {
        this.userName = name;
        this.password = password;
        this.userEmail = email;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateEmail(String email) {
        this.userEmail = email;
    }
}
