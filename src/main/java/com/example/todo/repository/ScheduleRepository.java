package com.example.todo.repository;

import com.example.todo.dto.userdto.UserResponseDto;
import com.example.todo.entity.User;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    UserResponseDto saveUser(User user);


    List<UserResponseDto> getAllUsers();


    Optional<User> getUserById(Long userId);

    int deleteUser(Long id);
}
