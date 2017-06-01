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


public class AppAreaDAOImpl implements AppAreaDAO {

    @Override
    public long add(AppArea appArea) {
        final String sql = "INSERT INTO work_flow.apparea (id, name, description, team_name) " +
                "VALUES (?, ?, ?, ?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, appArea.getId());
            stmt.setString(2, appArea.getName());
            stmt.setString(3, appArea.getDescription());
            stmt.setString(4, appArea.getTeamName());

            stmt.executeUpdate();

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
