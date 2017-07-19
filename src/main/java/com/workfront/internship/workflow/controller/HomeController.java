package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.controller.utils.ControllerUtils;
import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.service.AppAreaService;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.web.PageAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nane on 6/25/17
 */
@Controller
@RequestMapping("/")
public class HomeController {
    private PostService postService;
    private List<AppArea> appAreas;
    private List<Post> allPosts;
    private AppAreaService appAreaService;

    public HomeController() {
    }

    @Autowired
    public HomeController(PostService postService, AppAreaService appAreaService) {
        this.postService = postService;
        appAreas = Arrays.asList(AppArea.values());
        this.appAreaService = appAreaService;
    }

    @PostConstruct
    public void init() {
        AppArea[] appAreas = AppArea.values();
        for (AppArea appArea : appAreas) {
            if (appAreaService.getById(appArea.getId()) == null) {
                appAreaService.add(appArea);
            }
        }
    }

    @RequestMapping(value = {"/", "/home"})
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("home");

        allPosts = postService.getAll();
        List<Post> posts = ControllerUtils.getFirstPagePosts(allPosts);

        ControllerUtils.setDefaultAttributes(postService, modelAndView);

        modelAndView
                .addObject(PageAttributes.POSTS, posts)
                .addObject(PageAttributes.TOTAL, allPosts.size())
                .addObject(PageAttributes.APPAREAS, appAreas)
                .addObject(PageAttributes.NUMOFANSWERS,
                        ControllerUtils.getNumberOfAnswers(posts, postService))
                .addObject(PageAttributes.POSTS_OF_APPAAREA,
                        ControllerUtils.getNumberOfPostsForAppArea(appAreas, postService))
                .addObject(PageAttributes.TOPPOSTS, ControllerUtils.getTopPosts(postService, posts));

        return modelAndView;
    }

    @RequestMapping(value = "/home/*")
    public ModelAndView home(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("home");

        String url = request.getRequestURL().toString();
        int page = Integer.parseInt(url.substring(url.lastIndexOf('/') + 1));

        allPosts = postService.getAll();
        List<Post> posts = ControllerUtils.getPostsByPage(allPosts, page);

        ControllerUtils.setDefaultAttributes(postService, allPosts, modelAndView);

        modelAndView
                .addObject(PageAttributes.POSTS, posts)
                .addObject(PageAttributes.TOTAL, allPosts.size());
        return modelAndView;
    }

    @RequestMapping(value = "/appArea/*", method = RequestMethod.GET)
    public ModelAndView appArea(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("home");

        String url = request.getRequestURL().toString();
        long id = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));

        // getting and passing all posts to appAreas page
        List<Post> postsByAppArea = postService.getByAppAreaId(id);
        if (postsByAppArea.size() == 0) {
            request.setAttribute(PageAttributes.MESSAGE,
                    "No posts were found in " + AppArea.getById(id).getName() + " Application Area.");
        }

        ControllerUtils.setDefaultAttributes(postService, modelAndView);

        // pass all appAreas to appAreas page
        modelAndView
                .addObject(PageAttributes.POSTS, postsByAppArea);
        return modelAndView;
    }

    @RequestMapping(value = "topPosts", method = RequestMethod.GET)
    public ModelAndView topPosts(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("home");

        // getting and passing all posts
        allPosts = postService.getAll();
        if (allPosts.size() == 0) {
            request.setAttribute(PageAttributes.MESSAGE,
                    "No posts were found");
        }

        ControllerUtils.setDefaultAttributes(postService, allPosts, modelAndView);

        List<Post> mostDiscussedPosts = ControllerUtils.getTopPosts(postService, allPosts);

        modelAndView.addObject(PageAttributes.ALLPOSTS, mostDiscussedPosts)
                .addObject(PageAttributes.NUMOFANSWERS, ControllerUtils.getNumberOfAnswers(mostDiscussedPosts, postService));
        return modelAndView;
    }

    @RequestMapping(value = "mostDiscussedPosts", method = RequestMethod.GET)
    public ModelAndView mostDiscussedPosts(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("home");

        // getting and passing all posts
        allPosts = postService.getAll();
        if (allPosts.size() == 0) {
            request.setAttribute(PageAttributes.MESSAGE,
                    "No posts were found");
        }

        ControllerUtils.setDefaultAttributes(postService, allPosts, modelAndView);

        List<Post> mostDiscussedPosts = ControllerUtils.getMostDiscussedPosts(postService, allPosts);

        modelAndView.addObject(PageAttributes.ALLPOSTS, mostDiscussedPosts)
                .addObject(PageAttributes.NUMOFANSWERS, ControllerUtils.getNumberOfAnswers(mostDiscussedPosts, postService));
        return modelAndView;
    }

}
