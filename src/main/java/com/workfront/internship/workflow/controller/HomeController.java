package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.web.PageAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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

    public HomeController(){}

    @Autowired
    public HomeController(PostService postService) {
        this.postService = postService;
        appAreas = Arrays.asList(AppArea.values());
        posts = new ArrayList<>();
    }

    @RequestMapping(value = {"/", "/home"})
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("home");
        posts = postService.getAll();
        modelAndView.addObject(PageAttributes.ALLPOSTS, posts);
        modelAndView.addObject(PageAttributes.APPAREAS, appAreas);
        return modelAndView;
    }

    @RequestMapping(value = "/appArea/*", method = RequestMethod.GET)
    public ModelAndView appArea(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("home");

        String url = request.getRequestURL().toString();
        long id = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));

        posts = postService.getByAppAreaId(id);
        if (posts.size() == 0){
            request.setAttribute(PageAttributes.MESSAGE,
                    "No posts were found in this Application Area.");
        }
        modelAndView.addObject(PageAttributes.ALLPOSTS, posts);
        modelAndView.addObject(PageAttributes.APPAREAS, appAreas);
        return modelAndView;
    }

}
