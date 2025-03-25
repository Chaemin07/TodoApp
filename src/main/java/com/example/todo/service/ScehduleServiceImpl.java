package com.example.todo.service;

import com.example.todo.dto.userdto.UserRequestDto;
import com.example.todo.dto.userdto.UserResponseDto;
import com.example.todo.entity.User;
import com.example.todo.repository.ScheduleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScehduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScehduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public UserResponseDto saveUser(UserRequestDto dto) {
        User user = new User(dto.getUserName(), dto.getPassword(), dto.getUserEmail());
        return scheduleRepository.saveUser(user);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return scheduleRepository.getAllUsers();
    }
}
