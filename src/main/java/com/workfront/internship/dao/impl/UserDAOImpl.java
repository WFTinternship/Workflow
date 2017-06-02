package com.workfront.internship.dao.impl;

import com.workfront.internship.dao.UserDAO;
import com.workfront.internship.dataModel.AppArea;
import com.workfront.internship.dataModel.User;
import com.workfront.internship.dbConstants.DataBaseConstants;
import com.workfront.internship.exceptions.NoSuchUserException;
import com.workfront.internship.util.DBHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by Vahag on 5/27/2017.
 */
public class UserDAOImpl implements UserDAO {

    @Override
    public long add(User user) {
        long id = 0;
        final String addSql = "INSERT INTO work_flow.user (first_name, last_name, email, passcode, rating) " +
                "VALUES (?, ?, ?, ?, ?)";

        final String subscribeSql = "INSERT INTO work_flow.user_apparea (user_id, apparea_id) " +
                "VALUES (?, ?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement addStmt = conn.prepareStatement(addSql, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement subscribeStmt = conn.prepareStatement(subscribeSql)) {
            conn.setAutoCommit(false);

            addStmt.setString(1, user.getFirstName());
            addStmt.setString(2, user.getLastName());
            addStmt.setString(3, user.getEmail());
            addStmt.setString(4, user.getPassword());
            addStmt.setInt(5, user.getRating());

            addStmt.executeUpdate();
            ResultSet resultSet = addStmt.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            user.setId(id);

            subscribeStmt.setLong(1, id);
            for (AppArea appArea : AppArea.values()) {
                subscribeStmt.setLong(2, appArea.getId());
                subscribeStmt.executeUpdate();
                conn.commit();
            }


        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
        return user.getId();
    }

    @Override
    public void deleteById(long id) {
        final String sql = "DELETE FROM work_flow.user " +
                "WHERE id = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int numberOfUpdatedRows = stmt.executeUpdate();
            if (numberOfUpdatedRows == 0) {
                throw new NoSuchUserException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        final String sql = "DELETE FROM work_flow.user ";
        try (Connection conn = DBHelper.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void subscribeToArea(long userId, long appAreaId) {
        final String sql = "INSERT INTO work_flow.user_apparea (user_id, apparea_id) " +
                "VALUES (?, ?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.setLong(2, appAreaId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unsubscribeToArea(long userId, long appAreaId) {
        final String sql = "DELETE FROM work_flow.user_apparea " +
                " WHERE user_id = ? AND apparea_id = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.setLong(2, appAreaId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getByName(String name) {
        String filteredName = name.replaceAll(" ", "");
        List<User> userList = new ArrayList<>();
        final String sql = "SELECT * " +
                "FROM work_flow.user " +
                "WHERE CONCAT (first_name, last_name) LIKE ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, filteredName + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                userList.add(fromResultSet(user, rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    @Override
    public User getById(long id) {
        User user = null;
        final String sql = "SELECT * FROM work_flow.user " +
                "WHERE id = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                user = new User();
                user = fromResultSet(user, rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public List<AppArea> getAppAreasById(long userId) {
        List<AppArea> appAreaList = new ArrayList<>();
        final String sql = "SELECT * FROM work_flow.user_apparea " +
                "WHERE user_id = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                long appAreaId = rs.getLong("apparea_id");
                appAreaList.add(AppArea.getById(appAreaId));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return appAreaList;
    }

    public static User fromResultSet(User user, ResultSet rs) {
        try {

            user.setId(rs.getLong(DataBaseConstants.User.id));
            user.setFirstName(rs.getString(DataBaseConstants.User.firstName));
            user.setLastName(rs.getString(DataBaseConstants.User.lastName));
            user.setEmail(rs.getString(DataBaseConstants.User.email));
            user.setPassword(rs.getString(DataBaseConstants.User.password));
            user.setRating(rs.getInt(DataBaseConstants.User.rating));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}
