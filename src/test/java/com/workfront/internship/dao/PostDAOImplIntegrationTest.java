package com.workfront.internship.dao;

import com.workfront.internship.dao.impl.PostDAOImpl;
import com.workfront.internship.dataModel.Post;
import com.workfront.internship.dataModel.User;
import com.workfront.internship.util.DaoTestUtil;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nane on 5/29/17.
 */
public class PostDAOImplIntegrationTest {
    private PostDAO postDAO;
    private Post post;

    List<Post> postList = new ArrayList<>();

    @Before
    public void setup(){
        postDAO = new PostDAOImpl();
    }

    @Test
    public void add(){
        post = DaoTestUtil.getRandomPost();
        long expectedPostId = postDAO.add(post);

        Post actualPost = postDAO.getById(expectedPostId);
        verifyAddedPost(post, actualPost);
    }

    @After
    public void tearDown(){
        for (Post user: postList) {
            postDAO.delete(user.getId());
        }
        postDAO.delete(post.getId());
    }

    private void verifyAddedPost(Post post, Post actualPost) {
        assertEquals(post.getTitle(), actualPost.getTitle());
        assertEquals(post.getContent(), actualPost.getContent());
        assertEquals(post.getPostTime(), actualPost.getPostTime());
        assertEquals(post.isCorrect(), actualPost.isCorrect());
    }
}
