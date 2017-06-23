package com.workfront.internship.workflow.service;

import com.workfront.internship.workflow.dao.springJDBC.PostDAOSpringImpl;
import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.exceptions.service.InvalidObjectException;
import com.workfront.internship.workflow.exceptions.service.ServiceLayerException;
import com.workfront.internship.workflow.service.impl.PostServiceImpl;
import com.workfront.internship.workflow.util.DaoTestUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;


/**
 * Created by nane on 6/21/17
 */

public class PostServiceImplUnitTest extends BaseUnitTest {

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private PostDAOSpringImpl postDAOMock;

    @Before
    public void init() {
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

        post.setAppArea(AppArea.AGILE);
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
        doReturn(id).when(postDAOMock).add(any(Post.class));

        // Test method
        long actualId = postService.add(post);
        assertEquals(id, actualId);
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

        // Test method
        postService.setBestAnswer(id, answerId);
        verify(postDAOMock, times(1)).setBestAnswer(id, answerId);
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
        Post post = DaoTestUtil.getRandomPost();
        doReturn(post).when(postDAOMock).getById(anyLong());

        // Test method
        Post actualPost = postService.getById(15);
        assertEquals(post, actualPost);
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
        List<Post> posts = new ArrayList<>();
        doReturn(posts).when(postDAOMock).getByTitle(anyString());

        // Test method
        List<Post> actualPostList = postService.getByTitle("title");
        assertEquals(posts, actualPostList);
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
        List<Post> posts = new ArrayList<>();
        doReturn(posts).when(postDAOMock).getByUserId(anyLong());

        // Test method
        List<Post> actualPosts = postService.getByUserId(15);
        assertEquals(posts, actualPosts);
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
        List<Post> posts = new ArrayList<>();
        doReturn(posts).when(postDAOMock).getByAppAreaId(anyLong());

        // Test method
        List<Post> actualPosts = postService.getByAppAreaId(15);
        assertEquals(posts, actualPosts);
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
        List<Post> posts = new ArrayList<>();
        doReturn(posts).when(postDAOMock).getAnswersByPostId(anyLong());

        // Test method
        List<Post> actualPosts = postService.getAnswersByPostId(15);
        assertEquals(posts, actualPosts);
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
        Post post = DaoTestUtil.getRandomPost();
        doReturn(post).when(postDAOMock).getBestAnswer(anyLong());

        // Test method
        Post actualPost = postService.getBestAnswer(15);
        assertEquals(post, actualPost);
    }

    /**
    * @see PostServiceImpl#update(Post)
    */
    @Test
    public void update_postNotValid(){
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

        post.setAppArea(AppArea.AGILE);
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
        long id = 5;

        // Test method
        postService.delete(id);
        verify(postDAOMock, times(1)).delete(id);

    }
}
