package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.web.PageAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by nane on 6/25/17
 */
@Controller
@RequestMapping("/")
public class HomeController {

    private final PostService postService;
    private List<AppArea> appAreas;

    @Autowired
    public HomeController(PostService postService) {
        this.postService = postService;
        appAreas = Arrays.asList(AppArea.values());
    }

    @RequestMapping("/")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject(PageAttributes.ALLPOSTS, postService.getAll());
        modelAndView.addObject(PageAttributes.APPAREAS, appAreas);
        return modelAndView;
    }
}
