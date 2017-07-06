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
    private CommentService commentService;

    private PostService postService;

    private List<AppArea> appAreas;

    public PostController() {
    }

    @Autowired
    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        appAreas = Arrays.asList(AppArea.values());
        this.commentService = commentService;
    }

    @RequestMapping(value = "/post/*", method = RequestMethod.GET)
    public ModelAndView post(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("post");

        String url = request.getRequestURL().toString();
        long id = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));

        Post post = postService.getById(id);

        List<Comment> postComments = commentService.getByPostId(id);

        List<Post> answers = postService.getAnswersByPostId(id);

        List<Post> allPosts = new ArrayList<>(answers);
        allPosts.add(0, post);


        for (Post answer : answers) {
            answer.setCommentList(commentService.getByPostId(answer.getId()));
        }


        List<Post> posts = postService.getAll();

        modelAndView
                .addObject(PageAttributes.POST, post)
                .addObject(PageAttributes.POSTCOMMENTS, postComments)
                .addObject(PageAttributes.ANSWERS, answers)
                .addObject(PageAttributes.POST, post)
                .addObject(PageAttributes.NUMOFLIKES,
                        ControllerUtils.getNumberOfLikes(allPosts, postService))
                .addObject(PageAttributes.NUMOFDISLIKES,
                        ControllerUtils.getNumberOfDislikes(allPosts, postService))
                .addObject(PageAttributes.POSTS_OF_APPAAREA, ControllerUtils.getNumberOfPostsForAppArea(appAreas, postService))
                .addObject(PageAttributes.ALLPOSTS, posts);

        return modelAndView;
    }

    @RequestMapping(value = "/new-post", method = RequestMethod.POST)
    public ModelAndView newPost(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("home");

        String title = request.getParameter(PageAttributes.TITLE);
        String content = request.getParameter(PageAttributes.POSTCONTENT);
        String notify = request.getParameter(PageAttributes.NOTE);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(PageAttributes.USER);
        AppArea appArea = AppArea.getById(Integer.parseInt(request.getParameter(PageAttributes.APPAREA)));

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

        List<Post> posts = postService.getAll();

        modelAndView
                .addObject(PageAttributes.POSTS_OF_APPAAREA, ControllerUtils.getNumberOfPostsForAppArea(appAreas, postService))
                .addObject(PageAttributes.ALLPOSTS, posts);

        return modelAndView;
    }

    @RequestMapping(value = {"/new-post"}, method = RequestMethod.GET)
    public ModelAndView newPost() {
        ModelAndView modelAndView = new ModelAndView("new_post");

        List<Post> posts = postService.getAll();

        modelAndView
                .addObject(PageAttributes.POSTS_OF_APPAAREA, ControllerUtils.getNumberOfPostsForAppArea(appAreas, postService))
                .addObject(PageAttributes.ALLPOSTS, posts);
        return modelAndView;
    }

    @RequestMapping(value = {"/edit-post"}, method = RequestMethod.POST)
    public ModelAndView editPost(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("post");
        Post post = (Post) request.getAttribute(PageAttributes.POST);
        try {
            postService.update(post);
        } catch (RuntimeException e) {
            modelAndView
                    .addObject(PageAttributes.MESSAGE,
                            "Sorry, your post was not updated. Please try again")
                    .setViewName("post");
        }
        return modelAndView;
    }
}
