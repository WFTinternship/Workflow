package com.workfront.internship.workflow.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.entity.User;
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
import static junit.framework.TestCase.assertTrue;

/**
 * Created by nane on 5/29/17
 */
public class PostDAOIntegrationTest extends BaseIntegrationTest {
    private List<User> userList;

    @Autowired
    @Qualifier("userDAOHibernateImpl")
    private UserDAO userDAO;
    private User user;

    @Autowired
    @Qualifier("postDAOHibernateImpl")
    private PostDAO postDAO;

    private Post post;
    private AppArea appArea;


    // region <TEST CASE>

    public static void verifyPost(Post post, Post actualPost) {
        UserDAOIntegrationTest.verifyAddedUser(post.getUser(), actualPost.getUser());
        assertEquals(post.getTitle(), actualPost.getTitle());
        assertEquals(post.getContent(), actualPost.getContent());
        assertEquals(post.getPostTime(), actualPost.getPostTime());
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

        // Test method
        Integer numOfAnswers = postDAO.getNumberOfAnswers(post.getId());
        assertTrue(numOfAnswers.equals(1));
    }

    /**
     * @see PostDAO#getLikesNumber(long)
     */
    @Test
    public void getLikesNumber_failure() {
        // test method
        long likesNumber = postDAO.getLikesNumber(0);

        assertEquals(likesNumber, 0);
    }

    /**
     * @see PostDAO#getLikesNumber(long)
     */
    @Test
    public void getLikesNumber_success() {
        long postId = postDAO.add(post);
        long userId = post.getUser().getId();
        postDAO.like(userId, postId);

        // test method
        long likesNumber = postDAO.getLikesNumber(postId);

        assertEquals(likesNumber, 1);
    }

    /**
     * @see PostDAO#getDislikesNumber(long)
     */
    @Test
    public void getDislikesNumber_failure() {
        // test method
        long dislikesNumber = postDAO.getDislikesNumber(0);

        assertEquals(dislikesNumber, 0);
    }

    /**
     * @see PostDAO#getDislikesNumber(long)
     */
    @Test
    public void getDislikesNumber_success() {
        long postId = postDAO.add(post);
        long userId = post.getUser().getId();
        postDAO.dislike(userId, postId);

        // test method
        long dislikesNumber = postDAO.getDislikesNumber(postId);

        assertEquals(dislikesNumber, 1);
    }

    /**
     * @see PostDAO#update(Post)
     */
    @Test(expected = RuntimeException.class)
    public void update_failure() {
        postDAO.add(post);
        Post newPost = post.setContent(null);

        // Test method
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
     * @see PostDAO#like(long, long)
     */
    @Test
    public void like_success() {
        long postId = postDAO.add(post);
        long userId = post.getUser().getId();
        long likesNumber = postDAO.getLikesNumber(postId);

        //Test method
        postDAO.like(userId, postId);

        long newLikesNumber = postDAO.getLikesNumber(postId);
        assertEquals(likesNumber, newLikesNumber - 1);
    }

    /**
     * @see PostDAO#like(long, long)
     */
    @Test(expected = RuntimeException.class)
    public void like_failure() {
        long postId = postDAO.add(post);
        long userId = post.getUser().getId();

        //Test method
        postDAO.like(userId, postId);

        postDAO.like(userId, postId);
    }

    /**
     * @see PostDAO#dislike(long, long)
     */
    @Test
    public void dislike_success() {
        long postId = postDAO.add(post);
        long userId = post.getUser().getId();
        long dislikesNumber = postDAO.getDislikesNumber(postId);

        //Test method
        postDAO.dislike(userId, postId);

        long newDislikesNumber = postDAO.getDislikesNumber(postId);
        assertEquals(dislikesNumber, newDislikesNumber - 1);
    }

    /**
     * @see PostDAO#like(long, long)
     */
    @Test(expected = RuntimeException.class)
    public void dislike_failure() {
        long postId = postDAO.add(post);
        long userId = post.getUser().getId();

        //Test method
        postDAO.dislike(userId, postId);

        postDAO.dislike(userId, postId);
    }

    /**
     * @see PostDAO#delete(long)
     */
    @Test
    public void delete_failure() {
        long postId = postDAO.add(post);

        // Test Method
        postDAO.delete(postId + 100000);
        assertEquals(post, postDAO.getById(postId));
    }

    /**
     * @see PostDAO#delete(long)
     */
    @Test
    public void delete_success() {
        long postId = postDAO.add(post);
        postDAO.delete(postId);
        assertNull(postDAO.getById(postId));
    }

    /**
     * @see PostDAO#getNotified(long, long)
     */
    @Test(expected = RuntimeException.class)
    public void getNotified_failure(){
        postDAO.add(post);
        //Test method
        postDAO.getNotified(post.getId(), -1);

    }

    /**
     * @see PostDAO#getNotified(long, long)
     */
    @Test
    public void getNotified_success() {
        postDAO.add(post);
        //Test method
        postDAO.getNotified(post.getId(), post.getUser().getId());
        List<User> users = postDAO.getNotificationRecipients(post.getId());

        assertTrue(users.contains(user));
    }

    /**
     * @see PostDAO#getNotificationRecipients(long)
     */
    @Test
    public void getNotificationRecipients_success() {
        postDAO.add(post);
        postDAO.getNotified(post.getId(), user.getId());

        //Test method
        List<User> userList = postDAO.getNotificationRecipients(post.getId());
        assertTrue(userList.contains(user));
    }
    // endregion

}
