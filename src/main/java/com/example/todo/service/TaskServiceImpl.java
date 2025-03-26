package com.example.todo.service;

import com.example.todo.dto.taskdto.TaskRequestDto;
import com.example.todo.dto.taskdto.TaskResponseDto;
import com.example.todo.entity.Task;
import com.example.todo.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
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
        if (dto.getEndTime().isBefore(dto.getStartTime())) {
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
}
