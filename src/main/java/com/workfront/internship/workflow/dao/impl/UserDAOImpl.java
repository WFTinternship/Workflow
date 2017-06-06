package com.workfront.internship.workflow.dao.impl;

import com.workfront.internship.workflow.dao.AbstractDao;
import com.workfront.internship.workflow.dao.UserDAO;
import com.workfront.internship.workflow.dataModel.AppArea;
import com.workfront.internship.workflow.dataModel.User;
import com.workfront.internship.workflow.exceptions.dao.DuplicateEntryException;
import com.workfront.internship.workflow.util.ConnectionType;
import com.workfront.internship.workflow.util.DBHelper;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Vahag on 5/27/2017
 */
public class UserDAOImpl extends AbstractDao implements UserDAO {

    private static final Logger LOGGER = Logger.getLogger(UserDAOImpl.class);

    public static final String id = "id";
    public static final String firstName = "first_name";
    public static final String lastName = "last_name";
    public static final String email = "email";
    public static final String password = "passcode";
    public static final String rating = "rating";

    public UserDAOImpl() {
        this(ConnectionType.POOL);
    }

    public UserDAOImpl(ConnectionType connectionType) {
        this.connectionType = connectionType;
    }

    /**
     * @see UserDAO#add(User)
     * @param user
     * @return
     */
    @Override
    public long add(User user) {
        long id = 0;
        String addSql = "INSERT INTO  user (first_name, last_name, email, passcode, rating) " +
                "VALUES (?, ?, ?, ?, ?)";

        String subscribeSql = "INSERT INTO  user_apparea (user_id, apparea_id) " +
                "VALUES (?, ?)";
        try (Connection conn = DBHelper.getConnection(connectionType);
             PreparedStatement addStmt = conn.prepareStatement(addSql, Statement.RETURN_GENERATED_KEYS)) {
            //conn.setAutoCommit(false);

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

//            subscribeStmt.setLong(1, id);
//            for (AppArea appArea : AppArea.values()) {
//                subscribeStmt.setLong(2, appArea.getId());
//                subscribeStmt.executeUpdate();
//                conn.commit();
//            }

        } catch (SQLIntegrityConstraintViolationException e) {
            LOGGER.error("Duplicate user entry");
            throw new DuplicateEntryException("User with email " + user.getEmail() + " already exists!", e);
        } catch (SQLException e) {
            LOGGER.error("SQL exception");
            throw new RuntimeException(e);
        }
        return user.getId();
    }

    /**
     * @see UserDAO#subscribeToArea(long, long)
     * @param userId
     * @param appAreaId
     */
    @Override
    public void subscribeToArea(long userId, long appAreaId) {
        String sql = "INSERT INTO  user_apparea (user_id, apparea_id) " +
                "VALUES (?, ?)";
        try (Connection conn = DBHelper.getConnection(connectionType);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.setLong(2, appAreaId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("SQL exception");
            throw new RuntimeException(e);
        }
    }


    /**
     * @see UserDAO#unsubscribeToArea(long, long)
     * @param userId
     * @param appAreaId
     */
    @Override
    public void unsubscribeToArea(long userId, long appAreaId) {
        String sql = "DELETE FROM  user_apparea " +
                " WHERE user_id = ? AND apparea_id = ?";
        try (Connection conn = DBHelper.getConnection(connectionType);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.setLong(2, appAreaId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("SQL exception");
            throw new RuntimeException(e);
        }
    }

    /**
     * @see UserDAO#getByName(String)
     * @param name
     * @return
     */
    @Override
    public List<User> getByName(String name) {
        String filteredName = name.replaceAll(" ", "");
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM  user " +
                "WHERE CONCAT (first_name, last_name) LIKE ?";
        try (Connection conn = DBHelper.getConnection(connectionType);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, filteredName + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                userList.add(fromResultSet(user, rs));
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception");
            throw new RuntimeException(e);
        }
        return userList;
    }

    /**
     * @see UserDAO#getById(long)
     * @param id
     * @return
     */
    @Override
    public User getById(long id) {
        User user = null;
        String sql = "SELECT * FROM  user " +
                "WHERE id = ?";
        try (Connection conn = DBHelper.getConnection(connectionType);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                user = new User();
                user = fromResultSet(user, rs);
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception");
            throw new RuntimeException(e);
        }
        return user;
    }

    /**
     * @see UserDAO#getAppAreasById(long)
     * @param userId
     * @return
     */
    @Override
    public List<AppArea> getAppAreasById(long userId) {
        List<AppArea> appAreaList = new ArrayList<>();
        String sql = "SELECT * FROM  user_apparea " +
                "WHERE user_id = ?";
        try (Connection conn = DBHelper.getConnection(connectionType);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                long appAreaId = rs.getLong("apparea_id");
                appAreaList.add(AppArea.getById(appAreaId));
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception");
            throw new RuntimeException(e);
        }
        return appAreaList;
    }

    /**
     * @see UserDAO#deleteById(long)
     * @param id
     */
    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM  user " +
                "WHERE id = ?";
        try (Connection conn = DBHelper.getConnection(connectionType);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQL exception");
            throw new RuntimeException(e);
        }
    }

    /**
     * @see UserDAO#deleteAll()
     */
    @Override
    public void deleteAll() {
        String sql = "DELETE FROM  user ";
        try (Connection conn = DBHelper.getConnection(connectionType);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            LOGGER.error("SQL exception");
            throw new RuntimeException(e);
        }

    }



    /**
     * Sets users fields values from result set
     * @param user
     * @param rs
     * @return
     */
    public static User fromResultSet(User user, ResultSet rs) {
        try {
            user.setId(rs.getLong(id));
            user.setFirstName(rs.getString(firstName));
            user.setLastName(rs.getString(lastName));
            user.setEmail(rs.getString(email));
            user.setPassword(rs.getString(password));
            user.setRating(rs.getInt(rating));

        } catch (SQLException e) {
            LOGGER.error("SQL exception");
            throw new RuntimeException(e);
        }
        return user;
    }
}
