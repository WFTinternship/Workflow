package com.workfront.internship.dao.impl;

import com.workfront.internship.dao.AppAreaDAO;
import com.workfront.internship.dataModel.AppArea;
import com.workfront.internship.dataModel.User;
import com.workfront.internship.exceptions.NotExistingAppAreaException;
import com.workfront.internship.util.DBHelper;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AppAreaDAOImpl implements AppAreaDAO {

    public static final String id = "id";
    public static final String name = "name";
    public static final String description = "description";
    public static final String teamName = "team_name";

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

    @Override
    public AppArea getById(long id) {
        AppArea appArea = AppArea.getById(id);
        Map<String, Object> actualAppArea = getAppAreaFieldsById(id);
        if(actualAppArea.isEmpty()){
            return null;
        }
        if(!isTheActualAppArea(appArea, actualAppArea)){
            throw new NotExistingAppAreaException();
        }
        return appArea;
    }


    /**
     * Returns Map of appArea fields from database
     */
    private Map<String, Object> getAppAreaFieldsById(long id){
        Map<String, Object> fieldsMap = new HashMap<>();
        final String sql = "SELECT * FROM work_flow.apparea " +
                "WHERE id = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                fieldsMap.put("Name",rs.getString(AppAreaDAOImpl.name));
                fieldsMap.put("Description",rs.getString(AppAreaDAOImpl.description));
                fieldsMap.put("TeamName",rs.getString(AppAreaDAOImpl.teamName));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return fieldsMap;
    }

    /**
     * Checks if the appArea got from the database is the same as the one in AppArea enum
     *
     * @param appArea
     * @param actualAppArea
     * @return
     */

    private static boolean isTheActualAppArea(AppArea appArea, Map<String, Object> actualAppArea){
        return appArea.getName().equals(actualAppArea.get("Name")) &&
                appArea.getDescription().equals(actualAppArea.get("Description")) &&
                appArea.getTeamName().equals(actualAppArea.get("TeamName"));
    }


}
