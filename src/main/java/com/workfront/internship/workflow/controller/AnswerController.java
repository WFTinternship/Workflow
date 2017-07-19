package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.controller.utils.EmailType;
import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.service.CommentService;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.service.UserService;
import com.workfront.internship.workflow.web.PageAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
public class AnswerController {

    private PostService postService;

    public AnswerController() {
    }

    @Autowired
    public AnswerController(PostService postService, CommentService commentService, UserService userService) {
        this.postService = postService;
    }

    @RequestMapping(value = {"/new-answer/*"}, method = RequestMethod.POST)
    public ModelAndView newAnswer(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        long postId = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));

        ModelAndView modelAndView = new ModelAndView("redirect:/post/" + postId);

        String content = request.getParameter(PageAttributes.REPLY);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(PageAttributes.USER);

        Post post = postService.getById(postId);

        AppArea appArea = post.getAppArea();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Post answer = new Post();
        answer.setUser(user)
                .setContent(content)
                .setAppArea(appArea)
                .setTitle(post.getTitle())
                .setPostTime(timestamp)
                .setPost(post);

        try {
            postService.add(answer);
        } catch (RuntimeException e) {
            request.setAttribute(PageAttributes.MESSAGE,
                    "Sorry, your answer was not added. Please try again");
        }

        List<User> users = postService.getNotificationRecipients(postId);
        try {
            postService.notifyUsers(users, post, EmailType.NEW_RESPONSE);
        } catch (RuntimeException e) {

        }
        return modelAndView;
    }
}
