package com.workfront.internship.workflow.service.impl;

import com.workfront.internship.workflow.dao.AppAreaDAO;
import com.workfront.internship.workflow.dao.impl.PostDAOImpl;
import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.exceptions.service.InvalidObjectException;
import com.workfront.internship.workflow.exceptions.service.ServiceLayerException;
import com.workfront.internship.workflow.service.AppAreaService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * Created by nane on 6/5/17
 */
public class AppAreaServiceImpl implements AppAreaService {

    private static final Logger LOGGER = Logger.getLogger(AppAreaServiceImpl.class);

    private final AppAreaDAO appAreaDAO;

    @Autowired
    public AppAreaServiceImpl(@Qualifier("appAreaDAOSpring") AppAreaDAO appAreaDAO) {
        this.appAreaDAO = appAreaDAO;
    }

    @Override
    public long add(AppArea appArea) {
        if (appArea == null || !appArea.isValid()) {
            LOGGER.error("Application Area is invalid. Failed to add to the database");
            throw new InvalidObjectException("Invalid Application Area");
        }
        long id = 0;
        try{
            id = appAreaDAO.add(appArea);
        }catch (RuntimeException e){
            LOGGER.error("Failed to add the application area to database");
            throw new RuntimeException(e);
        }
        return id;
    }

    @Override
    public List<User> getUsersById(long id) {
        if (id < 1) {
            LOGGER.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }

        List<User> users;
        try {
            users = appAreaDAO.getUsersById(id);
        } catch (RuntimeException e) {
            LOGGER.error("Failed to delete the user");
            throw new ServiceLayerException("Failed to delete the user", e);
        }
        return users;
    }

    @Override
    public AppArea getById(long id) {
        if (id < 1) {
            LOGGER.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }

        AppArea appArea;
        try {
            appArea = appAreaDAO.getById(id);
        } catch (RuntimeException e) {
            LOGGER.error("Failed to delete the user");
            throw new ServiceLayerException("Failed to delete the user", e);
        }
        return appArea;
    }

    @Override
    public void deleteById(long id) {
        if (id < 1) {
            LOGGER.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }

        try {
            appAreaDAO.deleteById(id);
        } catch (RuntimeException e) {
            LOGGER.error("Failed to delete the user");
            throw new ServiceLayerException("Failed to delete the user", e);
        }
    }
}
