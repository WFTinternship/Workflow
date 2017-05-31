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
import static junit.framework.TestCase.*;



import java.util.ArrayList;
import java.util.List;

/**
 * Created by nane on 5/29/17.
 */
public class PostDAOImplIntegrationTest {
    private UserDAO userDAO;
    private User user;

    private PostDAO postDAO;
    private Post post;

    private AppArea appArea;

    List<Post> postList = new ArrayList<>();

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
    public void tearDown(){
        for (Post user: postList) {
            postDAO.delete(user.getId());
        }
        userDAO.deleteById(user.getId());
        postDAO.delete(post.getId());
    }

    // region <TEST CASE>

    /**
     * @see PostDAO#add(Post)
     */
    @Test
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
        verifyAddedPost(post, actualPost);
    }

    @Test
    public void getById_failure(){
        Post post = postDAO.getById(1000000);
        assertEquals(post, null);

    }

    @Test
    public void getById_success(){
        long expectedPostId = postDAO.add(post);

        Post actualPost = postDAO.getById(expectedPostId);
        verifyAddedPost(post, actualPost);

    }

    // endregion

    // region <HELPERS>

    static void verifyAddedPost(Post post, Post actualPost) {
        UserDAOImplIntegrationTest.verifyAddedUser(post.getUser(), actualPost.getUser());
        assertEquals(post.getTitle(), actualPost.getTitle());
        assertEquals(post.getContent(), actualPost.getContent());
        assertEquals(post.getPostTime(), actualPost.getPostTime());
        assertEquals(post.isCorrect(), actualPost.isCorrect());
    }

    // endregion

}
