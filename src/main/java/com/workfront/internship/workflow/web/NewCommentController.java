package com.workfront.internship.workflow.web;

import com.workfront.internship.workflow.domain.Comment;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.service.CommentService;
import com.workfront.internship.workflow.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Vahag on 6/12/2017
 */
public class NewCommentController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        CommentService commentService = BeanProvider.getCommentService();
        PostService postService = BeanProvider.getPostService();

        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("user");

        String url = req.getRequestURL().toString();
        long postId = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));

        Post post = postService.getById(postId);

        String content = req.getParameter(PageAttributes.COMMENTCONTENT);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Comment comment = new Comment();
        comment.setUser(user)
                .setPost(post)
                .setContent(content)
                .setCommentTime(timestamp);


        String jsp;
        try {
            commentService.add(comment);
            jsp = "/pages/home.jsp";
        }catch (RuntimeException e){
            jsp = "/pages/post.jsp";
        }

        List<Comment> comments = commentService.getAll();
        req.setAttribute(PageAttributes.POSTCOMMENTS, comments);

        getServletConfig().
                getServletContext().
                getRequestDispatcher(jsp).
                forward(req,resp);

    }
}

