package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {

    public void update(String sql, PreparedStatementSetter pstmtSetter) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmtSetter.setValues(pstmt);
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public List query(String sql, PreparedStatementSetter pstmtSetter, RowMapper rowMapper) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmtSetter.setValues(pstmt);
            rs = pstmt.executeQuery();

            List users = new ArrayList();
            if (rs.next()) {
                users.add(rowMapper.mapRow(rs));
            }
            return users;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    //단건을 조회하기 위한 메소드
    public Object queryForObject(String sql,PreparedStatementSetter pstmtSetter, RowMapper rowMapper) throws SQLException {
        List result = new ArrayList();
        result = query(sql, pstmtSetter, rowMapper);
        return result.get(0);
    }
}
