package com.example.todo.service;

import com.example.todo.dto.userdto.UserRequestDto;
import com.example.todo.dto.userdto.UserResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ScheduleService {
    UserResponseDto saveUser(UserRequestDto requestDto);

    List<UserResponseDto> getAllUsers();

}
