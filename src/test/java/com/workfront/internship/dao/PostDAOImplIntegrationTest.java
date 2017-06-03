package com.workfront.internship.dao;

import com.workfront.internship.dao.impl.PostDAOImpl;
import com.workfront.internship.dao.impl.UserDAOImpl;
import com.workfront.internship.dataModel.AppArea;
import com.workfront.internship.dataModel.Post;
import com.workfront.internship.dataModel.User;
import com.workfront.internship.util.DaoTestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

/** 
 * Created by nane on 5/29/17.
 */
public class PostDAOImplIntegrationTest {
    List<Post> postList = new ArrayList<>();
    private UserDAO userDAO;
    private User user;
    private PostDAO postDAO;
    private Post post;
    private AppArea appArea;

    // region <TEST CASE>

    @Before
    public void setUp() {
        userDAO = new UserDAOImpl();
        user = DaoTestUtil.getRandomUser();
        userDAO.add(user);
        appArea = AppArea.values()[0];
        postDAO = new PostDAOImpl();
        post = DaoTestUtil.getRandomPost(user, appArea);
    }

    @After
    public void tearDown() {
        for (Post user : postList) {
            postDAO.delete(user.getId());
        }
        userDAO.deleteById(user.getId());
        postDAO.delete(post.getId());
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

    @Test
    public void add_success() {
        // Test method
        long expectedPostId = postDAO.add(post);

        Post actualPost = postDAO.getById(expectedPostId);
        verifyPost(post, actualPost);
    }

    @Test
    public void getById_failure() {
        Post post = postDAO.getById(1000000);
        assertEquals(post, null);

    }

    @Test
    public void getById_success() {
        long expectedPostId = postDAO.add(post);
        // Test Method
        Post actualPost = postDAO.getById(expectedPostId);
        verifyPost(post, actualPost);

    }

    @Test
    public void getByUserId_failure(){
        postDAO.add(post);
        List<Post> posts = postDAO.getByUserId(1000000);
        assertEquals(posts.size(),  0);
    }

    @Test
    public void getByUserId_success() {
        postDAO.add(post);
        List<Post> userPosts = postDAO.getByUserId(user.getId());
        assertEquals(userPosts.get(0).getUser().getId(), user.getId());
    }

    @Test
    public void getAll_success(){
        postDAO.add(post);
        Post anotherPost = DaoTestUtil.getRandomPost(user, appArea);
        postDAO.add(anotherPost);

        //Test Method
        List<Post> allPosts = postDAO.getAll();

        assertNotNull(allPosts);
        assertTrue(allPosts.size() == 2 && allPosts.contains(post) && allPosts.contains(anotherPost));
    }

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

    @Test(expected = RuntimeException.class)
    public void getAnswersByPostId_failure(){
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
    @Test
    public void getAnswersByPostId_success(){
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
        postDAO.delete(answer.getId());
    }

    @Test(expected = RuntimeException.class)
    public void setBestAnswer_failure(){
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

    @Test
    public void setBestAnswer_success(){
        postDAO.add(post);
        User anotherUser = DaoTestUtil.getRandomUser();
        userDAO.add(anotherUser);
        Post answer = DaoTestUtil.getRandomAnswer(post);
        answer.setUser(anotherUser);
        postDAO.add(answer);

        //Test Method
        postDAO.setBestAnswer(post.getId(), answer.getId());

        Post bestAnswer = postDAO.getBestAnswer(post.getId());
        verifyPost(bestAnswer, answer);

        userDAO.deleteById(anotherUser.getId());
        postDAO.delete(answer.getId());

    }

    @Test
    public void getBestAnswer_success(){
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
        postDAO.delete(answer.getId());
    }

    @Test
    public void update_failure(){
        postDAO.add(post);
        Post newPost = post.setContent(null);
        postDAO.update(newPost);
    }

    @Test
    public void update_success() {
        postDAO.add(post);
        post.setTitle("newTitle");
        postDAO.update(post);
        Post updatedPost = postDAO.getById(post.getId());
        verifyPost(post, updatedPost);
    }

    @Test
    public void delete_failure(){
        long postId = postDAO.add(post);
        int n = postDAO.delete(postId + 100000);
        assertEquals(n, 0);
    }

    @Test
    public void delete_success() {
        long postId = postDAO.add(post);
        postDAO.delete(postId);
        assertNull(postDAO.getById(postId));

    }


    // endregion

    // region <HELPERS>

    static void verifyPost(Post post, Post actualPost) {
        UserDAOImplIntegrationTest.verifyAddedUser(post.getUser(), actualPost.getUser());
        assertEquals(post.getTitle(), actualPost.getTitle());
        assertEquals(post.getContent(), actualPost.getContent());
        assertEquals(post.getPostTime(), actualPost.getPostTime());
        assertEquals(post.isCorrect(), actualPost.isCorrect());
    }
    // endregion

}
