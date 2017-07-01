package com.workfront.internship.workflow.service;

import com.workfront.internship.workflow.dao.PostDAOIntegrationTest;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.exceptions.service.InvalidObjectException;
import com.workfront.internship.workflow.util.DaoTestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.Assert.*;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by nane on 6/21/17
 */

public class PostServiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    private Post post;

    @Before
    public void setup() {
        post = DaoTestUtil.getRandomPost();
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
     * @see PostService#like(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void like_failure() {
        //Test method
        postService.like(-1);
    }

    /**
     * @see PostService#like(long)
     */
    @Test
    public void like_success() {
        userService.add(post.getUser());
        postService.add(post);
        long likesNumber = post.getLikesNumber();

        //Test method
        postService.like(post.getId());
        long newLikesNumber = post.getLikesNumber();
        assertEquals(likesNumber, newLikesNumber);
    }

    /**
     * @see PostService#dislike(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void dislike_failure() {
        //Test method
        postService.dislike(-1);
    }

    /**
     * @see PostService#dislike(long)
     */
    @Test
    public void dislike_success() {
        userService.add(post.getUser());
        postService.add(post);
        long dislikesNumber = post.getDislikesNumber();

        //Test method
        postService.dislike(post.getId());
        long newDislikesNumber = post.getDislikesNumber();
        assertEquals(dislikesNumber, newDislikesNumber);
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

}
