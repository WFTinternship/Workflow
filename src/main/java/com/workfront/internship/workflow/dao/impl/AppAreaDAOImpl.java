package com.workfront.internship.workflow.dao.impl;

import com.workfront.internship.workflow.dao.AppAreaDAO;
import com.workfront.internship.workflow.dataModel.AppArea;
import com.workfront.internship.workflow.dataModel.User;
import com.workfront.internship.workflow.exceptions.dao.NotExistingAppAreaException;
import com.workfront.internship.workflow.util.DBHelper;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AppAreaDAOImpl implements AppAreaDAO {

    private static final Logger LOG = Logger.getLogger(UserDAOImpl.class);

    public static final String id = "id";
    public static final String name = "name";
    public static final String description = "description";
    public static final String teamName = "team_name";

    /**
     * @see AppAreaDAO#add(AppArea)
     * @param appArea
     * @return
     */
    @Override
    public long add(AppArea appArea) {
        final String sql = "INSERT INTO apparea (id, name, description, team_name) " +
                "VALUES (?, ?, ?, ?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, appArea.getId());
            stmt.setString(2, appArea.getName());
            stmt.setString(3, appArea.getDescription());
            stmt.setString(4, appArea.getTeamName());

            stmt.executeUpdate();

        } catch (SQLException e) {
            LOG.error("SQL exception occurred");
            throw new RuntimeException(e);
        }
        return appArea.getId();

    }

    /**
     * @see AppAreaDAO#deleteById(long)
     * @param id
     */
    @Override
    public void deleteById(long id) {
        final String sql = "DELETE FROM apparea " +
                "WHERE id = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOG.error("SQL exception occurred");
            throw new RuntimeException(e);
        }
    }

    /**
     * @see AppAreaDAO#getUsersById(long)
     * @param appAreaId
     * @return
     */
    @Override
    public List<User> getUsersById(long appAreaId) {
        List<User> userList = new ArrayList<>();
        final String sql = "SELECT * FROM user " +
                "WHERE id IN (SELECT user_id FROM user_apparea WHERE apparea_id = ?) ";
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
            LOG.error("SQL exception occurred");
            throw new RuntimeException(e);
        }
        return userList;
    }

    /**
     * @see AppAreaDAO#getById(long)
     * @param id
     * @return
     */
    @Override
    public AppArea getById(long id) {
        AppArea appArea = AppArea.getById(id);
        Map<String, Object> actualAppArea = getAppAreaFieldsById(id);
        if(actualAppArea.isEmpty()){
            return null;
        }
        if(!isTheActualAppArea(appArea, actualAppArea)){
            LOG.error("AppArea does not exist");
            throw new NotExistingAppAreaException();
        }
        return appArea;
    }


    /**
     * Returns Map of appArea fields from database
     */
    private Map<String, Object> getAppAreaFieldsById(long id){
        Map<String, Object> fieldsMap = new HashMap<>();
        final String sql = "SELECT * FROM apparea " +
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
            LOG.error("SQL exception occurred");
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
