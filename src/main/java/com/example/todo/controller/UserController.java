package com.example.todo.controller;

import com.example.todo.dto.userdto.UserRequestDto;
import com.example.todo.dto.userdto.UserResponseDto;
import com.example.todo.service.userService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule/user")
public class UserController {

    private final userService userService;

    public UserController(userService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto requestDto) {
        return new ResponseEntity<>(userService.saveUser(requestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<UserResponseDto> findAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
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
    @PutMapping("{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDto dto
    ) {
        return new ResponseEntity<>(userService.updateUser(id,dto),HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


//    @PostMapping()
//    public ResponseEntity<ScheduleResponseDto> createSchedule
}
