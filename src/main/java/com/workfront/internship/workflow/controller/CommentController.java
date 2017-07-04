package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.Comment;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.service.CommentService;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.web.PageAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Angel on 6/27/2017
 */
@Controller
public class CommentController {
    private List<Comment> allComments;
    private final PostService postService;
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
        allComments = new ArrayList<>();
        allComments = new ArrayList<>();
    }

    @RequestMapping(value = {"/new-comment/*"}, method = RequestMethod.POST)
    public ModelAndView newComment(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("post");

        String url = request.getRequestURL().toString();
        long postId = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));

        Post post = postService.getById(postId);
        request.setAttribute(PageAttributes.POST, post);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(PageAttributes.USER);

        String content = request.getParameter(PageAttributes.COMMENTCONTENT);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Comment newComment = new Comment();
        newComment.setUser(user)
                .setPost(post)
                .setContent(content)
                .setCommentTime(timestamp);

        try {
            commentService.add(newComment);
        } catch (RuntimeException e) {
            request.setAttribute(PageAttributes.MESSAGE,
                    "Sorry, your comment was not added. Please try again");
        }
        allComments = commentService.getByPostId(postId);
        request.setAttribute("comments", allComments);
        return modelAndView;
    }

    @RequestMapping(value = {"/edit-comment/*"}, method = RequestMethod.POST)
    public ModelAndView editComment(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("post");
        return modelAndView;
    }
}
