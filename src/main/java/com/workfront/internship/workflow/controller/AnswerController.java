package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.controller.utils.ControllerUtils;
import com.workfront.internship.workflow.controller.utils.EmailType;
import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.Comment;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.service.CommentService;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.service.UserService;
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
    private UserService userService;
    private Post post;
    private List<Post> answers;
    private List<Post> posts;


    public AnswerController() {
    }

    @Autowired
    public AnswerController(PostService postService, CommentService commentService, UserService userService) {
        this.postService = postService;
        appAreas = Arrays.asList(AppArea.values());
        post = new Post();
        this.commentService = commentService;
        this.userService = userService;
    }

    @RequestMapping(value = {"/new-answer/*"}, method = RequestMethod.POST)
    public ModelAndView newAnswer(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("post");

        String url = request.getRequestURL().toString();
        long postId = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));

        String content = request.getParameter("reply");
        posts = postService.getAll();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(PageAttributes.USER);

        post = postService.getById(postId);

        if (StringUtils.isEmpty(content)) {
            request.setAttribute(PageAttributes.MESSAGE, "The body is missing.");
            return modelAndView;
        }

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

        List<Post> answers = postService.getAnswersByPostId(postId);
        List<List<Comment>> answerComments = new ArrayList<>();

        for (Post postAnswer : answers) {
            answerComments.add(commentService.getByPostId(postAnswer.getId()));
        }

        List<Post> likedPosts = new ArrayList<>();
        List<Post> dislikedPosts = new ArrayList<>();
        if(user != null){
            likedPosts = userService.getLikedPosts(user.getId());
            dislikedPosts = userService.getDislikedPosts(user.getId());
        }

        List<Post> allPosts = new ArrayList<>(answers);
        allPosts.add(0, post);

        List<Comment> comments = commentService.getByPostId(postId);

        answers = postService.getAnswersByPostId(postId);
        List<AppArea> appAreas = Arrays.asList(AppArea.values());

        modelAndView
                .addObject(PageAttributes.POST, post)
                .addObject(PageAttributes.APPAREAS, appAreas)
                .addObject(PageAttributes.POSTS_OF_APPAAREA,
                        ControllerUtils.getNumberOfPostsForAppArea(appAreas, postService))
                .addObject(PageAttributes.POSTCOMMENTS, comments)
                .addObject(PageAttributes.ANSWERS, answers)
                .addObject(PageAttributes.ANSWERCOMMENTS, answerComments)
                .addObject(PageAttributes.LIKEDPOSTS, likedPosts)
                .addObject(PageAttributes.DISLIKEDPOSTS, dislikedPosts)
                .addObject(PageAttributes.NUMOFLIKES,
                        ControllerUtils.getNumberOfLikes(allPosts, postService))
                .addObject(PageAttributes.NUMOFDISLIKES,
                        ControllerUtils.getNumberOfDislikes(allPosts, postService))
                .addObject(PageAttributes.NUMOFANSWERS,
                        ControllerUtils.getNumberOfAnswers(posts, postService));

        List<User> users = postService.getNotificationRecipients(postId);
        try {
            postService.notifyUsers(users, post, EmailType.NEW_RESPONSE);
        } catch (RuntimeException e) {

        }

        return modelAndView;
    }
}
