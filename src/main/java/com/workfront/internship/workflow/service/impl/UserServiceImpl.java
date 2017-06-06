package com.workfront.internship.workflow.service.impl;

import com.workfront.internship.workflow.dao.UserDAO;
import com.workfront.internship.workflow.dao.impl.UserDAOImpl;
import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.exceptions.dao.DuplicateEntryException;
import com.workfront.internship.workflow.exceptions.service.InvalidObjectException;
import com.workfront.internship.workflow.exceptions.service.ServiceLayerException;
import com.workfront.internship.workflow.service.UserService;
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
        if(!user.isValid()){
            LOGGER.error("Not valid user. Failed to add.");
            throw new InvalidObjectException();
        }
        long id = 0;
        try {
            userDAO.add(user);
        }catch (RuntimeException e){
            LOGGER.error("Failed to add the user");
            throw new ServiceLayerException("Failed to add the user", e);
        }
        return id;
    }

    @Override
    public List<User> getByName(String name) {
        List<User> users;
        if (isEmpty(name)){
            LOGGER.error("Name is not valid");
            throw new InvalidObjectException("Not valid name");
        }
        try {
             users = userDAO.getByName(name);
        }catch (RuntimeException e){
            LOGGER.error("Failed to find such users");
            throw new ServiceLayerException("Failed to find such users", e);
        }
        return users;
    }

    @Override
    public User getById(long id) {
        User user;
        if (id < 1){
            LOGGER.error("Id is not valid");
            throw new InvalidObjectException("Invalid user id");
        }
        try {
            user = userDAO.getById(id);
        }catch (RuntimeException e){
            LOGGER.error("Failed to find such a user");
            throw new ServiceLayerException("Failed to find such a user", e);
        }
        return user;
    }

    @Override
    public List<AppArea> getAppAreasById(long id) {
        List<AppArea> appAreas;
        if (id < 1){
            LOGGER.error("Id is not valid");
            throw new InvalidObjectException("Invalid user id");
        }
        try {
            appAreas = userDAO.getAppAreasById(id);
        }catch (RuntimeException e){
            LOGGER.error("Failed to find app areas");
            throw new ServiceLayerException("Failed to find app areas", e);
        }
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
        try {
            userDAO.subscribeToArea(userId, appAreaId);
        }catch (RuntimeException e){
            LOGGER.error("Failed to subscribe to the app area");
            throw new ServiceLayerException("Failed to subscribe to the app area", e);
        }
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
        try {
            userDAO.unsubscribeToArea(userId, appAreaId);
        }catch (RuntimeException e){
            LOGGER.error("Failed to unsubscribe from the app area");
            throw new ServiceLayerException("Failed to unsubscribe from the app area", e);
        }
    }

    @Override
    public void deleteById(long id) {
        if (id < 1){
            LOGGER.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }
        try {
            userDAO.deleteById(id);
        }catch (RuntimeException e){
            LOGGER.error("Failed to delete the user");
            throw new ServiceLayerException("Failed to delete the user", e);
        }
    }

    @Override
    public void deleteAll() {
        try {
            userDAO.deleteAll();
        }catch (RuntimeException e){
            LOGGER.error("Failed to delete all the user");
            throw new ServiceLayerException("Failed to delete all the user", e);
        }
    }

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

}
