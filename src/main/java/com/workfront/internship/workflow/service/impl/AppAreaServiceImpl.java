package com.workfront.internship.workflow.service.impl;

import com.workfront.internship.workflow.dao.AppAreaDAO;
import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.exceptions.dao.DAOException;
import com.workfront.internship.workflow.exceptions.service.InvalidObjectException;
import com.workfront.internship.workflow.exceptions.service.ServiceLayerException;
import com.workfront.internship.workflow.service.AppAreaService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by nane on 6/5/17
 */
@Service
@Transactional
public class AppAreaServiceImpl implements AppAreaService {

    private static final Logger LOGGER = Logger.getLogger(AppAreaServiceImpl.class);

    private final AppAreaDAO appAreaDAO;

    @Autowired
    public AppAreaServiceImpl(@Qualifier("appAreaDAOSpringImpl") AppAreaDAO appAreaDAO) {
        this.appAreaDAO = appAreaDAO;
    }

    /**
     * @see AppAreaService#add(AppArea)
     */
    @Override
    public long add(AppArea appArea) {
        if (appArea == null || !appArea.isValid()) {
            LOGGER.error("Application Area is invalid. Failed to add to the database");
            throw new InvalidObjectException("Invalid Application Area");
        }
        long id;
        try{
            id = appAreaDAO.add(appArea);
        }catch (DAOException e){
            LOGGER.error("Failed to add the application area to database");
            throw new RuntimeException(e);
        }
        return id;
    }

    /**
     * @see AppAreaService#getUsersById(long)
     */
    @Override
    public List<User> getUsersById(long id) {
        if (id < 1) {
            LOGGER.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }

        List<User> users;
        try {
            users = appAreaDAO.getUsersById(id);
        } catch (DAOException e) {
            LOGGER.error("Failed to get users with specified id");
            throw new ServiceLayerException("Failed to get users with specified id", e);
        }
        return users;
    }

    /**
     * @see AppAreaService#getById(long)
     */
    @Override
    public AppArea getById(long id) {
        if (id < 1) {
            LOGGER.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }

        AppArea appArea;
        try {
            appArea = appAreaDAO.getById(id);
        } catch (DAOException e) {
            LOGGER.error("Failed to get the Application Area");
            throw new ServiceLayerException("Failed to get the Application Area", e);
        }
        return appArea;
    }

    /**
     * @see AppAreaService#deleteById(long)
     */
    @Override
    public void deleteById(long id) {
        if (id < 1) {
            LOGGER.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }

        try {
            appAreaDAO.deleteById(id);
        } catch (DAOException e) {
            LOGGER.error("Failed to delete the application area");
            throw new ServiceLayerException("Failed to delete the application area", e);
        }
    }
}
