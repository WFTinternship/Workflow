package com.workfront.internship.workflow.service.integration;

import com.workfront.internship.workflow.dao.PostDAOIntegrationTest;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.exceptions.service.InvalidObjectException;
import com.workfront.internship.workflow.exceptions.service.ServiceLayerException;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.service.UserService;
import com.workfront.internship.workflow.util.DaoTestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.Assert.*;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by nane on 6/21/17
 */

public class PostServiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    private Post post;
    private User user;

    @Before
    public void setup() {
        post = DaoTestUtil.getRandomPost();
        user = DaoTestUtil.getRandomUser();
    }

    @After
    public void tearDown() {
        if (post.getId() > 0 && postService.getById(post.getId()) != null) {
            postService.delete(post.getId());
        }
    }

    /**
     * @see PostService#add(Post)
     */
    @Test(expected = InvalidObjectException.class)
    public void add_failure() {
        postService.add(null);
    }

    /**
     * @see PostService#add(Post)
     */
    @Test
    public void add_success() {
        userService.add(post.getUser());

        //Test method
        postService.add(post);
        Post actualPost = postService.getById(post.getId());
        PostDAOIntegrationTest.verifyPost(post, actualPost);
        userService.deleteById(post.getUser().getId());
    }

    /**
     * @see PostService#setBestAnswer(long, long)
     */
    @Test(expected = InvalidObjectException.class)
    public void setBestAnswer_failure() {
        postService.setBestAnswer(-1, 18);
    }

    /**
     * @see PostService#setBestAnswer(long, long)
     */
    @Test
    public void setBestAnswer_success() {
        userService.add(post.getUser());
        postService.add(post);
        Post answer = DaoTestUtil.getRandomAnswer(post);
        User user = answer.getUser();
        userService.add(user);
        postService.add(answer);

        // Test method
        postService.setBestAnswer(post.getId(), answer.getId());

        Post bestAnswer = postService.getBestAnswer(post.getId());
        PostDAOIntegrationTest.verifyPost(bestAnswer, answer);

        userService.deleteById(post.getUser().getId());
        userService.deleteById(user.getId());
    }

    /**
     * @see PostService#removeBestAnswer(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void removeBestAnswer_failure() {
        postService.removeBestAnswer(-1);
    }

    /**
     * @see PostService#removeBestAnswer(long)
     */
    @Test
    public void removeBestAnswer_success() {
        userService.add(post.getUser());
        postService.add(post);
        Post answer = DaoTestUtil.getRandomAnswer(post);
        User user = answer.getUser();
        userService.add(user);
        postService.add(answer);

        postService.setBestAnswer(post.getId(), answer.getId());


        // Test method
        postService.removeBestAnswer(answer.getId());

        Post removedBestAnswer = postService.getBestAnswer(post.getId());
        assertNull(removedBestAnswer);

        userService.deleteById(post.getUser().getId());
        userService.deleteById(user.getId());
    }

    /**
     * @see PostService#getAll()
     */
    @Test
    public void getAll_success() {
        List<Post> otherList = postService.getAll();
        assertNotNull(otherList);
        int size = otherList.size();

        Post otherComment = DaoTestUtil.getRandomPost();
        userService.add(otherComment.getUser());
        postService.add(otherComment);

        Post anotherComment = DaoTestUtil.getRandomPost();
        userService.add(anotherComment.getUser());
        postService.add(anotherComment);

        otherList = postService.getAll();
        assertNotNull(otherList);
        assertTrue(otherList.size() == size + 2 && otherList.contains(otherComment) &&
                otherList.contains(anotherComment));

        userService.deleteById(otherComment.getUser().getId());
        userService.deleteById(anotherComment.getUser().getId());
    }

    /**
     * @see PostService#getById(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void getById_failure() {
        postService.getById(-1);
    }

    /**
     * @see PostService#getById(long)
     */
    @Test
    public void getById_success() {
        userService.add(post.getUser());
        postService.add(post);

        // Test Method
        Post expectedPost = postService.getById(post.getId());
        PostDAOIntegrationTest.verifyPost(post, expectedPost);
        userService.deleteById(post.getId());
    }

    /**
     * @see PostService#getByTitle(String)
     */
    @Test(expected = InvalidObjectException.class)
    public void getByTitle_failure() {
        postService.getByTitle(null);
    }

    /**
     * @see PostService#getByTitle(String)
     */
    @Test
    public void getByTitle_success() {
        String title = "Title 1";
        userService.add(post.getUser());
        post.setTitle(title);
        postService.add(post);

        // Test Method
        List<Post> posts = postService.getByTitle(title.substring(1, 4));
        assertTrue(posts.contains(post));
        userService.deleteById(post.getId());
    }

    /**
     * @see PostService#getByUserId(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void getByUserId_failure() {
        postService.getByUserId(-1);
    }

    /**
     * @see PostService#getByUserId(long)
     */
    @Test
    public void getByUserId_success() {
        userService.add(post.getUser());
        postService.add(post);

        // Test Method
        List<Post> posts = postService.getByUserId(post.getUser().getId());
        assertTrue(posts.contains(post));
        userService.deleteById(post.getId());
    }

    /**
     * @see PostService#getByAppAreaId(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void getByAppAreaId_failure() {
        postService.getByAppAreaId(-1);
    }

    /**
     * @see PostService#getByAppAreaId(long)
     */
    @Test
    public void getByAppAreaId_success() {
        userService.add(post.getUser());
        postService.add(post);

        // Test Method
        List<Post> posts = postService.getByAppAreaId(post.getAppArea().getId());
        assertTrue(posts.contains(post));

        userService.deleteById(post.getId());
    }

    /**
     * @see PostService#getAnswersByPostId(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void getAnswersByPostId_failure() {
        postService.getAnswersByPostId(-1);
    }

    /**
     * @see PostService#getAnswersByPostId(long)
     */
    @Test
    public void getAnswersByPostId_success() {
        userService.add(post.getUser());
        postService.add(post);

        Post answer = DaoTestUtil.getRandomAnswer(post);
        userService.add(answer.getUser());
        postService.add(answer);


        // Test Method
        List<Post> answers = postService.getAnswersByPostId(post.getId());
        assertTrue(answers.contains(answer));

        userService.deleteById(post.getId());
        userService.deleteById(answer.getUser().getId());
    }

    /**
     * @see PostService#getAnswersByUserId(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void getAnswersByUserId_failure() {
        postService.getAnswersByPostId(-1);
    }

    /**
     * @see PostService#getAnswersByUserId(long)
     */
    @Test
    public void getAnswersByUserId_success() {
        userService.add(post.getUser());
        postService.add(post);

        Post answer = DaoTestUtil.getRandomAnswer(post);
        userService.add(answer.getUser());
        answer.setUser(post.getUser());
        postService.add(answer);


        // Test Method
        List<Post> answers = postService.getAnswersByUserId(post.getUser().getId());
        assertTrue(answers.contains(answer));

        userService.deleteById(post.getId());
        userService.deleteById(answer.getUser().getId());
    }

    /**
     * @see PostService#getLikesNumber(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void getLikesNumber_failure() {
        // test method
        postService.getLikesNumber(0);
    }

    /**
     * @see PostService#getLikesNumber(long)
     */
    @Test
    public void getLikesNumber_success() {
        long userId = userService.add(post.getUser());
        long postId = postService.add(post);
        postService.like(userId, postId);

        // test method
        long likesNumber = postService.getLikesNumber(postId);

        assertEquals(likesNumber, 1);
    }

    /**
     * @see PostService#getDislikesNumber(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void getDislikesNumber_failure() {
        // test method
       postService.getDislikesNumber(0);
    }

    /**
     * @see PostService#getDislikesNumber(long)
     */
    @Test
    public void getDislikesNumber_success() {
        long userId = userService.add(post.getUser());
        long postId = postService.add(post);
        postService.dislike(userId, postId);

        // test method
        long dislikesNumber = postService.getDislikesNumber(postId);

        assertEquals(dislikesNumber, 1);
    }

    /**
     * @see PostService#update(Post)
     */
    @Test(expected = InvalidObjectException.class)
    public void update_failure() {
        postService.update(null);
    }


    /**
     * @see PostService#update(Post)
     */
    @Test
    public void update_success() {
        userService.add(post.getUser());
        postService.add(post);

        post.setTitle("Updated Title");
        // Test Method
        postService.update(post);
        Post expectedPost = postService.getById(post.getId());

        assertEquals(post.getTitle(), expectedPost.getTitle());

        userService.deleteById(post.getId());
    }

    /**
     * @see PostService#like(long, long)
     */
    @Test(expected = ServiceLayerException.class)
    public void like_failure() {
        long postId = postService.add(post);
        long userId = post.getUser().getId();

        //Test method
        postService.like(userId, postId);

        postService.like(userId, postId);
    }

    /**
     * @see PostService#like(long, long)
     */
    @Test
    public void like_success() {
        long userId = userService.add(post.getUser());
        long postId = postService.add(post);
        long likesNumber = postService.getLikesNumber(postId);

        //Test method
        postService.like(userId, postId);

        long newLikesNumber = postService.getLikesNumber(postId);
        assertEquals(likesNumber, newLikesNumber - 1);
    }

    /**
     * @see PostService#dislike(long, long)
     */
    @Test(expected = ServiceLayerException.class)
    public void dislike_failure() {
        long postId = postService.add(post);
        long userId = post.getUser().getId();

        //Test method
        postService.dislike(userId, postId);

        postService.dislike(userId, postId);
    }

    /**
     * @see PostService#dislike(long, long)
     */
    @Test
    public void dislike_success() {
        long userId = userService.add(post.getUser());
        long postId = postService.add(post);
        long dislikesNumber = postService.getDislikesNumber(postId);

        //Test method
        postService.dislike(userId, postId);

        long newDislikesNumber = postService.getDislikesNumber(postId);
        assertEquals(dislikesNumber, newDislikesNumber - 1);
    }

    /**
     * @see PostService#delete(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void delete_failure() {
        postService.delete(-1);
    }

    /**
     * @see PostService#delete(long)
     */
    @Test
    public void delete_success() {
        userService.add(post.getUser());
        postService.add(post);

        // Test Method
        postService.delete(post.getId());
        Post expectedPost = postService.getById(post.getId());

        assertNull(expectedPost);

        userService.deleteById(post.getId());
    }

    /**
     * @see PostService#getNotified(long, long)
     */
    @Test(expected = InvalidObjectException.class)
    public void getNotified_failure(){
        //Test method
        postService.getNotified(0, 1);
    }

    /**
     * @see PostService#getNotified(long, long)
     */
    @Test
    public void getNotified_success(){
        userService.add(post.getUser());
        postService.add(post);

        //Test method
        postService.getNotified(post.getId(), post.getUser().getId());
        List<User> actualUsers = postService.getNotificationRecipients(post.getId());
        assertTrue(actualUsers.contains(post.getUser()));
    }

    /**
     * @see PostService#getNotificationRecipients(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void getNotificationRecipients_failure() {
        //Test method
        postService.getNotificationRecipients(0);
    }

    /**
     * @see PostService#getNotificationRecipients(long)
     */
    @Test
    public void getNotificationRecipients_success() {
        User user = DaoTestUtil.getRandomUser();
        userService.add(user);
        userService.add(post.getUser());
        postService.add(post);
        postService.getNotified(post.getId(), user.getId());

        //Test method
        List<User> actualUsers = postService.getNotificationRecipients(post.getId());

        assertTrue(actualUsers.contains(user));
    }

}
