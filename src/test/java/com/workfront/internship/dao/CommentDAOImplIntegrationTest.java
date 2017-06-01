package com.workfront.internship.dao;

import com.workfront.internship.dao.impl.AppAreaDAOImpl;
import com.workfront.internship.dao.impl.CommentDAOImpl;
import com.workfront.internship.dao.impl.PostDAOImpl;
import com.workfront.internship.dao.impl.UserDAOImpl;
import com.workfront.internship.dataModel.AppArea;
import com.workfront.internship.dataModel.Comment;
import com.workfront.internship.dataModel.Post;
import com.workfront.internship.dataModel.User;
import com.workfront.internship.util.DaoTestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;


/**
 * Created by angel on 30.05.2017.
 */
public class CommentDAOImplIntegrationTest {
    //private CommentDAO commentDAO;
    private Comment comment;
    private CommentDAO commentDAO;

    private User user;
    private UserDAO userDAO;

    private Post post;
    private PostDAO postDAO;

    private AppArea appArea;
    private AppAreaDAO appAreaDAO;

    List<Comment> commentList = new ArrayList<>();

    @Before
    public void setup(){
       // comment = new Comment();
        userDAO = new UserDAOImpl();
        user=  DaoTestUtil.getRandomUser();
        userDAO.add(user);

        appAreaDAO = new AppAreaDAOImpl();
        appArea = DaoTestUtil.getRandomAppArea();
        appAreaDAO.add(appArea);

        postDAO = new PostDAOImpl();
        post=  DaoTestUtil.getRandomPost(user,appArea);
        postDAO.add(post);

        commentDAO = new CommentDAOImpl();
        comment=  DaoTestUtil.getRandomComment(user,post);
        commentDAO.add(comment);

    }

    @After
    public void tearDown(){

    }

    @Test
    public void add_success(){
        long expectedCommentId = commentDAO.add(comment);
        Comment actualComment = commentDAO.getById(expectedCommentId);
        verifyAddedComment(comment, actualComment);
    }
   /* @Test
    public void add_failure(){
        long commentId = commentDAO.add(comment);
        Comment comm = commentDAO.getById(commentId);
        assertNull(comm);
    }*/
    private void verifyAddedComment(Comment comment, Comment actualComment){

        //PostDAOImplIntegrationTest.verifyAddedPost(comment.getPost(),actualComment.getPost());
        //UserDAOImplIntegrationTest.verifyAddedUser(comment.getUser(),actualComment.getUser());
       // assertEquals(comment.getContent(),actualComment.getContent());
        assertEquals(comment.getCommentTime(),actualComment.getCommentTime());
    }

}



