package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {

    public void update(String sql, PreparedStatementSetter pstmtSetter) throws SQLException {

        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmtSetter.setValues(pstmt);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public List query(String sql, PreparedStatementSetter pstmtSetter, RowMapper rowMapper) throws SQLException {
        ResultSet rs = null;
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmtSetter.setValues(pstmt);
            rs = pstmt.executeQuery();

            List users = new ArrayList();
            if (rs.next()) {
                users.add(rowMapper.mapRow(rs));
            }
            return users;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    //단건을 조회하기 위한 메소드
    public Object queryForObject(String sql, PreparedStatementSetter pstmtSetter, RowMapper rowMapper) throws SQLException {
        List result = new ArrayList();
        result = query(sql, pstmtSetter, rowMapper);
        return result.get(0);
    }
}
