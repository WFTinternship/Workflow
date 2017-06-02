package com.workfront.internship.dao;

import com.workfront.internship.dao.impl.AppAreaDAOImpl;
import com.workfront.internship.dao.impl.UserDAOImpl;
import com.workfront.internship.dataModel.AppArea;
import com.workfront.internship.dataModel.User;
import com.workfront.internship.dbConstants.DataBaseConstants;
import com.workfront.internship.util.DBHelper;
import com.workfront.internship.util.DaoTestUtil;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppAreaImplIntegrationTest {

    private AppAreaDAO appAreaDAO;
    private UserDAO userDAO;
    private AppArea appArea;
    private User user;

    /**
     * Gets AppArea and creates new User
     */
    @Before
    public void setup(){
        appAreaDAO = new AppAreaDAOImpl();
        userDAO = new UserDAOImpl();
        appArea = AppArea.values()[0];
        user = DaoTestUtil.getRandomUser();
    }

    @After
    public void tearDown(){
        if(appAreaDAO.getById(appArea.getId()) == null){
            appAreaDAO.add(appArea);
        }
        try {
            userDAO.deleteById(user.getId());
        }catch (RuntimeException e){

        }
    }

    // region <TEST CASE>

    @Test
    public void add_success(){
        appAreaDAO.deleteById(appArea.getId());
        //Test method
        long appAreaId = appAreaDAO.add(appArea);

        AppArea actualAppArea = appAreaDAO.getById(appAreaId);
        assertTrue(appArea.equals(actualAppArea));
    }

    @Test
    public void deleteById_success(){
        //Test method
        appAreaDAO.deleteById(appArea.getId());

        assertNull(appAreaDAO.getById(appArea.getId()));
    }

    @Test
    public void getUsersById_success(){
        long userId = userDAO.add(user);
        userDAO.subscribeToArea(userId, appArea.getId());
        //Test method
        List<User> userList = appAreaDAO.getUsersById(appArea.getId());

        assertTrue(userList.contains(user));
    }

    // endregion

    // region <HELPERS>


    // endregion
}
