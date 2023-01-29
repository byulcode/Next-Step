package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import core.jdbc.JdbcTemplate;
import core.jdbc.SelectJdbcTemplate;
import next.model.User;

public class UserDao {
    //회원 insert
    public void insert(User user) throws SQLException {

        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {
            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
                pstmt.executeUpdate();
            }
        };
        jdbcTemplate.update(sql);
    }

    //회원 update
    public void update(User user) throws SQLException {

        String sql = "UPDATE USERS SET password = ?, name= ?, email = ? WHERE userId = ?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {
            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getPassword());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getUserId());
                pstmt.executeUpdate();
            }
        };
        jdbcTemplate.update(sql);
    }

    //모든 회원 반환
    public List<User> findAll() throws SQLException {
        String sql = "SELECT * FROM USERS";
        SelectJdbcTemplate selectJdbcTemplate = new SelectJdbcTemplate() {
            @Override
            public void setValues(PreparedStatement pstmt) {

            }

            @Override
            public Object mapRow(ResultSet rs) throws SQLException {
                User user = new User
                        (rs.getString("userId"),
                                rs.getString("password"),
                                rs.getString("name"),
                                rs.getString("email"));
                return user;
            }
        };
        return selectJdbcTemplate.query(sql);
    }

    //userId로 회원 찾기
    public User findByUserId(String userId) throws SQLException {
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        SelectJdbcTemplate selectJdbcTemplate = new SelectJdbcTemplate() {
            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, userId);
            }

            @Override
            public Object mapRow(ResultSet rs) throws SQLException {
                User user = new User
                        (rs.getString("userId"),
                                rs.getString("password"),
                                rs.getString("name"),
                                rs.getString("email"));
                return user;
            }
        };
        return (User) selectJdbcTemplate.queryForObject(sql);
    }
}
