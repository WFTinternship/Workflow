package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.controller.utils.EmailType;
import com.workfront.internship.workflow.entity.Comment;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.exceptions.service.InvalidObjectException;
import com.workfront.internship.workflow.exceptions.service.ServiceLayerException;
import com.workfront.internship.workflow.service.CommentService;
import com.workfront.internship.workflow.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Angel on 6/27/2017
 */
@Controller
public class CommentController extends BaseController {
    private final PostService postService;
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    @RequestMapping(value = {"/new-comment/*"}, method = RequestMethod.POST)
    public ModelAndView newComment(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        long postId = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(PageAttributes.USER);

        Post post = postService.getById(postId);
        ModelAndView modelAndView = new ModelAndView();

        if (post.getPost() == null) {
            modelAndView.setViewName("redirect:/post/" + postId);
        } else {
            modelAndView.setViewName("redirect:/post/" + post.getPost().getId());
        }

        String content = request.getParameter(PageAttributes.COMMENT_CONTENT);
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

        List<User> users = postService.getNotificationRecipients(postId);

        postService.notifyUsers(users, post, EmailType.NEW_RESPONSE);

        return modelAndView;
    }

    @RequestMapping(value = {"/edit-comment/*"}, method = RequestMethod.POST)
    public ModelAndView editComment(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("post");
        return modelAndView;
    }

}
