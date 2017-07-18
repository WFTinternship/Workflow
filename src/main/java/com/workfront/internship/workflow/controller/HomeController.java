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
    private final int NUMBER_OF_POST_PER_PAGE = 5;
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
        allPosts = postService.getAll();
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
        List<Post> posts = getPostsByPage(1);

        modelAndView
                .addObject(PageAttributes.POSTS, posts)
                .addObject(PageAttributes.ALLPOSTS, allPosts)
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

        List<Post> posts = getPostsByPage(page);

        modelAndView
                .addObject(PageAttributes.POSTS, posts)
                .addObject(PageAttributes.ALLPOSTS, allPosts)
                .addObject(PageAttributes.APPAREAS, appAreas)
                .addObject(PageAttributes.NUMOFANSWERS,
                        ControllerUtils.getNumberOfAnswers(posts, postService))
                .addObject(PageAttributes.POSTS_OF_APPAAREA,
                        ControllerUtils.getNumberOfPostsForAppArea(appAreas, postService))
                .addObject(PageAttributes.TOPPOSTS, ControllerUtils.getTopPosts(postService, posts));

        return modelAndView;
    }

    @RequestMapping(value = "/appArea/*", method = RequestMethod.GET)
    public ModelAndView appArea(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("home");

        String url = request.getRequestURL().toString();
        long id = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));

        // getting and passing all posts to appAreas page
        allPosts = postService.getByAppAreaId(id);
        if (allPosts.size() == 0) {
            request.setAttribute(PageAttributes.MESSAGE,
                    "No posts were found in " + AppArea.getById(id).getName() + " Application Area.");
        }
        // pass all appAreas to appAreas page
        modelAndView
                .addObject(PageAttributes.ALLPOSTS, allPosts)
                .addObject(PageAttributes.APPAREAS, appAreas)
                .addObject(PageAttributes.POSTS_OF_APPAAREA,
                        ControllerUtils.getNumberOfPostsForAppArea(appAreas, postService))
                .addObject(PageAttributes.TOPPOSTS, ControllerUtils.getTopPosts(postService, allPosts));

        return modelAndView;
    }

    private List<Post> getPostsByPage(int page) {
        List<Post> allPosts = postService.getAll();
        List<Post> posts;
        int total = allPosts.size();

        if (page == 1) {
            posts = total < NUMBER_OF_POST_PER_PAGE ?
                    allPosts.subList(0, total) : allPosts.subList(0, NUMBER_OF_POST_PER_PAGE);
        } else {
            int start = (page - 1) * NUMBER_OF_POST_PER_PAGE;
            int end = start + 5;
            posts = total < end ?
                    allPosts.subList(start, total) : allPosts.subList(start, end);
        }

        return posts;
    }

}
