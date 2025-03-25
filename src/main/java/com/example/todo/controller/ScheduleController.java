package com.example.todo.controller;

import com.example.todo.dto.userdto.UserRequestDto;
import com.example.todo.dto.userdto.UserResponseDto;
import com.example.todo.service.ScheduleService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/user")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto requestDto) {
        return new ResponseEntity<>(scheduleService.saveUser(requestDto), HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public List<UserResponseDto> findAllUsers() {
        return scheduleService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable Long id) {
        return new ResponseEntity<>(scheduleService.getUserById(id), HttpStatus.OK);
    }

    // 사용자 정보 수정(부분 수정)
//    @PatchMapping("/user/{id}")
//    public ResponseEntity<UserResponseDto> updateTask(
//            @PathVariable Long id,
//            @RequestBody UserRequestDto dto
//    ) {
////        return new ResponseEntity<>(scheduleService.updateTask(id, dto), HttpStatus.OK);
//    }

    // 사용자 정보 수정(일괄 수정)
    @PutMapping("/user/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDto dto
    ) {
        return new ResponseEntity<>(scheduleService.updateUser(id,dto),HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        scheduleService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


//    @PostMapping()
//    public ResponseEntity<ScheduleResponseDto> createSchedule
}
