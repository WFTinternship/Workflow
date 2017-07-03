package com.workfront.internship.workflow.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.util.DaoTestUtil;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.*;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

/**
 * Created by nane on 5/29/17
 */
public class PostDAOIntegrationTest extends BaseIntegrationTest {
    private List<User> userList;

    @Autowired
    @Qualifier("userDAOSpringImpl")
    private UserDAO userDAO;
    private User user;

    @Autowired
    @Qualifier("postDAOSpringImpl")
    private PostDAO postDAO;
    private Post post;
    private AppArea appArea;


    // region <TEST CASE>

    public static void verifyPost(Post post, Post actualPost) {
        UserDAOIntegrationTest.verifyAddedUser(post.getUser(), actualPost.getUser());
        assertEquals(post.getTitle(), actualPost.getTitle());
        assertEquals(post.getContent(), actualPost.getContent());
        assertEquals(post.getPostTime(), actualPost.getPostTime());
        assertEquals(post.isCorrect(), actualPost.isCorrect());
    }

    @Before
    public void setUp() {
        userList = new ArrayList<>();
        appArea = AppArea.values()[0];
        user = DaoTestUtil.getRandomUser();
        userList.add(user);
        userDAO.add(user);
        post = DaoTestUtil.getRandomPost(user, appArea);

        LOG = Logger.getLogger(PostDAOIntegrationTest.class);
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

        for (User user : userList) {
            if (userDAO.getById(user.getId()) != null) {
                userDAO.deleteById(user.getId());
            }
        }
        userDAO.deleteById(user.getId());
        if (dataSource instanceof ComboPooledDataSource) {
            try {
                LOG.info(((ComboPooledDataSource) dataSource).getNumBusyConnections());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @see PostDAO#add(Post)
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
     * @see PostDAO#add(Post)
     */
    @Test
    public void add_success() {
        // Test method
        long expectedPostId = postDAO.add(post);

        Post actualPost = postDAO.getById(expectedPostId);
        verifyPost(post, actualPost);
    }

    /**
     * @see PostDAO#getById(long)
     */
    @Test
    public void getById_failure() {
        Post post = postDAO.getById(1000000);
        assertEquals(post, null);

    }

    /**
     * @see PostDAO#getById(long)
     */
    @Test
    public void getById_success() {
        long expectedPostId = postDAO.add(post);
        // Test Method
        Post actualPost = postDAO.getById(expectedPostId);
        verifyPost(post, actualPost);

    }

    /**
     * @see PostDAO#getByUserId(long)
     */
    @Test
    public void getByUserId_failure() {
        List<Post> posts = postDAO.getByUserId(1000000);
        assertEquals(posts.size(), 0);
    }

    /**
     * @see PostDAO#getByUserId(long)
     */
    @Test
    public void getByUserId_success() {
        postDAO.add(post);
        //Test method
        List<Post> userPosts = postDAO.getByUserId(user.getId());
        assertTrue(userPosts.contains(post));
    }

    /**
     * @see PostDAO#getByAppAreaId(long)
     */
    @Test
    public void getByAppAreaId_failure() {
        //Test method
        List<Post> actualPosts = postDAO.getByAppAreaId(100000);
        assertEquals(actualPosts.size(), 0);
    }

    /**
     * @see PostDAO#getByAppAreaId(long)
     */
    @Test
    public void getByAppAreaId_success() {
        postDAO.add(post);
        //Test method
        List<Post> actualPosts = postDAO.getByAppAreaId(appArea.getId());
        assertTrue(actualPosts.contains(post));
    }

    /**
     * @see PostDAO#getAll()
     */
    @Test
    public void getAll_success() {
        List<Post> otherList = postDAO.getAll();
        int size = otherList.size();
        assertNotNull(otherList);

        Post otherComment = DaoTestUtil.getRandomPost(user, appArea);
        postDAO.add(otherComment);

        Post anotherComment = DaoTestUtil.getRandomPost(user, appArea);
        postDAO.add(anotherComment);

        otherList = postDAO.getAll();
        assertNotNull(otherList);
        assertTrue(otherList.size() == size + 2 && otherList.contains(otherComment) &&
                otherList.contains(anotherComment));

    }

    /**
     * @see PostDAO#getAll()
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
     * @see PostDAO#getAnswersByPostId(long)
     */
    @Test(expected = RuntimeException.class)
    public void getAnswersByPostId_failure() {
        postDAO.add(post);
        User user = DaoTestUtil.getRandomUser();
        userList.add(user);
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
     * @see PostDAO#getAnswersByPostId(long)
     */
    @Test
    public void getAnswersByPostId_success() {
        postDAO.add(post);
        User user = DaoTestUtil.getRandomUser();
        userList.add(user);
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
     * @see PostDAO#setBestAnswer(long, long)
     */
    @Test(expected = RuntimeException.class)
    public void setBestAnswer_failure() {
        postDAO.add(post);
        User anotherUser = DaoTestUtil.getRandomUser();
        userList.add(anotherUser);
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
     * @see PostDAO#setBestAnswer(long, long)
     */
    @Test
    public void setBestAnswer_success() {
        postDAO.add(post);
        User anotherUser = DaoTestUtil.getRandomUser();
        userList.add(anotherUser);
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
     * @see PostDAO#getBestAnswer(long)
     */
    @Test
    public void getBestAnswer_success() {
        postDAO.add(post);
        User user = DaoTestUtil.getRandomUser();
        userList.add(user);
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
     * @see PostDAO#getNumberOfAnswers(long)
     */
    @Test
    public void getNumberOfAnswers_success() {
        postDAO.add(post);
        Post answer = DaoTestUtil.getRandomAnswer(post);
        userDAO.add(answer.getUser());
        postDAO.add(answer);
        Integer numOfAnswers = postDAO.getNumberOfAnswers(post.getId());
        assertTrue(numOfAnswers.equals(1));
    }

    /**
     * @see PostDAO#update(Post)
     */
    @Test(expected = RuntimeException.class)
    public void update_failure() {
        postDAO.add(post);
        Post newPost = post.setContent(null);
        postDAO.update(newPost);
    }

    /**
     * @see PostDAO#update(Post)
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
     * @see PostDAO#like(long)
     */
    @Test
    public void like_success() {
        postDAO.add(post);
        long likesNumber = post.getLikesNumber();

        //Test method
        postDAO.like(post.getId());
        long newLikesNumber = post.getLikesNumber();
        assertEquals(likesNumber, newLikesNumber);
    }

    /**
     * @see PostDAO#dislike(long)
     */
    @Test
    public void dislike_success() {
        postDAO.add(post);
        long dislikesNumber = post.getLikesNumber();

        //Test method
        postDAO.dislike(post.getId());
        long newDislikesNumber = post.getLikesNumber();
        assertEquals(dislikesNumber, newDislikesNumber);
    }

    /**
     * @see PostDAO#delete(long)
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
     * @see PostDAO#delete(long)
     */
    @Test
    public void delete_success() {
        long postId = postDAO.add(post);
        postDAO.delete(postId);
        assertNull(postDAO.getById(postId));
    }
    // endregion

}
