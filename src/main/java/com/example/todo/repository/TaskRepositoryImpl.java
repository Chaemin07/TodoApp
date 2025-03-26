package com.example.todo.repository;

import com.example.todo.dto.taskdto.TaskResponseDto;
import com.example.todo.entity.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TaskRepositoryImpl implements TaskRepository {
    private final JdbcTemplate jdbcTemplate;

    public TaskRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TaskResponseDto saveTask(Task task) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("Task").usingGeneratedKeyColumns("taskId");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userId", task.getUserId());
        parameters.put("title", task.getTitle());
        parameters.put("contents", task.getContents());
        parameters.put("startTime", task.getStartTime());
        parameters.put("endTime", task.getEndTime());
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        parameters.put("createdAt", task.getCreatedAt());
        parameters.put("updatedAt", task.getUpdatedAt());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        // TODO createdAt, updatedAt 조회 안됨, db에서 조회한 데이터 가져와서 리턴해줘야함
        return new TaskResponseDto(
                key.longValue(),
                task.getUserId(),
                task.getTitle(),
                task.getContents(),
                task.getStartTime(),
                task.getEndTime(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }

    @Override
    public List<TaskResponseDto> findAllTasksByUserId(Long userId) {
        return jdbcTemplate.query("select * from Task where userId =?", taskRowMapper(), userId);
    }

    @Override
    public Optional<Task> findTaskByUserIdAndTaskId(Long userId, Long taskId) {
        List<Task> result = jdbcTemplate.query("select *from Task where userId =? and taskId=?", taskRowMapper2(), userId, taskId);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        return result.stream().findAny();
    }

    @Override
    public int deleteTaskByUserId(Long userId) {
        // 삭제된 행의 수 리턴
        return jdbcTemplate.update("delete from Task where userId =?", userId);
    }

    @Override
    public int deleteTask(Long userId, Long taskId) {
        return jdbcTemplate.update("delete from Task where userId= ? and taskId=?", userId, taskId);
    }

    private RowMapper<Task> taskRowMapper2() {
        return (rs, rowNum) -> new Task(
                rs.getLong("taskId"),
                rs.getLong("userId"),
                rs.getString("title"),
                rs.getString("contents"),
                toLocalDateTime(rs.getTimestamp("startTime")),
                toLocalDateTime(rs.getTimestamp("endTime")),
                toLocalDateTime(rs.getTimestamp("createdAt")),
                toLocalDateTime(rs.getTimestamp("updatedAt"))
        );
    }

    private RowMapper<TaskResponseDto> taskRowMapper() {
        return new RowMapper<TaskResponseDto>() {
            @Override
            public TaskResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new TaskResponseDto(
                        rs.getLong("taskId"),
                        rs.getLong("userId"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        toLocalDateTime(rs.getTimestamp("startTime")),
                        toLocalDateTime(rs.getTimestamp("endTime")),
                        toLocalDateTime(rs.getTimestamp("createdAt")),
                        toLocalDateTime(rs.getTimestamp("updatedAt"))
                );
            }
        };
    }

    private LocalDateTime toLocalDateTime(Timestamp ts) {
        return ts != null ? ts.toLocalDateTime() : null;
    }

}
