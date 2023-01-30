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

    public <T> List<T> query(String sql, PreparedStatementSetter pstmtSetter, RowMapper<T> rowMapper) throws SQLException {
        ResultSet rs = null;
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmtSetter.setValues(pstmt);
            rs = pstmt.executeQuery();

            List<T> list = new ArrayList<>();
            if (rs.next()) {
                list.add(rowMapper.mapRow(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    //단건을 조회하기 위한 메소드
    public <T> T queryForObject(String sql, PreparedStatementSetter pstmtSetter, RowMapper<T> rowMapper) throws SQLException {
        List<T> result = query(sql, pstmtSetter, rowMapper);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }
}
