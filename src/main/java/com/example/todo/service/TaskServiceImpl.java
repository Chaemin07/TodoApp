package com.example.todo.service;

import com.example.todo.dto.taskdto.TaskRequestDto;
import com.example.todo.dto.taskdto.TaskResponseDto;
import com.example.todo.dto.userdto.UserRequestDto;
import com.example.todo.dto.userdto.UserResponseDto;
import com.example.todo.entity.Task;
import com.example.todo.entity.User;
import com.example.todo.repository.TaskRepository;
import com.example.todo.repository.userRepositoryImpl;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final DataSource dataSource;

    public TaskServiceImpl(TaskRepository taskRepository, DataSource dataSource) {
        this.taskRepository = taskRepository;
        this.dataSource = dataSource;
    }

    @Override
    public TaskResponseDto saveTask(TaskRequestDto dto) {

        // 디폴트 시작, 종료 시간 지정
        LocalDate day = LocalDate.now();
        // 시작시간 정보 없다면 초기화
        LocalDateTime startTime = dto.getStartTime() != null ?
                dto.getStartTime() : day.atStartOfDay();
        LocalDateTime endTime = dto.getEndTime() != null ?
                dto.getEndTime() : day.atTime(23, 59, 59);
        // 종료시간보다 시작시간이 이전일때 오류
        if (endTime.isBefore(startTime)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "입력이 올바르지 않습니다.");
        }
        Task task = new Task(dto.getUserId(), dto.getTitle(), dto.getContents(),
                startTime, endTime);

        return taskRepository.saveTask(task);
    }

    @Override
    public List<TaskResponseDto> findAllTasksByUserId(Long userId) {
        return taskRepository.findAllTasksByUserId(userId);
    }

    @Override
    public TaskResponseDto findTaskByUserIdAndTaskId(Long userId, Long taskId) {
        Optional<Task> task = taskRepository.findTaskByUserIdAndTaskId(userId, taskId);
        if (task.isPresent()) {
            return new TaskResponseDto(task.orElse(new Task()));
        }else {
            throw new RuntimeException("해당 id의 일정을 찾을 수 없습니다!");
        }
    }
    // 사용자 id를 입력으로 해당 사용자의 모든 일정 삭제
    @Override
    public void deleteTaskByUserId(Long userId) {
        int deletedRow = taskRepository.deleteTaskByUserId(userId);
        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }
    // 사용자 id를 바탕으로 해당 사용자의 일정id값에 해당하는 일정 삭제
    @Override
    public void deleteTask(Long userId,Long taskId) {
        int deletedRow = taskRepository.deleteTask(userId, taskId);
        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public TaskResponseDto updateTask(Long userId, Long taskId,TaskRequestDto dto) {

        userService userService = new userServiceImpl(new userRepositoryImpl(dataSource));
        User findUserEntity = userService.findUserEntityById(userId).orElse(new User());
        // 패스워드 검증
        isValidPassword(findUserEntity, dto);

        Task updatedTask = taskRepository.findTaskByUserIdAndTaskId(userId, taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        updatedTask.setTitle(dto.getTitle());
        updatedTask.setContents(dto.getContents());

        // 디폴트 시작, 종료 시간 지정
        LocalDate day = LocalDate.now();
        // 시작시간 정보 없다면 초기화
        LocalDateTime startTime = dto.getStartTime() != null ?
                dto.getStartTime() : day.atStartOfDay();
        LocalDateTime endTime = dto.getEndTime() != null ?
                dto.getEndTime() : day.atTime(23, 59, 59);
        // 종료시간보다 시작시간이 이전일때 오류
        if (endTime.isBefore(startTime)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "입력이 올바르지 않습니다.");
        }

        updatedTask.setStartTime(startTime);
        updatedTask.setEndTime(endTime);
        updatedTask.setCreatedAt(updatedTask.getCreatedAt());
        updatedTask.setUpdatedAt(LocalDateTime.now());

        // 수정된 데이터 db에 반영
        int updatedRow = taskRepository.updateTask(updatedTask);
        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data has been modified.");
        }
        // 수정 반영된 객체 찾기
        Task result = taskRepository.findTaskByUserIdAndTaskId(userId, taskId).orElse(new Task());
        return new TaskResponseDto(taskId, userId, result.getTitle(), result.getContents(),
                result.getStartTime(), result.getEndTime(), result.getCreatedAt(), result.getUpdatedAt());
    }

    private boolean isValidPassword(User user, TaskRequestDto dto) {
        if (!(user.getPassword().equals(dto.getPassword()))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, " 비밀번호가 일치하지 않습니다.");
        }
        return true;
    }
}
