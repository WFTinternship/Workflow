package com.workfront.internship.workflow.service;

import com.workfront.internship.workflow.dao.springJDBC.PostDAOSpringImpl;
import com.workfront.internship.workflow.dao.springJDBC.UserDAOSpringImpl;
import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.AppAreaEnum;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.exceptions.service.InvalidObjectException;
import com.workfront.internship.workflow.exceptions.service.ServiceLayerException;
import com.workfront.internship.workflow.service.impl.PostServiceImpl;
import com.workfront.internship.workflow.service.impl.UserServiceImpl;
import com.workfront.internship.workflow.util.DaoTestUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;


/**
 * Created by nane on 6/21/17
 */

public class PostServiceUnitTest extends BaseUnitTest {

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private PostDAOSpringImpl postDAOMock;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDAOSpringImpl userDAOMock;

    private AppArea appArea;

    @Before
    public void init() {
        appArea = new AppArea();
        appArea.setAppAreaEnum(AppAreaEnum.API);
        MockitoAnnotations.initMocks(this);
    }

    /**
     * @see PostService#add(Post)
     */
    @Test
    public void add_postNotValid() {
        Post post = DaoTestUtil.getRandomPost();
        post.setUser(null);

        try {
            // Test method
            postService.add(post);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }

        post.setUser(DaoTestUtil.getRandomUser());
        post.setAppArea(null);
        try {
            // Test method
            postService.add(post);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }

        post.setAppArea(appArea);
        post.setPostTime(null);
        try {
            // Test method
            postService.add(post);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }

        post.setPostTime(new Timestamp(System.currentTimeMillis()));
        post.setTitle(null);
        try {
            // Test method
            postService.add(post);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }

        post.setTitle("my title");
        post.setContent(null);
        try {
            // Test method
            postService.add(post);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }
    }

    /**
     * @see PostService#add(Post)
     */
    @Test(expected = ServiceLayerException.class)
    public void add_DAOException() {
        Post post = DaoTestUtil.getRandomPost();
        doThrow(RuntimeException.class).when(postDAOMock).add(any(Post.class));

        // Test method
        postService.add(post);
    }

    /**
     * @see PostService#add(Post)
     */
    @Test
    public void add_success() {
        Post post = DaoTestUtil.getRandomPost();
        long id = 50;
        doReturn(id).when(postDAOMock).add(post);

        // Test method
        long actualId = postService.add(post);
        assertEquals(id, actualId);

        ArgumentCaptor<Post> argumentCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postDAOMock, only()).add(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue(), post);
    }

    /**
     * @see PostService#setBestAnswer(long, long)
     */
    @Test
    public void setBestAnswer_negativeId() {
        try {

            // Test method
            postService.setBestAnswer(-1, 15);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }

        try {

            // Test method
            postService.setBestAnswer(15, -1);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }
    }

    /**
     * @see PostService#setBestAnswer(long, long)
     */
    @Test(expected = ServiceLayerException.class)
    public void setBestAnswer_DAOException() {
        doThrow(RuntimeException.class).when(postDAOMock).setBestAnswer(anyLong(), anyLong());

        // Test method
        postService.setBestAnswer(15, 17);
    }

    /**
     * @see PostService#setBestAnswer(long, long)
     */
    @Test
    public void setBestAnswer_success() {
        long id = 17, answerId = 15;
        List<Long> expected = Arrays.asList(id, answerId);

        // Test method
        postService.setBestAnswer(id, answerId);
        verify(postDAOMock, times(1)).setBestAnswer(id, answerId);

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(postDAOMock, only()).setBestAnswer(argumentCaptor.capture(), argumentCaptor.capture());
        assertEquals(argumentCaptor.getAllValues(), expected);
    }

    /**
     * @see PostService#getAll()
     */
    @Test(expected = ServiceLayerException.class)
    public void getAll_DAOException() {
        doThrow(RuntimeException.class).when(postDAOMock).getAll();
        // Test method
        postService.getAll();
    }

    /**
     * @see PostService#getAll()
     */
    @Test
    public void getAll_success() {
        // Test method
        postService.getAll();
        verify(postDAOMock, times(1)).getAll();
    }

    /**
     * @see PostService#getById(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void getById_NegativeId() {
        // Test method
        postService.getById(-1);
    }

    /**
     * @see PostService#getById(long)
     */
    @Test(expected = ServiceLayerException.class)
    public void getById_DAOException() {
        long id = 15;
        doThrow(RuntimeException.class).when(postDAOMock).getById(id);
        // Test method
        postService.getById(id);
    }

    /**
     * @see PostService#getById(long)
     */
    @Test
    public void getById_success() {
        Long id = 15L;
        Post post = DaoTestUtil.getRandomPost();
        doReturn(post).when(postDAOMock).getById(id);

        // Test method
        Post actualPost = postService.getById(id);
        assertEquals(post, actualPost);

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(postDAOMock, only()).getById(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue(), id);
    }

    /**
     * @see PostService#getByTitle(String)
     */
    @Test(expected = InvalidObjectException.class)
    public void getByTitle_titleNotValid() {
        // Test method
        postService.getByTitle(null);
    }

    /**
     * @see PostService#getByTitle(String)
     */
    @Test(expected = ServiceLayerException.class)
    public void getByTitle_DAOException() {
        String title = "Title";
        doThrow(RuntimeException.class).when(postDAOMock).getByTitle(title);

        // Test method
        postService.getByTitle(title);
    }

    /**
     * @see PostService#getByTitle(String)
     */
    @Test
    public void getByTitle_success() {
        String title = "title";
        List<Post> posts = new ArrayList<>();
        doReturn(posts).when(postDAOMock).getByTitle(anyString());

        // Test method
        List<Post> actualPostList = postService.getByTitle(title);
        assertEquals(posts, actualPostList);

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(postDAOMock, only()).getByTitle(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue(), title);
    }

    /**
     * @see PostService#getByUserId(long)
     */
    @Test
    public void getByUserId_negativeId() {
        try {
            // Test method
            postService.getByUserId(-1);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }
    }

    /**
     * @see PostService#getByUserId(long)
     */
    @Test(expected = ServiceLayerException.class)
    public void getByUserId_DAOException() {
        long id = 15;
        doThrow(RuntimeException.class).when(postDAOMock).getByUserId(id);

        // Test method
        postService.getByUserId(id);
    }

    /**
     * @see PostService#getByUserId(long)
     */
    @Test
    public void getByUserId_success() {
        Long id = 15L;
        List<Post> posts = new ArrayList<>();
        doReturn(posts).when(postDAOMock).getByUserId(anyLong());

        // Test method
        List<Post> actualPosts = postService.getByUserId(id);
        assertEquals(posts, actualPosts);

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(postDAOMock, only()).getByUserId(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue(), id);
    }

    /**
     * @see PostService#getByAppAreaId(long)
     */
    @Test
    public void getByAppAreaId_negativeId() {
        try {
            // Test method
            postService.getByAppAreaId(-1);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }
    }

    /**
     * @see PostService#getByAppAreaId(long)
     */
    @Test(expected = ServiceLayerException.class)
    public void getByAppAreaId_DAOException() {
        long id = 15;
        doThrow(RuntimeException.class).when(postDAOMock).getByAppAreaId(id);

        // Test method
        postService.getByAppAreaId(id);
    }

    /**
     * @see PostService#getByAppAreaId(long)
     */
    @Test
    public void getByAppAreaId_success() {
        Long id = 15L;
        List<Post> posts = new ArrayList<>();
        doReturn(posts).when(postDAOMock).getByAppAreaId(anyLong());

        // Test method
        List<Post> actualPosts = postService.getByAppAreaId(id);
        assertEquals(posts, actualPosts);

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(postDAOMock, only()).getByAppAreaId(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue(), id);
    }

    /**
     * @see PostService#getAnswersByPostId(long)
     */
    @Test
    public void getAnswersByPostId_negativeId() {
        try {
            // Test method
            postService.getAnswersByPostId(-1);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }
    }

    /**
     * @see PostService#getAnswersByPostId(long)
     */
    @Test(expected = ServiceLayerException.class)
    public void getAnswersByPostId_DAOException() {
        long id = 15;
        doThrow(RuntimeException.class).when(postDAOMock).getAnswersByPostId(id);

        // Test method
        postService.getAnswersByPostId(id);
    }

    /**
     * @see PostService#getAnswersByPostId(long)
     */
    @Test
    public void getAnswersByPostId_success() {
        Long id = 15L;
        List<Post> posts = new ArrayList<>();
        doReturn(posts).when(postDAOMock).getAnswersByPostId(anyLong());

        // Test method
        List<Post> actualPosts = postService.getAnswersByPostId(id);
        assertEquals(posts, actualPosts);

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(postDAOMock, only()).getAnswersByPostId(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue(), id);
    }

    /**
     * @see PostService#getBestAnswer(long)
     */
    @Test
    public void getBestAnswer_negativeId() {
        try {
            // Test method
            postService.getBestAnswer(-1);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }
    }

    /**
     * @see PostService#getBestAnswer(long)
     */
    @Test(expected = ServiceLayerException.class)
    public void getBestAnswer_DAOException() {
        long id = 15;
        doThrow(RuntimeException.class).when(postDAOMock).getBestAnswer(id);

        // Test method
        postService.getBestAnswer(id);
    }

    /**
     * @see PostService#getBestAnswer(long)
     */
    @Test
    public void getBestAnswer_success() {
        Long id = 15L;
        Post post = DaoTestUtil.getRandomPost();
        doReturn(post).when(postDAOMock).getBestAnswer(anyLong());

        // Test method
        Post actualPost = postService.getBestAnswer(id);
        assertEquals(post, actualPost);

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(postDAOMock, only()).getBestAnswer(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue(), id);
    }

    /**
     * @see PostService#getLikesNumber(long)
     */
    @Test
    public void getLikesNumber_success() {
        Long postId = 1L;
        long likesNumber = 10;
        doReturn(likesNumber).when(postDAOMock).getLikesNumber(anyLong());

        //Test method
        long actualLikesNumber = postService.getLikesNumber(postId);
        verify(postDAOMock, times(1)).getLikesNumber(postId);
        assertEquals(likesNumber, actualLikesNumber);

        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        verify(postDAOMock, only()).getLikesNumber(argument.capture());
        assertEquals(postId, argument.getValue());
    }

    /**
     * @see PostService#getLikesNumber(long)
     */
    @Test(expected = ServiceLayerException.class)
    public void getLikesNumber_DAOException() {
        doThrow(RuntimeException.class).when(postDAOMock).getLikesNumber(anyLong());

        // Test method
        postService.getLikesNumber(1);
    }

    /**
     * @see PostService#getLikesNumber(long)
     */
    @Test
    public void getLikesNumber_negativeId() {
        try {
            // Test method
            postService.getLikesNumber(-1);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }

    }

    /**
     * @see PostService#dislike(long, long)
     */
    @Test
    public void getDislikesNumber_success() {
        Long postId = 1L;
        long dislikesNumber = 10;
        doReturn(dislikesNumber).when(postDAOMock).getDislikesNumber(anyLong());

        //Test method
        long actualDislikesNumber = postService.getDislikesNumber(postId);
        verify(postDAOMock, times(1)).getDislikesNumber(postId);
        assertEquals(dislikesNumber, actualDislikesNumber);

        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        verify(postDAOMock, only()).getDislikesNumber(argument.capture());
        assertEquals(postId, argument.getValue());
    }

    /**
     * @see PostService#dislike(long, long)
     */
    @Test(expected = ServiceLayerException.class)
    public void getDislikesNumber_DAOException() {
        doThrow(RuntimeException.class).when(postDAOMock).dislike(anyLong(), anyLong());

        // Test method
        postService.dislike(1, 1);
    }

    /**
     * @see PostService#getDislikesNumber(long)
     */
    @Test
    public void getDislikesNumber_negativeId() {
        try {
            // Test method
            postService.dislike(-1, 1);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }
    }

    /**
     * @see PostServiceImpl#update(Post)
     */
    @Test
    public void update_postNotValid() {
        Post post = DaoTestUtil.getRandomPost();
        post.setUser(null);

        try {
            // Test method
            postService.update(post);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }

        post.setUser(DaoTestUtil.getRandomUser());
        post.setAppArea(null);
        try {
            // Test method
            postService.update(post);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }

        post.setAppArea(appArea);
        post.setPostTime(null);
        try {
            // Test method
            postService.update(post);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }

        post.setPostTime(new Timestamp(System.currentTimeMillis()));
        post.setTitle(null);
        try {
            // Test method
            postService.update(post);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }

        post.setTitle("my title");
        post.setContent(null);
        try {
            // Test method
            postService.update(post);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }
    }

    /**
     * @see PostService#update(Post)
     */
    @Test(expected = ServiceLayerException.class)
    public void update_DAOException() {
        Post post = DaoTestUtil.getRandomPost();
        doThrow(RuntimeException.class).when(postDAOMock).update(any(Post.class));

        // Test method
        postService.update(post);
    }

    /**
     * @see PostService#update(Post)
     */
    @Test
    public void update_success() {
        Post post = DaoTestUtil.getRandomPost();

        // Test method
        postService.update(post);
        verify(postDAOMock, times(1)).update(post);

        ArgumentCaptor<Post> argumentCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postDAOMock, only()).update(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue(), post);
    }

    /**
     * @see PostService#like(long, long)
     */
    @Test
    public void like_success() {
        Long userId = 1L, postId = 1L;
        List<Long> expected = Arrays.asList(userId, postId);
        //Test method
        postService.like(userId, postId);
        verify(postDAOMock, times(1)).like(userId, postId);

        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        verify(postDAOMock, only()).like(argument.capture(), argument.capture());
        assertEquals(expected, argument.getAllValues());
    }

    /**
     * @see PostService#like(long, long)
     */
    @Test(expected = ServiceLayerException.class)
    public void like_DAOException() {
        doThrow(RuntimeException.class).when(postDAOMock).like(anyLong(), anyLong());

        // Test method
        postService.like(1, 1);
    }

    /**
     * @see PostService#like(long, long)
     */
    @Test
    public void like_negativeId() {
        try {
            //Test method
            postService.like(-1, 5);
            fail();
        } catch (RuntimeException e) {
            assertTrue(e instanceof InvalidObjectException);
        }

        try {
            //Test method
            postService.like(5, -1);
            fail();
        } catch (RuntimeException e) {
            assertTrue(e instanceof InvalidObjectException);
        }
    }

    /**
     * @see PostService#dislike(long, long)
     */
    @Test
    public void dislike_success() {
        Long userId = 1L, postId = 1L;
        List<Long> expected = Arrays.asList(userId, postId);
        //Test method
        postService.dislike(userId, postId);
        verify(postDAOMock, times(1)).dislike(userId, postId);

        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        verify(postDAOMock, only()).dislike(argument.capture(), argument.capture());
        assertEquals(expected, argument.getAllValues());
    }

    /**
     * @see PostService#dislike(long, long)
     */
    @Test(expected = ServiceLayerException.class)
    public void dislike_DAOException() {
        doThrow(RuntimeException.class).when(postDAOMock).dislike(anyLong(), anyLong());

        // Test method
        postService.dislike(1, 1);
    }

    /**
     * @see PostService#dislike(long, long)
     */
    @Test
    public void dislike_negativeId() {
        try {
            //Test method
            postService.dislike(-1, 5);
            fail();
        } catch (RuntimeException e) {
            assertTrue(e instanceof InvalidObjectException);
        }

        try {
            //Test method
            postService.dislike(5, -1);
            fail();
        } catch (RuntimeException e) {
            assertTrue(e instanceof InvalidObjectException);
        }
    }

    /**
     * @see PostService#delete(long)
     */
    @Test
    public void delete_negativeId() {
        try {
            // Test method
            postService.delete(-1);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }
    }

    /**
     * @see PostService#delete(long)
     */
    @Test(expected = ServiceLayerException.class)
    public void delete_DAOException() {
        long id = 15;
        doThrow(RuntimeException.class).when(postDAOMock).delete(anyLong());

        // Test method
        postService.delete(id);
    }

    /**
     * @see PostService#delete(long)
     */
    @Test
    public void delete_success() {
        Long id = 15L;

        // Test method
        postService.delete(id);
        verify(postDAOMock, times(1)).delete(id);

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(postDAOMock, only()).delete(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue(), id);
    }

    /**
     * @see PostService#getNotified(long, long)
     */
    @Test
    public void getNotified_negativeId() {
        User user = DaoTestUtil.getRandomUser();
        long userId =  userService.add(user);
        try {
            //Test method
            postService.getNotified(-1, userId);
            fail();
        } catch (RuntimeException e) {
            assertTrue(e instanceof InvalidObjectException);
        }
        Post post = DaoTestUtil.getRandomPost();
        post.setUser(user);
        postService.add(post);
        try {
            //Test method
            userService.subscribeToArea(post.getId(), -1);
            fail();
        } catch (RuntimeException e) {
            assertTrue(e instanceof InvalidObjectException);
        }
    }

    /**
     * @see PostService#getNotified(long, long)
     */
    @Test(expected = ServiceLayerException.class)
    public void getNotified_DAOException() {
        doThrow(RuntimeException.class).when(postDAOMock).getNotified(anyLong(), anyLong());

        //Test method
        postService.getNotified(10, 1);
    }

    /**
     * @see PostService#getNotified(long, long)
     */
    @Test
    public void getNotified_success() {
        Long postId = 15L, userId = 17L;
        List<Long> expected = Arrays.asList(postId, userId);

        //Test method
        postService.getNotified(postId, userId);
        verify(postDAOMock, times(1)).getNotified(postId, userId);

        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        verify(postDAOMock, only()).getNotified(argument.capture(), argument.capture());
        assertEquals(expected, argument.getAllValues());
    }

    /**
     * @see PostService#getNotificationRecipients(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void getNotificationRecipients_negativeId() {
        postService.getNotificationRecipients(-1);
    }

    /**
     * @see PostService#getNotificationRecipients(long)
     */
    @Test(expected = RuntimeException.class)
    public void getNotificationRecipients_DAOException() {
        doThrow(RuntimeException.class).when(postDAOMock).getNotificationRecipients(anyLong());

        // Test method
        postService.getNotificationRecipients(15);
    }

    /**
     * @see PostService#getNotificationRecipients(long)
     */
    @Test
    public void getNotificationRecipients_success() {
        Long id = 15L;
        List<User> users = new ArrayList<>();
        doReturn(users).when(postDAOMock).getNotificationRecipients(id);

        // Test method
        List<User> actualUsers = postService.getNotificationRecipients(id);
        assertEquals(users, actualUsers);

        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        verify(postDAOMock, only()).getNotificationRecipients(argument.capture());
        assertEquals(id, argument.getValue());
    }


}
