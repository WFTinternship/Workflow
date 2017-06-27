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
public class HomeController {

    private PostService postService;

    private List<AppArea> appAreas;

    private List<Post> posts;

    private List<Integer> sizeOfPostsBySameAppAreaID;

    public HomeController(){}

    @Autowired
    public HomeController(PostService postService) {
        this.postService = postService;

        appAreas = Arrays.asList(AppArea.values());

        posts = new ArrayList<>();

        sizeOfPostsBySameAppAreaID = new ArrayList<>();
    }

    @RequestMapping(value = {"/", "home"})
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("home");

        // getting and passing all posts to home page
        posts = postService.getAll();
        modelAndView.addObject(PageAttributes.ALLPOSTS, posts);

        // passing all appAreas to home page
        modelAndView.addObject(PageAttributes.APPAREAS, appAreas);

        // getting and passing list of sizes of each posts by same appArea id to home page
        for(AppArea appArea : appAreas){
            sizeOfPostsBySameAppAreaID.add(postService.getByAppAreaId(appArea.getId()).size());
        }
        modelAndView.addObject(PageAttributes.POSTS_OF_APPAAREA, sizeOfPostsBySameAppAreaID);

        return modelAndView ;
    }

    @RequestMapping(value = {"/appArea/*", "home"}, method = RequestMethod.GET)
    public ModelAndView appArea(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("home");

        String url = request.getRequestURL().toString();
        long id = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));


        // getting and passing all posts to appAreas page
        posts = postService.getByAppAreaId(id);
        if (posts.size() == 0){
            request.setAttribute(PageAttributes.MESSAGE,
                    "No posts were found in this Application Area.");
        }
        modelAndView.addObject(PageAttributes.ALLPOSTS, posts);

        // pass all appAreas to appAreas page
        modelAndView.addObject(PageAttributes.APPAREAS, appAreas);

        // getting and passing list of sizes of each posts by same appArea id to appAreas page
        for(AppArea appArea : appAreas){
            sizeOfPostsBySameAppAreaID.add(postService.getByAppAreaId(appArea.getId()).size());
        }
        modelAndView.addObject(PageAttributes.POSTS_OF_APPAAREA, sizeOfPostsBySameAppAreaID );

        return modelAndView ;
    }

}
