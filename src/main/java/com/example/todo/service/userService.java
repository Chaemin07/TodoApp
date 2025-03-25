package com.example.todo.service;

import com.example.todo.dto.userdto.UserRequestDto;
import com.example.todo.dto.userdto.UserResponseDto;

import java.util.List;

public interface userService {
    UserResponseDto saveUser(UserRequestDto requestDto);

    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserById(Long userId);

    void deleteUser(Long id);

    UserResponseDto updateUser(Long id, UserRequestDto dto);
}
