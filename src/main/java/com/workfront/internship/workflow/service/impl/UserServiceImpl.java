package com.workfront.internship.workflow.service.impl;

import com.workfront.internship.workflow.dao.UserDAO;
import com.workfront.internship.workflow.dao.impl.UserDAOImpl;
import com.workfront.internship.workflow.dataModel.AppArea;
import com.workfront.internship.workflow.dataModel.User;
import com.workfront.internship.workflow.exceptions.dao.DuplicateEntryException;
import com.workfront.internship.workflow.exceptions.service.InvalidObjectException;
import com.workfront.internship.workflow.service.UserService;
import com.workfront.internship.workflow.service.util.Validator;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by Vahag on 6/4/2017. 
 */
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    UserDAO userDAO = new UserDAOImpl();

    @Override
    public long add(User user) {
        if(!Validator.isValidUser(user)){
            LOGGER.error("Not valid user. Failed to add.");
            throw new InvalidObjectException();
        }
        long id = 0;
        try {
            userDAO.add(user);
        }catch (DuplicateEntryException e){
            LOGGER.error("Duplicate user entry");
            throw new DuplicateEntryException("User with email " + user.getEmail() + " already exists!", e);
        }catch (RuntimeException e){
            LOGGER.error("Failed to add the user");
        }
        return id;
    }

    @Override
    public List<User> getByName(String name) {
        if (Validator.isEmpty(name)){
            LOGGER.error("Name is not valid");
            throw new InvalidObjectException("Not valid title");
        }
        //TODO exception handling
        List<User> users = userDAO.getByName(name);
        return users;
    }

    @Override
    public User getById(long id) {
        if (id < 1){
            LOGGER.error("Id is not valid");
            throw new InvalidObjectException("Invalid user id");
        }
        //TODO exception handling
        User user = userDAO.getById(id);
        return user;
    }

    @Override
    public List<AppArea> getAppAreasById(long id) {
        if (id < 1){
            LOGGER.error("Id is not valid");
            throw new InvalidObjectException("Invalid user id");
        }
        //TODO exception handling
        List<AppArea> appAreas = userDAO.getAppAreasById(id);
        return appAreas;
    }

    @Override
    public void subscribeToArea(long userId, long appAreaId) {
        if(userId < 1){
            LOGGER.error("Id is not valid");
            throw new InvalidObjectException("Invalid user id");
        }
        if(appAreaId < 1){
            LOGGER.error("Id is not valid");
            throw new InvalidObjectException("Invalid app area id");
        }
        //TODO exception handling
        userDAO.subscribeToArea(userId, appAreaId);
    }

    @Override
    public void unsubscribeToArea(long userId, long appAreaId) {
        if(userId < 1){
            LOGGER.error("Id is not valid");
            throw new InvalidObjectException("Invalid user id");
        }
        if(appAreaId < 1){
            LOGGER.error("Id is not valid");
            throw new InvalidObjectException("Invalid app area id");
        }
        //TODO exception handling
        userDAO.unsubscribeToArea(userId, appAreaId);
    }

    @Override
    public void deleteById(long id) {
        if (id < 1){
            LOGGER.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }
        //TODO exception handling
        userDAO.deleteById(id);
    }

    @Override
    public void deleteAll() {
        //TODO ask about catching the exception
        userDAO.deleteAll();
    }
}
