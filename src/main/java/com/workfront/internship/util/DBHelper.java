package com.workfront.internship.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by nane on 5/27/17.
 */
public class DBHelper {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "barev";
    private static final String CONN_STRING =
            "jdbc:mysql://localhost:3306/work_flow";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
    }
}
