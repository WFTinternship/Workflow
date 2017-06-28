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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
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

    // region <TEST_CASE>

    /**
     * @see UserService#add(User)
     */
    @Test(expected = InvalidObjectException.class)
    public void add_nullUser() {
        userService.add(null);
    }

    /**
     * @see UserService#add(User)
     */
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

    /**
     * @see UserService#add(User)
     */
    @Test(expected = DuplicateEntryException.class)
    public void add_existingUser() {
        User user = DaoTestUtil.getRandomUser();
        doReturn(user).when(userDAOMock).getByEmail(anyString());

        //Test method
        userService.add(user);
    }

    /**
     * @see UserService#add(User)
     */
    @Test
    public void add_success() {
        User user = DaoTestUtil.getRandomUser();
        long id = 15;
        doReturn(id).when(userDAOMock).add(user);

        //Test method
        long actualId = userService.add(user);
        assertEquals(actualId, id);

        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(userDAOMock, times(1)).add(argument.capture());
        assertEquals(user, argument.getValue());
    }

    /**
     * @see UserService#getByName(String)
     */
    @Test(expected = InvalidObjectException.class)
    public void getByName_emptyName() {
        userService.getByName(null);
    }

    /**
     * @see UserService#getByName(String)
     */
    @Test(expected = ServiceLayerException.class)
    public void getByName_DAOException() {
        doThrow(RuntimeException.class).when(userDAOMock).getByName(anyString());

        //Test method
        userService.getByName("123");
    }

    /**
     * @see UserService#getByName(String)
     */
    @Test
    public void getByName_success() {
        String name = "John";
        List<User> userList = new ArrayList<>();
        doReturn(userList).when(userDAOMock).getByName(name);

        //Test method
        List<User> actualList = userService.getByName(name);
        assertEquals(actualList, userList);

        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        verify(userDAOMock, only()).getByName(argument.capture());
        assertEquals(name, argument.getValue());
    }

    /**
     * @see UserService#getById(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void getById_negativeId() {
        userService.getById(-1);
    }

    /**
     * @see UserService#getById(long)
     */
    @Test(expected = ServiceLayerException.class)
    public void getById_DAOException() {
        doThrow(RuntimeException.class).when(userDAOMock).getById(anyInt());

        //Test method
        userService.getById(123);
    }

    /**
     * @see UserService#getById(long)
     */
    @Test
    public void getById_success() {
        Long id = 123L;
        User user = new User();
        doReturn(user).when(userDAOMock).getById(id);

        //Test method
        User actualUser = userService.getById(id);
        assertEquals(actualUser, user);

        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        verify(userDAOMock, only()).getById(argument.capture());
        assertEquals(id, argument.getValue());
    }

    /**
     * @see UserService#getByEmail(String)
     */
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

    /**
     * @see UserService#getByEmail(String)
     */
    @Test(expected = ServiceLayerException.class)
    public void getByEmail_DAOException() {
        doThrow(RuntimeException.class).when(userDAOMock).getByEmail(anyString());

        //Test method
        userService.getByEmail("123");
    }

    /**
     * @see UserService#getByEmail(String)
     */
    @Test
    public void getByEmail_success() {
        String email = "abc";
        User user = DaoTestUtil.getRandomUser();
        doReturn(user).when(userDAOMock).getByEmail(email);

        //Test method
        User actualUser = userService.getByEmail(email);
        assertEquals(actualUser, user);

        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        verify(userDAOMock, only()).getByEmail(argument.capture());
        assertEquals(email, argument.getValue());
    }

    /**
     * @see UserService#getAppAreasById(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void getAppAreasById_negativeId() {
        userService.getAppAreasById(-1);
    }

    /**
     * @see UserService#getAppAreasById(long)
     */
    @Test(expected = ServiceLayerException.class)
    public void getAppAreasById_DAOException() {
        doThrow(RuntimeException.class).when(userDAOMock).getAppAreasById(anyInt());

        //Test method
        userService.getAppAreasById(123);
    }

    /**
     * @see UserService#getAppAreasById(long)
     */
    @Test
    public void getAppAreasById_success() {
        Long id = 123L;
        List<AppArea> appAreas = new ArrayList<>();
        doReturn(appAreas).when(userDAOMock).getAppAreasById(id);

        //Test method
        List<AppArea> actualAppAreas = userService.getAppAreasById(id);
        assertEquals(appAreas, actualAppAreas);

        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        verify(userDAOMock, only()).getAppAreasById(argument.capture());
        assertEquals(id, argument.getValue());
    }

    /**
     * @see UserService#subscribeToArea(long, long)
     */
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

    /**
     * @see UserService#subscribeToArea(long, long)
     */
    @Test(expected = ServiceLayerException.class)
    public void subscribeToArea_DAOException() {
        doThrow(RuntimeException.class).when(userDAOMock).subscribeToArea(anyInt(), anyInt());

        //Test method
        userService.subscribeToArea(10, 1);
    }

    /**
     * @see UserService#subscribeToArea(long, long)
     */
    @Test
    public void subscribeToArea_success() {
        Long userId = 1L, appAreaId = 1L;
        List<Long> expected = Arrays.asList(userId, appAreaId);
        //Test method
        userService.subscribeToArea(userId, appAreaId);
        verify(userDAOMock, times(1)).subscribeToArea(userId, appAreaId);

        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        verify(userDAOMock, only()).subscribeToArea(argument.capture(), argument.capture());
        assertEquals(expected, argument.getAllValues());
    }

    /**
     * @see UserService#unsubscribeToArea(long, long)
     */
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

    /**
     * @see UserService#unsubscribeToArea(long, long)
     */
    @Test(expected = ServiceLayerException.class)
    public void unsubscribeToArea_DAOException() {
        doThrow(RuntimeException.class).when(userDAOMock).unsubscribeToArea(anyInt(), anyInt());

        //Test method
        userService.unsubscribeToArea(10, 1);
    }

    /**
     * @see UserService#unsubscribeToArea(long, long)
     */
    @Test
    public void unsubscribeToArea_success() {
        Long userId = 1L, appAreaId = 1L;
        List<Long> expected = Arrays.asList(userId, appAreaId);

        //Test method
        userService.unsubscribeToArea(1, 1);
        verify(userDAOMock, times(1)).unsubscribeToArea(userId, appAreaId);

        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        verify(userDAOMock, only()).unsubscribeToArea(argument.capture(), argument.capture());
        assertEquals(expected, argument.getAllValues());
    }

    /**
     * @see UserService#deleteById(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void deleteById_negativeId() {
        //Test method
        userService.deleteById(-1);
    }

    /**
     * @see UserService#deleteById(long)
     */
    @Test(expected = ServiceLayerException.class)
    public void deleteById_DAOException() {
        doThrow(RuntimeException.class).when(userDAOMock).deleteById(anyInt());

        //Test method
        userService.deleteById(1);
    }

    /**
     * @see UserService#deleteById(long)
     */
    @Test
    public void deleteById_success() {
        Long id = 1L;
        //Test method
        userService.deleteById(id);
        verify(userDAOMock, times(1)).deleteById(id);

        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        verify(userDAOMock, only()).deleteById(argument.capture());
        assertEquals(id, argument.getValue());
    }

    /**
     * @see UserService#deleteAll()
     */
    @Test(expected = ServiceLayerException.class)
    public void deleteAll_DAOException() {
        doThrow(RuntimeException.class).when(userDAOMock).deleteAll();

        //Test method
        userService.deleteAll();
    }

    /**
     * @see UserService#deleteAll()
     */
    @Test
    public void deleteAll_success() {
        //Test method
        userService.deleteAll();
        verify(userDAOMock, times(1)).deleteAll();
    }

    /**
     * @see UserService#authenticate(String, String)
     */
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

    /**
     * @see UserService#authenticate(String, String)
     */
    @Test(expected = ServiceLayerException.class)
    public void authenticate_userNotExist() {
        doReturn(null).when(userDAOMock).getByEmail(anyString());
        //Test method
        userService.authenticate("A", "a");
    }

    /**
     * @see UserService#authenticate(String, String)
     */
    @Test(expected = ServiceLayerException.class)
    public void authenticate_wrongPassword() {
        User user = DaoTestUtil.getRandomUser();
        user.setPassword("123");
        doReturn(user).when(userDAOMock).getByEmail(anyString());
        //Test method
        userService.authenticate("A", "a");
    }

    /**
     * @see UserService#authenticate(String, String)
     */
    @Test
    public void authenticate_success() {
        User user = DaoTestUtil.getRandomUser();
        user.setPassword(ServiceUtils.hashPassword("123"));
        doReturn(user).when(userDAOMock).getByEmail(anyString());
        //Test method
        User actualUser = userService.authenticate("A", "123");
        assertEquals(user, actualUser);
    }

    // endregion

}
