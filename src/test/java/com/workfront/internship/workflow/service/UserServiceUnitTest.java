package com.workfront.internship.workflow.service;

import com.workfront.internship.workflow.dao.springJDBC.UserDAOSpringImpl;
import com.workfront.internship.workflow.domain.AppArea;
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
import static org.mockito.Mockito.*;

/**
 * Created by Vahag on 6/22/2017
 */
public class UserServiceUnitTest extends BaseUnitTest {

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

    @Test(expected = InvalidObjectException.class)
    public void getAppAreasById_negativeId() {
        userService.getAppAreasById(-1);
    }

    @Test(expected = ServiceLayerException.class)
    public void getAppAreasById_DAOException() {
        doThrow(RuntimeException.class).when(userDAOMock).getAppAreasById(anyInt());

        //Test method
        userService.getAppAreasById(123);
    }

    @Test
    public void getAppAreasById_success() {
        List<AppArea> appAreas = new ArrayList<>();
        doReturn(appAreas).when(userDAOMock).getAppAreasById(anyInt());

        //Test method
        List<AppArea> actualAppAreas = userService.getAppAreasById(123);
        assertEquals(appAreas, actualAppAreas);
    }

    @Test
    public void subscribeToArea_negativeId() {
        try {
            //Test method
            userService.subscribeToArea(-1, 5);
            fail();
        } catch (RuntimeException e) {
            assertTrue(e instanceof InvalidObjectException);
        }

        try {
            //Test method
            userService.subscribeToArea(5, -1);
            fail();
        } catch (RuntimeException e) {
            assertTrue(e instanceof InvalidObjectException);
        }
    }

    @Test(expected = ServiceLayerException.class)
    public void subscribeToArea_DAOException() {
        doThrow(RuntimeException.class).when(userDAOMock).subscribeToArea(anyInt(), anyInt());

        //Test method
        userService.subscribeToArea(10, 1);
    }

    @Test
    public void subscribeToArea_success() {
        //Test method
        userService.subscribeToArea(1, 1);
        verify(userDAOMock, times(1)).subscribeToArea(anyInt(), anyInt());
    }

    @Test
    public void unsubscribeToArea_negativeId() {
        try {
            //Test method
            userService.unsubscribeToArea(-1, 5);
            fail();
        } catch (RuntimeException e) {
            assertTrue(e instanceof InvalidObjectException);
        }

        try {
            //Test method
            userService.unsubscribeToArea(5, -1);
            fail();
        } catch (RuntimeException e) {
            assertTrue(e instanceof InvalidObjectException);
        }
    }

    @Test(expected = ServiceLayerException.class)
    public void unsubscribeToArea_DAOException() {
        doThrow(RuntimeException.class).when(userDAOMock).unsubscribeToArea(anyInt(), anyInt());

        //Test method
        userService.unsubscribeToArea(10, 1);
    }

    @Test
    public void unsubscribeToArea_success() {
        //Test method
        userService.unsubscribeToArea(1, 1);
        verify(userDAOMock, times(1)).unsubscribeToArea(anyInt(), anyInt());
    }

    @Test(expected = InvalidObjectException.class)
    public void deleteById_negativeId() {
        //Test method
        userService.deleteById(-1);
    }

    @Test(expected = ServiceLayerException.class)
    public void deleteById_DAOException() {
        doThrow(RuntimeException.class).when(userDAOMock).deleteById(anyInt());

        //Test method
        userService.deleteById(1);
    }

    @Test
    public void deleteById_success() {
        //Test method
        userService.deleteById(1);
        verify(userDAOMock, times(1)).deleteById(anyInt());
    }

    @Test(expected = ServiceLayerException.class)
    public void deleteAll_DAOException() {
        doThrow(RuntimeException.class).when(userDAOMock).deleteAll();

        //Test method
        userService.deleteAll();
    }

    @Test
    public void deleteAll_success() {
        //Test method
        userService.deleteAll();
        verify(userDAOMock, times(1)).deleteAll();
    }

    @Test
    public void authenticate_passwordIsEmpty() {
        try {
            //Test method
            userService.authenticate("A", "");
            fail();
        } catch (RuntimeException e) {
            assertTrue(e instanceof InvalidObjectException);
        }

        try {
            //Test method
            userService.authenticate("A", null);
            fail();
        } catch (RuntimeException e) {
            assertTrue(e instanceof InvalidObjectException);
        }
    }

    @Test(expected = ServiceLayerException.class)
    public void authenticate_userNotExist() {
        doReturn(null).when(userDAOMock).getByEmail(anyString());
        //Test method
        userService.authenticate("A", "a");
    }

    @Test(expected = ServiceLayerException.class)
    public void authenticate_wrongPassword() {
        User user = DaoTestUtil.getRandomUser();
        user.setPassword("123");
        doReturn(user).when(userDAOMock).getByEmail(anyString());
        //Test method
        userService.authenticate("A", "a");
    }

    @Test
    public void authenticate_success() {
        User user = DaoTestUtil.getRandomUser();
        user.setPassword("123");
        doReturn(user).when(userDAOMock).getByEmail(anyString());
        //Test method
        User actualUser = userService.authenticate("A", "123");
        assertEquals(user, actualUser);
    }
}
