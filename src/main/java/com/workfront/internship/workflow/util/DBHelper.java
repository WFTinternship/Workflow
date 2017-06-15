package com.workfront.internship.workflow.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by nane on 5/27/17
 */
public class DBHelper {
    static ComboPooledDataSource cpds = new ComboPooledDataSource();

    public static Properties loadDbCfgProperties(){
        Properties properties = new Properties();
        InputStream inputStream =  DBHelper.class
                .getClassLoader()
                .getResourceAsStream("dbConfig.properties");
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

    public static DataSource getPooledConnection(){
        Properties properties = loadDbCfgProperties();

        try{
            cpds.setDriverClass("com.mysql.jdbc.Driver");
        }catch(PropertyVetoException e){
            e.printStackTrace();
        }
        cpds.setJdbcUrl(properties.getProperty("DB_TEST_URL"));
        cpds.setUser(properties.getProperty("USERNAME"));
        cpds.setPassword(properties.getProperty("PASSWORD"));

        cpds.setInitialPoolSize(5);
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(20);

        return cpds;
    }

    public static Connection getConnection(ConnectionType connectionType) throws SQLException {
        if(connectionType.equals(ConnectionType.POOL)) {
            return getPooledConnection().getConnection();
        } else if(connectionType.equals(ConnectionType.BASIC)){
            return getConnection();
        } else {
            throw new RuntimeException(String.format(
                    "Unknown connection type: %s", connectionType
            ));
        }
    }
}
