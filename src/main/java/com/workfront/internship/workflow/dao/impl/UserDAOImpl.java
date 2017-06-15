package com.workfront.internship.workflow.dao.impl;

import com.workfront.internship.workflow.dao.AbstractDao;
import com.workfront.internship.workflow.dao.UserDAO;
import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.util.DBHelper;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Vahag on 5/27/2017
 */
@SuppressWarnings("WeakerAccess")
public class UserDAOImpl extends AbstractDao implements UserDAO {

    public static final String id = "id";
    public static final String firstName = "first_name";
    public static final String lastName = "last_name";
    public static final String email = "email";
    public static final String password = "passcode";
    public static final String avatarURl = "avatar_url";
    public static final String rating = "rating";
    private static final Logger LOGGER = Logger.getLogger(UserDAOImpl.class);

    public UserDAOImpl() {
        dataSource = DBHelper.getPooledConnection();
    }

    public UserDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Sets users fields values from result set
     *
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
            user.setAvatarURL(rs.getString(avatarURl));
            user.setRating(rs.getInt(rating));

        } catch (SQLException e) {
            LOGGER.error("SQL exception");
            throw new RuntimeException(e);
        }
        return user;
    }

    /**
     * @param user
     * @return
     * @see UserDAO#add(User)
     */
    @Override
    public long add(User user) {
        long id = 0;
        try {
            id = add(user, dataSource.getConnection());
        } catch (SQLException e) {
            LOGGER.error("SQL exception");
            throw new RuntimeException(e);
        }
        return id;
    }

    @Override
    public long add(User user, Connection connection) {
        long id = 0;
        String addSql = "INSERT INTO  user (first_name, last_name, email, passcode, avatar_url, rating) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement addStmt = null;
        ResultSet resultSet = null;
        try {
            addStmt = connection.prepareStatement(addSql, Statement.RETURN_GENERATED_KEYS);
            addStmt.setString(1, user.getFirstName());
            addStmt.setString(2, user.getLastName());
            addStmt.setString(3, user.getEmail());
            addStmt.setString(4, user.getPassword());
            addStmt.setString(5, user.getAvatarURL());
            addStmt.setInt(6, user.getRating());

            addStmt.executeUpdate();
            resultSet = addStmt.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            user.setId(id);
        } catch (SQLException e) {
            LOGGER.error("SQL exception");
            throw new RuntimeException(e);
        }finally {
            closeResources(connection, addStmt, resultSet);
        }
        return user.getId();
    }

    /**
     * @param userId
     * @param appAreaId
     * @see UserDAO#subscribeToArea(long, long)
     */
    @Override
    public void subscribeToArea(long userId, long appAreaId) {
        String sql = "INSERT INTO  user_apparea (user_id, apparea_id) " +
                "VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, userId);
            stmt.setLong(2, appAreaId);

            stmt.execute();

        } catch (SQLException e) {
            LOGGER.error("SQL exception");
            throw new RuntimeException(e);
        } finally {
            closeResources(conn, stmt);
        }
    }

    /**
     * @param userId
     * @param appAreaId
     * @see UserDAO#unsubscribeToArea(long, long)
     */
    @Override
    public void unsubscribeToArea(long userId, long appAreaId) {
        String sql = "DELETE FROM  user_apparea " +
                " WHERE user_id = ? AND apparea_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, userId);
            stmt.setLong(2, appAreaId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("SQL exception");
            throw new RuntimeException(e);
        } finally {
            closeResources(conn, stmt);
        }
    }

    /**
     * @param name
     * @return
     * @see UserDAO#getByName(String)
     */
    @Override
    public List<User> getByName(String name) {
        String filteredName = name.replaceAll(" ", "");
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM  user " +
                "WHERE CONCAT (first_name, last_name) LIKE ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, filteredName + "%");
            rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                userList.add(fromResultSet(user, rs));
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception");
            throw new RuntimeException(e);
        } finally {
            closeResources(conn, stmt, rs);
        }
        return userList;
    }

    /**
     * @param id
     * @return
     * @see UserDAO#getById(long)
     */
    @Override
    public User getById(long id) {
        User user = null;
        String sql = "SELECT * FROM  user " +
                "WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                user = new User();
                user = fromResultSet(user, rs);
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception");
            throw new RuntimeException(e);
        } finally {
            closeResources(conn, stmt, rs);
        }
        return user;
    }

    /**
     * @param email
     * @return
     * @see UserDAO#getByEmail(String)
     */
    @Override
    public User getByEmail(String email) {
        User user = new User();
        String sql = "SELECT * FROM user " +
                "WHERE user.email = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            while (rs.next()) {
                user = fromResultSet(user, rs);
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception");
            throw new RuntimeException(e);
        } finally {
            closeResources(conn, stmt, rs);
        }
        return user;
    }

    /**
     * @param userId
     * @return
     * @see UserDAO#getAppAreasById(long)
     */
    @Override
    public List<AppArea> getAppAreasById(long userId) {
        List<AppArea> appAreaList = new ArrayList<>();
        String sql = "SELECT * FROM  user_apparea " +
                "WHERE user_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, userId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                long appAreaId = rs.getLong("apparea_id");
                appAreaList.add(AppArea.getById(appAreaId));
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception");
            throw new RuntimeException(e);
        } finally {
            closeResources(conn, stmt, rs);
        }
        return appAreaList;
    }

    /**
     * @param id
     * @see UserDAO#deleteById(long)
     */
    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM  user " +
                "WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQL exception");
            throw new RuntimeException(e);
        } finally {
            closeResources(conn, stmt);
        }
    }

    /**
     * @see UserDAO#deleteAll()
     */
    @Override
    public void deleteAll() {
        String sql = "DELETE FROM  user ";
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            LOGGER.error("SQL exception");
            throw new RuntimeException(e);
        } finally {
            closeResources(conn, stmt);
        }

    }

}
