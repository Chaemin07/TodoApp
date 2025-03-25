package com.example.todo.controller;

import com.example.todo.dto.userdto.UserRequestDto;
import com.example.todo.dto.userdto.UserResponseDto;
import com.example.todo.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping()
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto requestDto) {
        return new ResponseEntity<>(scheduleService.saveUser(requestDto), HttpStatus.CREATED);
    }

    @GetMapping()
    public List<UserResponseDto> findAllUsers() {
        return scheduleService.getAllUsers();
    }

//    @PostMapping()
//    public ResponseEntity<ScheduleResponseDto> createSchedule
}
