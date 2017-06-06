package com.workfront.internship.workflow.service.impl;

import com.workfront.internship.workflow.dao.AppAreaDAO;
import com.workfront.internship.workflow.dao.impl.AppAreaDAOImpl;
import com.workfront.internship.workflow.dao.impl.PostDAOImpl;
import com.workfront.internship.workflow.dataModel.AppArea;
import com.workfront.internship.workflow.dataModel.User;
import com.workfront.internship.workflow.exceptions.service.InvalidObjectException;
import com.workfront.internship.workflow.service.AppAreaService;
import com.workfront.internship.workflow.service.util.Validator;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by nane on 6/5/17.
 */
public class AppAreaServiceImpl implements AppAreaService {

    private static final Logger logger = Logger.getLogger(PostDAOImpl.class);

    private AppAreaDAO appAreaDAO = new AppAreaDAOImpl();

    @Override
    public long add(AppArea appArea) {
        if (!Validator.isValidAppArea(appArea)){
            logger.error("Application Area is invalid. Failed to add to the database");
            throw new InvalidObjectException("Invalid Application Area");
        }
        long id = 0;
        try{
            id = appAreaDAO.add(appArea);
        }catch (RuntimeException e){
            logger.error("Failed to add the application area to database");
        }
        return id;
    }

    @Override
    public List<User> getUsersById(long appAreaId) {
        return appAreaDAO.getUsersById(appAreaId);
    }

    @Override
    public AppArea getById(long id) {
        return appAreaDAO.getById(id);
    }

    @Override
    public void deleteById(long id) {
        appAreaDAO.deleteById(id);
    }
}
