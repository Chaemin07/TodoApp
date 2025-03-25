package com.example.todo.repository;

import com.example.todo.dto.userdto.UserResponseDto;
import com.example.todo.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        return jdbcTemplate.query("select * from User", userRowMapper1());
    }



    @Override
    public Optional<User> getUserById(Long userId) {
        List<User> result = jdbcTemplate.query("select *from User where userId = ?", userRowMapper2(), userId);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        return result.stream().findAny();
    }

    @Override
    public int deleteUser(Long id) {
        // 삭제된 행의 수 리턴
        return jdbcTemplate.update("delete from User where userId =?",id);
    }

    @Override
    public int updateUser(User updatedUser) {
        return jdbcTemplate.update("update User set name =?, password =?, email=? where userId=?",
                updatedUser.getUserName(),
                updatedUser.getPassword(),
                updatedUser.getUserEmail(),
                updatedUser.getUserId());
    }

    @Override
    public User findUserByIdOrElseThrow(Long id) {
        List<User> result = jdbcTemplate.query("select *from User where user.userId=?", userRowMapper2(), id);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    private RowMapper<UserResponseDto> userRowMapper1() {
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

    private RowMapper<User> userRowMapper2() {
        return (rs, rowNum) -> new User(
                rs.getLong("userId"),
                rs.getString("name"),
                rs.getString("password"),
                rs.getString("email")
        );
    }

}
