package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.controller.utils.ControllerUtils;
import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.Comment;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.entity.User;
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
import java.util.Arrays;
import java.util.List;

/**
 * Created by Angel on 6/27/2017
 */
@Controller
public class AnswerController {

    private List<AppArea> appAreas;
    private PostService postService;
    private CommentService commentService;
    private Post post;
    private List<Post> answers;
    private List<Post> posts;


    public AnswerController() {
    }

    @Autowired
    public AnswerController(PostService postService, CommentService commentService) {
        this.postService = postService;
        appAreas = Arrays.asList(AppArea.values());
        post = new Post();
        this.commentService = commentService;
    }

    @RequestMapping(value = {"/new-answer/*"}, method = RequestMethod.POST)
    public ModelAndView newAnswer(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("post");

        String url = request.getRequestURL().toString();
        long postId = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));

        post = postService.getById(postId);

        String content = request.getParameter("reply");

        if (StringUtils.isEmpty(content)) {
            request.setAttribute(PageAttributes.MESSAGE, "The body is missing.");

            posts = postService.getAll();
            modelAndView
                    .addObject(PageAttributes.APPAREAS, appAreas)
                    .addObject(PageAttributes.ALLPOSTS, posts);
            return modelAndView;
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(PageAttributes.USER);

        request.setAttribute(PageAttributes.APPAREAS, appAreas);

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
            postService.notifyUsers(users, post);
        } catch (RuntimeException e) {

        }

        answers = postService.getAnswersByPostId(postId);
        request.setAttribute("answers", answers);

        List<Post> allPosts = new ArrayList<>(answers);
        allPosts.add(0, post);

        List<Comment> comments = commentService.getByPostId(postId);
        request.setAttribute(PageAttributes.POSTCOMMENTS, comments);

        List<Post> answers = postService.getAnswersByPostId(postId);
        List<List<Comment>> answerComments = new ArrayList<>();

        for (Post postAnswer : answers) {
            answerComments.add(commentService.getByPostId(postAnswer.getId()));
        }

        modelAndView
                .addObject(PageAttributes.POST, post)
                .addObject(PageAttributes.ANSWERCOMMENTS, answerComments)
                .addObject(PageAttributes.NUMOFLIKES,
                        ControllerUtils.getNumberOfLikes(allPosts, postService))
                .addObject(PageAttributes.NUMOFDISLIKES,
                        ControllerUtils.getNumberOfDislikes(allPosts, postService));

        return modelAndView;
    }


   /* @RequestMapping(value = {"/appArea*//**//*", "home"}, method = RequestMethod.GET)
    public ModelAndView editAnswer(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("home");
        return modelAndView;
    }*/
}
