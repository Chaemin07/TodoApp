package com.example.todo.repository;

import com.example.todo.dto.userdto.UserResponseDto;
import com.example.todo.entity.User;

import java.util.List;

public interface ScheduleRepository {
    UserResponseDto saveUser(User user);


    List<UserResponseDto> getAllUsers();


}
