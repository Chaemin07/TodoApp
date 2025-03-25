package com.example.todo.service;

import com.example.todo.dto.userdto.UserRequestDto;
import com.example.todo.dto.userdto.UserResponseDto;
import com.example.todo.entity.User;
import com.example.todo.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.net.ssl.HttpsURLConnection;
import java.util.List;
import java.util.Optional;

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

    @Override
    public UserResponseDto getUserById(Long userId) {
        Optional<User> user = scheduleRepository.getUserById(userId);
        if (user.isPresent()) {
            User findUser = user.get();
            return new UserResponseDto(findUser.getUserId(),
                    findUser.getUserName(),
                    findUser.getUserEmail());
        } else {
            throw new RuntimeException("해당 id의 사용자를 찾을 수 없습니다!");
        }
    }

    @Override
    public void deleteUser(Long id) {
        int deletedRow = scheduleRepository.deleteUser(id);
        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto dto) {

        User findUser = scheduleRepository.getUserById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."));

        isValidPassword(findUser, dto);
        User updatedUser = new User(id, dto);
        int updatedRow = scheduleRepository.updateUser(updatedUser);
        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data has been modified.");
        }
        User result = scheduleRepository.findUserByIdOrElseThrow(id);
        return new UserResponseDto(id, result.getUserName(), result.getUserEmail());
    }

    private boolean isValidPassword(User user, UserRequestDto dto) {
        if (!(user.getPassword().equals(dto.getPassword()))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, " 비밀번호가 일치하지 않습니다.");
        }
        return true;
    }

}
