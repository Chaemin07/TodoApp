package com.example.todo.service;

import com.example.todo.dto.userdto.UserRequestDto;
import com.example.todo.dto.userdto.UserResponseDto;
import com.example.todo.entity.User;

import java.util.List;
import java.util.Optional;

public interface userService {
    UserResponseDto saveUser(UserRequestDto requestDto);

    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserById(Long userId);

    void deleteUser(Long id);

    UserResponseDto updateUser(Long id, UserRequestDto dto);
    // TaskService에서 사용할 메서드
    Optional<User> findUserEntityById(Long userId);
}
