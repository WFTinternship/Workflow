package com.workfront.internship.dao;

import com.workfront.internship.dao.impl.UserDAOImpl;
import com.workfront.internship.dataModel.User;
import com.workfront.internship.util.DaoTestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karen on 5/29/2017.
 */
public class UserDAOImplIntegrationTest {

    private UserDAO userDAO;
    private User user;

    List<User> userList = new ArrayList<>();

    @Before
    public void setup(){
        userDAO = new UserDAOImpl();

    }

    @Test
    public void add(){
        user = DaoTestUtil.getRandomUser();
        long expectedUserId = userDAO.add(user);

        User actualUser = userDAO.getById(expectedUserId);

        verifyAddedUser(user, actualUser);
    }

    @After
    public void tearDown(){
        for (User user: userList) {
            userDAO.delete(user.getId());
        }
        userDAO.delete(user.getId());
    }

    private void verifyAddedUser(User user, User actualUser){
        assertEquals(user.getFirstName(),actualUser.getFirstName());
        assertEquals(user.getLastName(),actualUser.getLastName());
        assertEquals(user.getEmail(),actualUser.getEmail());
        assertEquals(user.getPassword(),actualUser.getPassword());
        assertEquals(user.getRating(),actualUser.getRating());
    }

}
