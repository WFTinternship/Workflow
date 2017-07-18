package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.controller.utils.ControllerUtils;
import com.workfront.internship.workflow.controller.utils.EmailType;
import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.Comment;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.service.AppAreaService;
import com.workfront.internship.workflow.service.CommentService;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.service.UserService;
import com.workfront.internship.workflow.web.PageAttributes;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nane on 6/25/17
 */
@Controller
public class PostController {

    private static final Logger LOGGER = Logger.getLogger(PostController.class);
    private CommentService commentService;
    private PostService postService;
    private UserService userService;
    private AppAreaService appAreaService;
    private List<AppArea> appAreas;

    public PostController() {
    }

    @Autowired
    public PostController(PostService postService, CommentService commentService, UserService userService, AppAreaService appAreaService) {
        this.postService = postService;
        appAreas = Arrays.asList(AppArea.values());
        this.commentService = commentService;
        this.userService = userService;
        this.appAreaService = appAreaService;
    }

    @RequestMapping(value = "/post/*", method = RequestMethod.GET)
    public ModelAndView post(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("post");

        String url = request.getRequestURL().toString();
        long id = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));

        User user = (User) request.getSession().getAttribute(PageAttributes.USER);

        List<Post> likedPosts = new ArrayList<>();
        List<Post> dislikedPosts = new ArrayList<>();
        if (user != null) {
            likedPosts = userService.getLikedPosts(user.getId());
            dislikedPosts = userService.getDislikedPosts(user.getId());
        }

        Post post = postService.getById(id);

        List<Comment> postComments = commentService.getByPostId(post.getId());

        List<Post> answers = postService.getAnswersByPostId(post.getId());

        List<Post> allPosts = new ArrayList<>(answers);
        allPosts.add(0, post);

        ControllerUtils.setDefaultAttributes(postService, modelAndView);

        modelAndView
                .addObject(PageAttributes.POST, post)
                .addObject(PageAttributes.POSTCOMMENTS, postComments)
                .addObject(PageAttributes.ANSWERS, answers)
                .addObject(PageAttributes.LIKEDPOSTS, likedPosts)
                .addObject(PageAttributes.DISLIKEDPOSTS, dislikedPosts)
                .addObject(PageAttributes.POST, post)
                .addObject(PageAttributes.NUMOFLIKES,
                        ControllerUtils.getNumberOfLikes(allPosts, postService))
                .addObject(PageAttributes.NUMOFDISLIKES,
                        ControllerUtils.getNumberOfDislikes(allPosts, postService));

        return modelAndView;
    }

    @RequestMapping(value = "/new-post", method = RequestMethod.POST)
    public ModelAndView newPost(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("home");

        String title = request.getParameter(PageAttributes.TITLE);
        String content = request.getParameter(PageAttributes.POSTCONTENT);
        String notify = request.getParameter(PageAttributes.NOTE);

        AppArea appArea = AppArea.getById(Integer
                .parseInt(request.getParameter(PageAttributes.APPAREA)));

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(PageAttributes.USER);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Post post = new Post();
        post.setTitle(title)
                .setAppArea(appArea)
                .setContent(content)
                .setUser(user)
                .setPostTime(timestamp);
        try {
            postService.add(post);
        } catch (RuntimeException e) {
            modelAndView.addObject(PageAttributes.MESSAGE,
                    "Sorry, your post was not added. Please try again")
                    .setViewName("new_post");
        }

        if (notify != null && notify.equals("on")) {
            postService.getNotified(post.getId(), post.getUser().getId());
        }

        try {
            List<User> usersToNotify = appAreaService.getUsersById(appArea.getId());
            postService.notifyUsers(usersToNotify, post, EmailType.NEW_POST);
        } catch (RuntimeException e) {
            LOGGER.info("Failed to send emails");
        }

        ControllerUtils.setDefaultAttributes(postService, modelAndView);

        return modelAndView;
    }

    @RequestMapping(value = {"/new-post"}, method = RequestMethod.GET)
    public ModelAndView newPost() {
        ModelAndView modelAndView = new ModelAndView("new_post");

        ControllerUtils.setDefaultAttributes(postService, modelAndView);

        return modelAndView;
    }

    @RequestMapping(value = {"/delete-post/*"}, method = RequestMethod.POST)
    public ModelAndView deletePost(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("user");

        String url = request.getRequestURL().toString();
        long postId = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));

        try {
            postService.delete(postId);
        } catch (RuntimeException e) {
            modelAndView.addObject(PageAttributes.MESSAGE,
                    "Sorry, your post was not deleted. " +
                            "If you really want to delete it please try again.")
                    .addObject(PageAttributes.POST, postService.getById(postId))
                    .setViewName("post");
            return modelAndView;
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(PageAttributes.USER);

        List<Post> postList = postService.getByUserId(user.getId());
        List<AppArea> myAppAreas = userService.getAppAreasById(user.getId());

        List<AppArea> allAppAreas = new ArrayList<>(Arrays.asList(AppArea.values()));
        allAppAreas.removeAll(myAppAreas);

        ControllerUtils.setDefaultAttributes(postService, postList, modelAndView);

        modelAndView
                .addObject(PageAttributes.ALLPOSTS, postList)
                .addObject(PageAttributes.MYAPPAREAS, myAppAreas)
                .addObject(PageAttributes.PROFILEOWNER, user);
        return modelAndView;
    }

    @RequestMapping(value = {"/searchPost"}, method = RequestMethod.POST)
    public ModelAndView searchPost(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("home");

        String postTitle = request.getParameter("postTitle");
        List<Post> posts = postService.getByTitle(postTitle);

        String searchMessage = "search results for post with title " + "'" + postTitle + "'";

        ControllerUtils.setDefaultAttributes(postService, posts, modelAndView);

        modelAndView
                .addObject(PageAttributes.ALLPOSTS, posts)
                .addObject(PageAttributes.SEARCHMESSAGE, searchMessage);

        return modelAndView;
    }

}