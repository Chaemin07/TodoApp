package com.example.todo.repository;

import com.example.todo.dto.userdto.UserResponseDto;
import com.example.todo.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {
    // JdbcTemplateMemoRepository
    private final JdbcTemplate jdbcTemplate;

    // 생성자 주입
    public ScheduleRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public UserResponseDto saveUser(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("User").usingGeneratedKeyColumns("userId");

        Map<String, Object> parameters = new HashMap<>();
        // DB컬럼명과 일치해야함
        parameters.put("name", user.getUserName());
        parameters.put("password", user.getPassword());
        parameters.put("email", user.getUserEmail());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new UserResponseDto(key.longValue(),user.getUserName(),user.getUserEmail());
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return jdbcTemplate.query("select * from User", userRowMapper());
    }

    private RowMapper<UserResponseDto> userRowMapper() {
        return new RowMapper<UserResponseDto>() {
            @Override
            public UserResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new UserResponseDto(
                        // columnLabel을 실제 DB의 컬럼명과 일치 시켜줘야함
                        rs.getLong("userId"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }
        };
    }

}
