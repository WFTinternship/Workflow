package com.workfront.internship.workflow.web;

import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.Comment;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.service.CommentService;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.service.impl.CommentServiceImpl;
import com.workfront.internship.workflow.service.impl.PostServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nane on 6/7/17
 */
public class PostController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostService postService = new PostServiceImpl();
        CommentService commentService = new CommentServiceImpl();

        String url = req.getRequestURL().toString();
        long id = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));

        Post post = postService.getById(id);
        req.setAttribute(PageAttributes.post, post);

        List<Comment> postComments = commentService.getByPostId(id);
        req.setAttribute(PageAttributes.postComments, postComments);

        List<AppArea> appAreas = Arrays.asList(AppArea.values());
        req.setAttribute(PageAttributes.appAreas, appAreas);

        List<Post> answers = postService.getAnswersByPostId(id);
        req.setAttribute(PageAttributes.answers, answers);

        for (Post answer: answers) {
            answer.setCommentList(commentService.getByPostId(answer.getId()));
        }

        getServletConfig()
                .getServletContext()
                .getRequestDispatcher("/pages/post.jsp")
                .forward(req, resp);
    }
}
