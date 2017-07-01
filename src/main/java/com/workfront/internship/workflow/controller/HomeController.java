package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.controller.utils.ControllerUtils;
import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.Post;
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
    private List<Post> posts;
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
    public void init(){
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

        // getting and passing all posts to home page
        posts = postService.getAll();
        modelAndView.addObject(PageAttributes.ALLPOSTS, posts);

        // passing all appAreas to home page
        modelAndView.addObject(PageAttributes.APPAREAS, appAreas);


        modelAndView.addObject(PageAttributes.POSTS_OF_APPAAREA,
                ControllerUtils.getNumberOfPostsForAppArea(appAreas, postService));

        return modelAndView;
    }

    @RequestMapping(value = "/appArea/*", method = RequestMethod.GET)
    public ModelAndView appArea(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("home");

        String url = request.getRequestURL().toString();
        long id = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));

        // getting and passing all posts to appAreas page
        posts = postService.getByAppAreaId(id);
        if (posts.size() == 0) {
            request.setAttribute(PageAttributes.MESSAGE,
                    "No posts were found in " + AppArea.getById(id).getName() + " Application Area.");
        }
        modelAndView.addObject(PageAttributes.ALLPOSTS, posts);

        // pass all appAreas to appAreas page
        modelAndView.addObject(PageAttributes.APPAREAS, appAreas);

        modelAndView.addObject(PageAttributes.POSTS_OF_APPAAREA,
                ControllerUtils.getNumberOfPostsForAppArea(appAreas, postService));

        return modelAndView;
    }
}
