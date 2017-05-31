package com.workfront.internship.dao;

import com.workfront.internship.dao.impl.CommentDAOImpl;
import com.workfront.internship.dao.impl.UserDAOImpl;
import com.workfront.internship.dataModel.Comment;
import com.workfront.internship.dataModel.User;
import com.workfront.internship.util.DaoTestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by angel on 30.05.2017.
 */
public class CommentDAOImplIntegrationTest {
    private CommentDAO commentDAO;
    private Comment comment;

    List<Comment> commentList = new ArrayList<>();

    @Before
    public void setup(){
        commentDAO = new CommentDAOImpl();
    }

    @After
    public void tearDown(){

    }

    @Test
    public void add_success(){

    }
    @Test
    public void add_failure(){

    }
    private void verifyAddedComment(Comment comment, Comment actualComment){

    }

}



