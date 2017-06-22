package com.workfront.internship.workflow.service;

import com.workfront.internship.workflow.dao.springJDBC.UserDAOSpringImpl;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.exceptions.service.DuplicateEntryException;
import com.workfront.internship.workflow.exceptions.service.InvalidObjectException;
import com.workfront.internship.workflow.exceptions.service.ServiceLayerException;
import com.workfront.internship.workflow.service.impl.UserServiceImpl;
import com.workfront.internship.workflow.util.DaoTestUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

/**
 * Created by Vahag on 6/22/2017
 */
public class UserServiceImplUnitTest extends BaseUnitTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserDAOSpringImpl userDAOMock;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = InvalidObjectException.class)
    public void add_nullUser() {
        userService.add(null);
    }

    @Test
    public void add_userNotValid() {
        User user = DaoTestUtil.getRandomUser();
        user.setFirstName(null);

        try {
            //Test method
            userService.add(user);
            fail();
        } catch (RuntimeException e) {
            assertTrue(e instanceof InvalidObjectException);
        }

        user.setFirstName("John");
        user.setLastName(null);

        try {
            //Test method
            userService.add(user);
            fail();
        } catch (RuntimeException e) {
            assertTrue(e instanceof InvalidObjectException);
        }

        user.setFirstName("John");
        user.setLastName("Smith");
        user.setEmail(null);

        try {
            //Test method
            userService.add(user);
            fail();
        } catch (RuntimeException e) {
            assertTrue(e instanceof InvalidObjectException);
        }

        user.setFirstName("John");
        user.setLastName("Smith");
        user.setEmail("abc");
        user.setPassword(null);

        try {
            //Test method
            userService.add(user);
            fail();
        } catch (RuntimeException e) {
            assertTrue(e instanceof InvalidObjectException);
        }

    }

    @Test(expected = DuplicateEntryException.class)
    public void add_existingUser() {
        User user = DaoTestUtil.getRandomUser();
        doReturn(user).when(userDAOMock).getByEmail(anyString());

        //Test method
        userService.add(user);
    }

    @Test
    public void add_success() {
        User user = DaoTestUtil.getRandomUser();
        long id = 15;
        doReturn(id).when(userDAOMock).add(user);

        //Test method
        long actualId = userService.add(user);
        assertEquals(actualId, id);
    }

    @Test(expected = InvalidObjectException.class)
    public void getByName_emptyName() {
        userService.getByName(null);
    }

    @Test(expected = ServiceLayerException.class)
    public void getByName_DAOException() {
        doThrow(RuntimeException.class).when(userDAOMock).getByName(anyString());

        //Test method
        userService.getByName("123");
    }

    @Test
    public void getByName_success() {
        List<User> userList = new ArrayList<>();
        doReturn(userList).when(userDAOMock).getByName(anyString());

        //Test method
        List<User> actualList = userService.getByName("abc");
        assertEquals(actualList, userList);
    }

    @Test(expected = InvalidObjectException.class)
    public void getById_negativeId() {
        userService.getById(-1);
    }

    @Test(expected = ServiceLayerException.class)
    public void getById_DAOException() {
        doThrow(RuntimeException.class).when(userDAOMock).getById(anyInt());

        //Test method
        userService.getById(123);
    }

    @Test
    public void getById_success() {
        User user = new User();
        doReturn(user).when(userDAOMock).getById(anyInt());

        //Test method
        User actualUser = userService.getById(123);
        assertEquals(actualUser, user);
    }

    @Test
    public void getByEmail_emailNotValid() {
        try {
            //Test method
            userService.getByEmail("");
            fail();
        } catch (RuntimeException e) {
            assertTrue(e instanceof InvalidObjectException);
        }

        try {
            //Test method
            userService.getByEmail(null);
            fail();
        } catch (RuntimeException e) {
            assertTrue(e instanceof InvalidObjectException);
        }

    }

    @Test(expected = ServiceLayerException.class)
    public void getByEmail_DAOException() {
        doThrow(RuntimeException.class).when(userDAOMock).getByEmail(anyString());

        //Test method
        userService.getByEmail("123");
    }

    @Test
    public void getByEmail_success() {
        User user = DaoTestUtil.getRandomUser();
        doReturn(user).when(userDAOMock).getByEmail(anyString());

        //Test method
        User actualUser = userService.getByEmail("abc");
        assertEquals(actualUser, user);
    }


}
