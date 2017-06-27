package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.web.BeanProvider;
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

    private PostService postService;

    private List<AppArea> appAreas;

    private List<Post> posts;

    public PostController() {
    }

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
        appAreas = Arrays.asList(AppArea.values());
        posts = new ArrayList<>();
    }

    @RequestMapping(value = "/new-post", method = RequestMethod.POST)
    public ModelAndView newPost(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("home");
        setAllPosts(modelAndView);

        String title = request.getParameter(PageAttributes.TITLE);
        String content = request.getParameter(PageAttributes.POSTCONTENT);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(PageAttributes.USER);
        AppArea appArea = AppArea.getById(Integer.parseInt(request.getParameter(PageAttributes.APPAREA)));

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        PostService postService = BeanProvider.getPostService();
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
                    "Sorry, your post was not added. Please try again");
            modelAndView.setViewName("new_post");
        }
        return modelAndView;
    }


    @RequestMapping(value = {"/new-post"}, method = RequestMethod.GET)
    public ModelAndView newPost() {
        ModelAndView modelAndView = new ModelAndView("new_post");
        setAllPosts(modelAndView);
        return modelAndView;
    }

    private void setAllPosts(ModelAndView modelAndView) {
        modelAndView.addObject(PageAttributes.APPAREAS, appAreas);
        posts = postService.getAll();
        modelAndView.addObject(PageAttributes.ALLPOSTS, posts);
    }
}
