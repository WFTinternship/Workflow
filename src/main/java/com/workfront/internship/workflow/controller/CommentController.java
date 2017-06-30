package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.Comment;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.service.CommentService;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.web.PageAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Angel on 6/27/2017
 */
@Controller
public class CommentController {
    private Post post;
    private Comment comment;
    private PostService postService;
    private List<AppArea> appAreas;
    private List<Comment> comments;
    private CommentService commentService;


    public CommentController() {

    }
    public CommentController(PostService postService) {

    }

    @RequestMapping(value = {"/new-comment/*"}, method = RequestMethod.POST)
    public ModelAndView newComment(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("post");

        String url = request.getRequestURL().toString();
        long postId = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));


        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(PageAttributes.USER);

        post = postService.getById(postId);
        request.setAttribute(PageAttributes.POST, post);

        String content = request.getParameter(PageAttributes.COMMENTCONTENT) ;

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
        comments = commentService.getByPostId(postId);
        request.setAttribute("comments", comments);
        return modelAndView;
    }

    @RequestMapping(value = {"/comment/*"}, method = RequestMethod.POST)
    public ModelAndView editComment(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("post");
        return modelAndView;

    }
}
