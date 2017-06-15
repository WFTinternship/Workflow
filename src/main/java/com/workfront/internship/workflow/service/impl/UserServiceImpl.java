package com.workfront.internship.workflow.service.impl;

import com.workfront.internship.workflow.dao.UserDAO;
import com.workfront.internship.workflow.dao.impl.UserDAOImpl;
import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.exceptions.service.DuplicateEntryException;
import com.workfront.internship.workflow.exceptions.service.InvalidObjectException;
import com.workfront.internship.workflow.exceptions.service.ServiceLayerException;
import com.workfront.internship.workflow.service.UserService;
import com.workfront.internship.workflow.util.DBHelper;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Vahag on 6/4/2017
 */
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    private UserDAO userDAO = new UserDAOImpl();

    /**
     * @param user
     * @return
     * @see UserService#add(User)
     */
    @Override
    public long add(User user) {
        long id = 0;
        if (!user.isValid()) {
            LOGGER.error("Not valid user. Failed to add.");
            throw new InvalidObjectException();
        }
        if (userDAO.getByEmail(user.getEmail()).getFirstName() != null) {
            LOGGER.error("Failed to add. User already exists");
            throw new DuplicateEntryException("User already exists");
        }
        userDAO = new UserDAOImpl();
        DataSource dataSource;
        try {
            dataSource = DBHelper.getPooledConnection();
            Connection connection = null;
            try {
                connection = dataSource.getConnection();
                connection.setAutoCommit(false);
                id = userDAO.add(user, dataSource);
                for (AppArea appArea : AppArea.values()) {
                    userDAO.subscribeToArea(user.getId(), appArea.getId());
                }
                connection.commit();
            } catch (RuntimeException e) {
                if (connection != null) {
                    connection.rollback();
                }
                LOGGER.error("Failed to add the user");
                throw new ServiceLayerException("Failed to add the user", e);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * @param name
     * @return
     * @see UserService#getByName(String)
     */
    @Override
    public List<User> getByName(String name) {
        List<User> users;
        if (isEmpty(name)) {
            LOGGER.error("Name is not valid");
            throw new InvalidObjectException("Not valid name");
        }
        try {
            users = userDAO.getByName(name);
        } catch (RuntimeException e) {
            LOGGER.error("Failed to find such users");
            throw new ServiceLayerException("Failed to find such users", e);
        }
        return users;
    }

    /**
     * @param id
     * @return
     * @see UserService#getById(long)
     */
    @Override
    public User getById(long id) {
        User user;
        if (id < 1) {
            LOGGER.error("Id is not valid");
            throw new InvalidObjectException("Invalid user id");
        }
        try {
            user = userDAO.getById(id);
        } catch (RuntimeException e) {
            LOGGER.error("Failed to find such a user");
            throw new ServiceLayerException("Failed to find such a user", e);
        }
        return user;
    }

    @Override
    public User getByEmail(String email) {
        User user;
        if (isEmpty(email)){
            LOGGER.error("Email is not valid");
            throw new InvalidObjectException("Not valid email");
        }
        try {
            user = userDAO.getByEmail(email);
        } catch (RuntimeException e){
            LOGGER.error("Couldn't get the user");
            throw new ServiceLayerException("Failed to find such a user", e);
        }
        return user;
    }

    /**
     * @param id
     * @return
     * @see UserService#getAppAreasById(long)
     */
    @Override
    public List<AppArea> getAppAreasById(long id) {
        List<AppArea> appAreas;
        if (id < 1) {
            LOGGER.error("Id is not valid");
            throw new InvalidObjectException("Invalid user id");
        }
        try {
            appAreas = userDAO.getAppAreasById(id);
        } catch (RuntimeException e) {
            LOGGER.error("Failed to find app areas");
            throw new ServiceLayerException("Failed to find app areas", e);
        }
        return appAreas;
    }

    /**
     * @param userId
     * @param appAreaId
     * @see UserService#subscribeToArea(long, long)
     */
    @Override
    public void subscribeToArea(long userId, long appAreaId) {
        if (userId < 1) {
            LOGGER.error("Id is not valid");
            throw new InvalidObjectException("Invalid user id");
        }
        if (appAreaId < 1) {
            LOGGER.error("Id is not valid");
            throw new InvalidObjectException("Invalid app area id");
        }
        try {
            userDAO.subscribeToArea(userId, appAreaId);
        } catch (RuntimeException e) {
            LOGGER.error("Failed to subscribe to the app area");
            throw new ServiceLayerException("Failed to subscribe to the app area", e);
        }
    }

    /**
     * @param userId
     * @param appAreaId
     * @see UserService#unsubscribeToArea(long, long)
     */
    @Override
    public void unsubscribeToArea(long userId, long appAreaId) {
        if (userId < 1) {
            LOGGER.error("Id is not valid");
            throw new InvalidObjectException("Invalid user id");
        }
        if (appAreaId < 1) {
            LOGGER.error("Id is not valid");
            throw new InvalidObjectException("Invalid app area id");
        }
        try {
            userDAO.unsubscribeToArea(userId, appAreaId);
        } catch (RuntimeException e) {
            LOGGER.error("Failed to unsubscribe from the app area");
            throw new ServiceLayerException("Failed to unsubscribe from the app area", e);
        }
    }

    /**
     * @param id
     * @see UserService#deleteById(long)
     */
    @Override
    public void deleteById(long id) {
        if (id < 1) {
            LOGGER.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }
        try {
            userDAO.deleteById(id);
        } catch (RuntimeException e) {
            LOGGER.error("Failed to delete the user");
            throw new ServiceLayerException("Failed to delete the user", e);
        }
    }

    /**
     * @see UserService#deleteAll()
     */
    @Override
    public void deleteAll() {
        try {
            userDAO.deleteAll();
        } catch (RuntimeException e) {
            LOGGER.error("Failed to delete all the user");
            throw new ServiceLayerException("Failed to delete all the user", e);
        }
    }

    /**
     * @see UserService#authenticate(String, String)
     * @param email is input from client
     * @param password is input from client
     */
    @Override
    public User authenticate(String email, String password) {
        if (isEmpty(password)){
            LOGGER.error("Password is not valid");
            throw new InvalidObjectException("Not valid password");
        }

        User user = getByEmail(email);

        //TODO: Password should be hashed
        if (user != null && user.getPassword().equals(password)){
            return user;
        }else {
            LOGGER.error("Invalid email-password combination!");
            throw new ServiceLayerException("Invalid email-password combination!");
        }
    }

    public static boolean isEmpty(String string) {
        return string == null || string.trim().length() == 0;
    }

}
