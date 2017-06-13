package com.workfront.internship.workflow.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Created by nane on 6/6/17
 */
public abstract class AbstractDao {
    protected DataSource dataSource;

    public static void closeResources(Connection conn, Statement stmt, ResultSet rs) {

        closeResources(conn, stmt);

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeResources(Connection conn, Statement stmt) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }
}
