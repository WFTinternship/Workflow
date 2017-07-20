package com.workfront.internship.workflow.web;

import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.Comment;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.service.CommentService;
import com.workfront.internship.workflow.service.PostService;

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
        PostService postService = BeanProvider.getPostService();
        CommentService commentService = BeanProvider.getCommentService();

        String url = req.getRequestURL().toString();
        long id = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));

        Post post = postService.getById(id);
        req.setAttribute(PageAttributes.POST, post);

        List<Comment> postComments = commentService.getByPostId(id);
        req.setAttribute(PageAttributes.POST_COMMENTS, postComments);

        List<AppArea> appAreas = Arrays.asList(AppArea.values());
        req.setAttribute(PageAttributes.APPAREAS, appAreas);

        List<Post> answers = postService.getAnswersByPostId(id);
        req.setAttribute(PageAttributes.ANSWERS, answers);

        for (Post answer: answers) {
            answer.setCommentList(commentService.getByPostId(answer.getId()));
        }

        getServletConfig()
                .getServletContext()
                .getRequestDispatcher("/pages/post.jsp")
                .forward(req, resp);
    }
}
