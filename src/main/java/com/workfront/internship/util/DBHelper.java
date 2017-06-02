package com.workfront.internship.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
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

    public static Connection getPooledConnection() throws SQLException {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        try {
            cpds.setDriverClass("com.mysql.jdbc.Driver");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        cpds.setJdbcUrl(CONN_STRING);
        cpds.setUser(USERNAME);
        cpds.setPassword(PASSWORD);

        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(2);
        cpds.setMaxPoolSize(20);
        return cpds.getConnection();
    }
}
