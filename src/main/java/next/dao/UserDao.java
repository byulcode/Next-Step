package next.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import core.jdbc.JdbcTemplate;
import core.jdbc.PreparedStatementSetter;
import core.jdbc.RowMapper;
import next.model.User;

public class UserDao {
    //회원 insert
    public void insert(User user) throws SQLException {

        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";

        PreparedStatementSetter pstmtSetter = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
                pstmt.executeUpdate();
            }
        };
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.update(sql, pstmtSetter);
    }

    //회원 update
    public void update(User user) throws SQLException {

        String sql = "UPDATE USERS SET password = ?, name= ?, email = ? WHERE userId = ?";
        PreparedStatementSetter pstmtSetter = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getPassword());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getUserId());
                pstmt.executeUpdate();
            }
        };

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.update(sql, pstmtSetter);
    }

    //모든 회원 반환
    public List<User> findAll() throws SQLException {
        String sql = "SELECT * FROM USERS";
        PreparedStatementSetter pstmtSetter = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
            }
        };
        RowMapper rowMapper = rs -> {
            User user = new User
                    (rs.getString("userId"),
                            rs.getString("password"),
                            rs.getString("name"),
                            rs.getString("email"));
            return user;
        };

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        return jdbcTemplate.query(sql, pstmtSetter, rowMapper);
    }

    //userId로 회원 찾기
    public User findByUserId(String userId) throws SQLException {
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        PreparedStatementSetter pstmtSetter = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, userId);
            }
        };
        RowMapper<User> rowMapper = new RowMapper() {
            @Override
            public User mapRow(ResultSet rs) throws SQLException {
                return new User
                        (rs.getString("userId"),
                                rs.getString("password"),
                                rs.getString("name"),
                                rs.getString("email"));
            }
        };
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        return jdbcTemplate.queryForObject(sql, pstmtSetter, rowMapper);
    }
}
