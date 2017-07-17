package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.controller.utils.ControllerUtils;
import com.workfront.internship.workflow.controller.utils.EmailType;
import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.Comment;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.service.CommentService;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.web.PageAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.Enumerated;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Angel on 6/27/2017
 */
@Controller
public class CommentController {
    private final PostService postService;
    private final CommentService commentService;
    private List<Comment> comments;
    private List<AppArea> appAreas;

    @Autowired
    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
        comments = new ArrayList<>();
        appAreas = Arrays.asList(AppArea.values());
    }

    @RequestMapping(value = {"/new-comment/*"}, method = RequestMethod.POST)
    public ModelAndView newComment(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("post");

        String url = request.getRequestURL().toString();
        long postId = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(PageAttributes.USER);

        Post post = postService.getById(postId);

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
            postService.notifyUsers(users, post, EmailType.NEW_RESPONSE);
        } catch (RuntimeException e) {

        }

        List<Post> answers;
        List<Post> allPosts;
        if (post.getPost() == null) {

            answers = postService.getAnswersByPostId(postId);

            allPosts = new ArrayList<>(answers);
            allPosts.add(0, post);

            comments = commentService.getByPostId(postId);
            request.setAttribute(PageAttributes.POSTCOMMENTS, comments);
        } else {
            Post parentPost = post.getPost();
            request.setAttribute(PageAttributes.POST, parentPost);
            answers = postService.getAnswersByPostId(parentPost.getId());

            allPosts = new ArrayList<>(answers);
            allPosts.add(0, parentPost);

            List<Comment> postComments = commentService.getByPostId(parentPost.getId());
            request.setAttribute(PageAttributes.POSTCOMMENTS, postComments);

            List<List<Comment>> answerComments = new ArrayList<>();

            for (Post postAnswer : answers) {
                answerComments.add(commentService.getByPostId(postAnswer.getId()));
            }
            request.setAttribute(PageAttributes.ANSWERCOMMENTS, answerComments);
        }
        modelAndView
                .addObject(PageAttributes.ANSWERS, answers)
                .addObject(PageAttributes.POST, post)
                .addObject(PageAttributes.NUMOFLIKES,
                        ControllerUtils.getNumberOfLikes(allPosts, postService))
                .addObject(PageAttributes.NUMOFDISLIKES,
                        ControllerUtils.getNumberOfDislikes(allPosts, postService))
                .addObject(PageAttributes.APPAREAS, appAreas)
                .addObject(PageAttributes.POSTS_OF_APPAAREA,
                        ControllerUtils.getNumberOfPostsForAppArea(appAreas, postService));

        return modelAndView;
    }

    @RequestMapping(value = {"/edit-comment/*"}, method = RequestMethod.POST)
    public ModelAndView editComment(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("post");
        return modelAndView;
    }
}
