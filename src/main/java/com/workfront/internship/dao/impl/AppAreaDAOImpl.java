package com.workfront.internship.dao.impl;

import com.workfront.internship.dao.AppAreaDAO;
import com.workfront.internship.dataModel.AppArea;
import com.workfront.internship.dataModel.User;
import com.workfront.internship.util.DBHelper;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Karen on 5/31/2017.
 */
public class AppAreaDAOImpl implements AppAreaDAO {

    @Override
    public long add(AppArea appArea) {
        long id = 0;
        final String sql = "INSERT INTO work_flow.apparea (name, description, team_name) " +
                "VALUES (?, ?, ?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, appArea.getName());
            stmt.setString(2, appArea.getDescription());
            stmt.setString(3, appArea.getTeamName());

            try {
                Field idField = appArea.getClass().getDeclaredField("id");
                idField.setAccessible(true);
                idField.setLong(appArea, id);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            stmt.executeUpdate();
            ResultSet resultSet = stmt.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return appArea.getId();

    }

    @Override
    public void deleteById(long id) {
        final String sql = "DELETE FROM work_flow.apparea " +
                "WHERE id = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO check the right place of the method
    @Override
    public List<User> getUsersById(long appAreaId) {
        List<User> userList = new ArrayList<>();
        final String sql = "SELECT * FROM work_flow.user " +
                "WHERE id IN (SELECT user_id FROM work_flow.user_apparea WHERE apparea_id = ?) ";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, appAreaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user = UserDAOImpl.fromResultSet(user, rs);
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

}
