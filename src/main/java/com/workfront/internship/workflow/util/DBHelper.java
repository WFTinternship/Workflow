package com.workfront.internship.workflow.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by nane on 5/27/17. 
 */
public class DBHelper {

    public static Properties loadDbCfgProperties(){
        Properties properties = new Properties();
        InputStream inputStream =  DBHelper.class.getClassLoader().getResourceAsStream("dbConfig.properties");
        try (InputStream is = inputStream) {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        Properties properties = loadDbCfgProperties();

        return DriverManager.getConnection(properties.getProperty("DB_TEST_URL"),
                properties.getProperty("USERNAME"),
                properties.getProperty("PASSWORD"));
    }

    public static Connection getPooledConnection() throws SQLException {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        Properties properties = loadDbCfgProperties();
        try {
            cpds.setDriverClass("com.mysql.jdbc.Driver");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        cpds.setJdbcUrl(properties.getProperty("URL"));
        cpds.setUser(properties.getProperty("USERNAME"));
        cpds.setPassword(properties.getProperty("PASSWORD"));

        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(2);
        cpds.setMaxPoolSize(20);
        return cpds.getConnection();
    }

    public static Connection getConnection(String connectionType) throws SQLException {
        if("Pooled connection".equals(connectionType)){
            return getPooledConnection();
        }else if("Single connection".equals(connectionType)){
            return getConnection();
        }
        return null;
    }
}
