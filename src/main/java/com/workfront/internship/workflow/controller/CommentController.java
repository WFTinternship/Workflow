package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.domain.Comment;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.service.CommentService;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.web.PageAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Angel on 6/27/2017
 */
@Controller
public class CommentController {
    private final PostService postService;
    private final CommentService commentService;
    private List<Comment> comments;

    @Autowired
    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
        comments = new ArrayList<>();
    }


    @RequestMapping(value = {"/new-comment/*"}, method = RequestMethod.POST)
    public ModelAndView newComment(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("post");

        String url = request.getRequestURL().toString();
        long postId = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(PageAttributes.USER);

        Post post = postService.getById(postId);
        request.setAttribute(PageAttributes.POST, post);

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
        List<User> users = postService.getNotificationRecipients(postId);

        try {
            postService.notifyUsers(users, post);
        } catch (RuntimeException e) {

        }
        List<Post> answers;
        if (post.getPost() == null) {

            answers = postService.getAnswersByPostId(postId);

            comments = commentService.getByPostId(postId);
            request.setAttribute(PageAttributes.POSTCOMMENTS, comments);
        } else {
            Post parentPost = post.getPost();
            request.setAttribute(PageAttributes.POST, parentPost);
            answers = postService.getAnswersByPostId(parentPost.getId());

            List<Comment> postComments = commentService.getByPostId(parentPost.getId());
            request.setAttribute(PageAttributes.POSTCOMMENTS, postComments);

            List<List<Comment>> answerComments = new ArrayList<>();

            for (Post postAnswer : answers) {
                answerComments.add(commentService.getByPostId(postAnswer.getId()));
            }
            request.setAttribute(PageAttributes.ANSWERCOMMENTS, answerComments);
        }
        request.setAttribute(PageAttributes.ANSWERS, answers);

        return modelAndView;
    }

    @RequestMapping(value = {"/edit-comment/*"}, method = RequestMethod.POST)
    public ModelAndView editComment(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("post");
        return modelAndView;
    }
}
