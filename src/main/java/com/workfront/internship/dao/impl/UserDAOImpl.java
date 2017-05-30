package com.workfront.internship.dao.impl;

import com.workfront.internship.dao.UserDAO;
import com.workfront.internship.dataModel.AppArea;
import com.workfront.internship.dataModel.User;
import com.workfront.internship.dbConstants.DataBaseConstants;
import com.workfront.internship.util.DBHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karen on 5/27/2017.
 */
public class UserDAOImpl implements UserDAO {
    @Override
    public long add(User user) {
        long id = 0;
        final String sql = "INSERT INTO work_flow.user (first_name, last_name, email, passcode, rating) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setInt(5, user.getRating());

            stmt.executeUpdate();
            ResultSet resultSet = stmt.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            user.setId(id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user.getId();
    }

    @Override
    public boolean delete(long id) {
        final String sql = "DELETE FROM work_flow.user " +
                "WHERE id = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteAll() {
        final String sql = "DELETE FROM work_flow.user ";
        try (Connection conn = DBHelper.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean subscribeToArea(User user, AppArea appArea) {
        final String sql = "INSERT INTO work_flow.user_apparea (user_id, apparea_id) " +
                "VALUES (?, ?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, user.getId());
            stmt.setLong(2, appArea.getId());

            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<User> getByName(String name) {
        String filteredName = name.replaceAll(" ", "");
        List<User> userList = new ArrayList<>();
        final String sql = "SELECT * " +
                "FROM work_flow.user " +
                "WHERE CONCAT (firstName, lastName) LIKE ?%";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, filteredName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                userList.add(fromResultSet(user, rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return user;
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
            e.printStackTrace();
        }
        return user;
    }
}
