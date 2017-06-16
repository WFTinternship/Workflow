package com.workfront.internship.workflow.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.workfront.internship.workflow.dao.impl.PostDAOImpl;
import com.workfront.internship.workflow.dao.impl.UserDAOImpl;
import com.workfront.internship.workflow.dao.springJDBC.PostDAOSpringImpl;
import com.workfront.internship.workflow.dao.springJDBC.UserDAOSpringImpl;
import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.util.DaoTestUtil;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.*;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

/**
 * Created by nane on 5/29/17
 */
public class PostDAOImplIntegrationTest extends BaseIntegrationTest {
    private List<Post> postList = new ArrayList<>();
    private UserDAO userDAO;
    private User user;
    private PostDAO postDAO;
    private Post post;
    private AppArea appArea;


    // region <TEST CASE>

    public static void verifyPost(Post post, Post actualPost) {
        UserDAOImplIntegrationTest.verifyAddedUser(post.getUser(), actualPost.getUser());
        assertEquals(post.getTitle(), actualPost.getTitle());
        assertEquals(post.getContent(), actualPost.getContent());
        assertEquals(post.getPostTime(), actualPost.getPostTime());
        assertEquals(post.isCorrect(), actualPost.isCorrect());
    }

    @Before
    public void setUp() {
        userDAO = new UserDAOSpringImpl(dataSource);
        postDAO = new PostDAOSpringImpl(dataSource);

        appArea = AppArea.values()[0];
        user = DaoTestUtil.getRandomUser();
        userDAO.add(user);
        post = DaoTestUtil.getRandomPost(user, appArea);

        LOG = Logger.getLogger(PostDAOImplIntegrationTest.class);
        if (dataSource instanceof ComboPooledDataSource) {
            try {
                LOG.info(((ComboPooledDataSource) dataSource).getNumBusyConnections());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @After
    public void tearDown() {
        try {
            for (Post user : postList) {
                postDAO.delete(user.getId());
            }
            userDAO.deleteById(user.getId());
            postDAO.delete(post.getId());
        } catch (RuntimeException e) {

        }
        if (dataSource instanceof ComboPooledDataSource) {
            try {
                LOG.info(((ComboPooledDataSource) dataSource).getNumBusyConnections());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @see PostDAOImpl#add(Post)
     */
    @Test(expected = RuntimeException.class)
    public void add_failure() {
        post.setTitle(null);

        // Test method
        long postId = postDAO.add(post);

        Post post = postDAO.getById(postId);
        assertNull(post);

    }

    /**
     * @see PostDAOImpl#add(Post)
     */
    @Test
    public void add_success() {
        // Test method
        long expectedPostId = postDAO.add(post);

        Post actualPost = postDAO.getById(expectedPostId);
        verifyPost(post, actualPost);
    }

    /**
     * @see PostDAOImpl#getById(long)
     */
    @Test
    public void getById_failure() {
        Post post = postDAO.getById(1000000);
        assertEquals(post, null);

    }

    /**
     * @see PostDAOImpl#getById(long)
     */
    @Test
    public void getById_success() {
        long expectedPostId = postDAO.add(post);
        // Test Method
        Post actualPost = postDAO.getById(expectedPostId);
        verifyPost(post, actualPost);

    }

    /**
     * @see PostDAOImpl#getByUserId(long)
     */
    @Test
    public void getByUserId_failure() {
        List<Post> posts = postDAO.getByUserId(1000000);
        assertEquals(posts.size(), 0);
    }

    /**
     * @see PostDAOImpl#getByUserId(long)
     */
    @Test
    public void getByUserId_success() {
        postDAO.add(post);
        //Test method
        List<Post> userPosts = postDAO.getByUserId(user.getId());
        assertTrue(userPosts.contains(post));
    }

    /**
     * @see PostDAOImpl#getByAppAreaId(long)
     */
    @Test
    public void getByAppAreaId_failure(){
        //Test method
        List<Post> actualPosts = postDAO.getByAppAreaId(100000);
        assertEquals(actualPosts.size(), 0);
    }

    /**
     * @see PostDAOImpl#getByAppAreaId(long)
     */
    @Test
    public void getByAppAreaId_success(){
        postDAO.add(post);
        //Test method
        List<Post> actualPosts = postDAO.getByAppAreaId(appArea.getId());
        assertTrue(actualPosts.contains(post));
    }

    /**
     * @see PostDAOImpl#getAll()
     */
    @Test
    public void getAll_success() {
        List<Post> otherList = postDAO.getAll();
        int size = otherList.size();
        assertNotNull(otherList);

        Post otherComment = DaoTestUtil.getRandomPost(user, appArea);
        postDAO.add(otherComment);

        postList.add(otherComment);

        Post anotherComment = DaoTestUtil.getRandomPost(user, appArea);
        postDAO.add(anotherComment);

        postList.add(anotherComment);

        otherList = postDAO.getAll();
        assertNotNull(otherList);
        assertTrue(otherList.size() == size + 2 && otherList.contains(otherComment) &&
                otherList.contains(anotherComment));

    }

    /**
     * @see PostDAOImpl#getAll()
     */
    @Test
    public void getByTitle_success() {
        post.setTitle("Title 1");
        postDAO.add(post);
        Post anotherPost = DaoTestUtil.getRandomPost(user, appArea);
        anotherPost.setTitle("Title 2");
        postDAO.add(anotherPost);
        List<Post> posts = postDAO.getByTitle(post.getTitle().substring(1, 5));
        assertTrue(posts.contains(post) && posts.contains(anotherPost));
    }

    /**
     * @see PostDAOImpl#getAnswersByPostId(long)
     */
    @Test(expected = RuntimeException.class)
    public void getAnswersByPostId_failure() {
        postDAO.add(post);
        User user = DaoTestUtil.getRandomUser();
        userDAO.add(user);
        Post answer = DaoTestUtil.getRandomAnswer(post);
        answer.setUser(user);
        answer.setContent(null);
        postDAO.add(answer);

        // Test Method
        List<Post> answers = postDAO.getAnswersByPostId(post.getId());
        assertEquals(answers.get(0), answer);

        userDAO.deleteById(user.getId());
        postDAO.delete(answer.getId());
    }

    /**
     * @see PostDAOImpl#getAnswersByPostId(long)
     */
    @Test
    public void getAnswersByPostId_success() {
        postDAO.add(post);
        User user = DaoTestUtil.getRandomUser();
        userDAO.add(user);
        Post answer = DaoTestUtil.getRandomAnswer(post);
        answer.setUser(user);
        postDAO.add(answer);

        // Test Method
        List<Post> answers = postDAO.getAnswersByPostId(post.getId());
        assertEquals(answers.get(0), answer);

        userDAO.deleteById(user.getId());
    }

    /**
     * @see PostDAOImpl#setBestAnswer(long, long)
     */
    @Test(expected = RuntimeException.class)
    public void setBestAnswer_failure() {
        postDAO.add(post);
        User anotherUser = DaoTestUtil.getRandomUser();
        userDAO.add(anotherUser);
        Post answer = DaoTestUtil.getRandomAnswer(post);
        // not added to db
        Post anotherPost = DaoTestUtil.getRandomPost();
        answer.setPost(anotherPost);

        answer.setUser(anotherUser);
        postDAO.add(answer);

        // Test Method
        postDAO.setBestAnswer(answer.getPost().getId(), answer.getId());

        userDAO.deleteById(anotherUser.getId());
        postDAO.delete(answer.getId());
    }

    /**
     * @see PostDAOImpl#setBestAnswer(long, long)
     */
    @Test
    public void setBestAnswer_success() {
        postDAO.add(post);
        User anotherUser = DaoTestUtil.getRandomUser();
        userDAO.add(anotherUser);
        Post answer = DaoTestUtil.getRandomAnswer(post);
        answer.setUser(anotherUser);
        long id = postDAO.add(answer);
        answer.setId(id);

        //Test Method
        postDAO.setBestAnswer(post.getId(), answer.getId());

        Post bestAnswer = postDAO.getBestAnswer(post.getId());
        verifyPost(bestAnswer, answer);

        userDAO.deleteById(anotherUser.getId());

    }

    /**
     * @see PostDAOImpl#getBestAnswer(long)
     */
    @Test
    public void getBestAnswer_success() {
        postDAO.add(post);
        User user = DaoTestUtil.getRandomUser();
        userDAO.add(user);
        Post answer = DaoTestUtil.getRandomAnswer(post);
        answer.setUser(user);
        postDAO.add(answer);
        postDAO.setBestAnswer(post.getId(), answer.getId());

        // Test Method
        Post bestAnswer = postDAO.getBestAnswer(post.getId());

        assertEquals(bestAnswer, answer);

        userDAO.deleteById(user.getId());
    }

    /**
     * @see PostDAOImpl#update(Post)
     */
    @Test(expected = RuntimeException.class)
    public void update_failure() {
        postDAO.add(post);
        Post newPost = post.setContent(null);
        postDAO.update(newPost);
    }

    /**
     * @see PostDAOImpl#update(Post)
     */
    @Test
    public void update_success() {
        postDAO.add(post);
        post.setTitle("newTitle");
        postDAO.update(post);
        Post updatedPost = postDAO.getById(post.getId());
        verifyPost(post, updatedPost);
    }

    /**
     * @see PostDAOImpl#delete(long)
     */
    @Test
    public void delete_failure() {
        long postId = postDAO.add(post);
        postDAO.delete(postId + 100000);
        assertNotSame(post, postDAO.getById(postId));
    }


    // endregion

    // region <HELPERS>

    /**
     * @see PostDAOImpl#delete(long)
     */
    @Test
    public void delete_success() {
        long postId = postDAO.add(post);
        postDAO.delete(postId);
        assertNull(postDAO.getById(postId));
    }
    // endregion

}
