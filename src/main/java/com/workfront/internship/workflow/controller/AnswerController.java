package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Angel on 6/27/2017
 */
@Controller
public class AnswerController {

    private PostService postService ;

    private Post post ;

    public AnswerController() {
    }

    @Autowired
    public AnswerController(PostService postService) {
        this.postService = postService;
        post = new Post() ;
    }

    @RequestMapping(value = {"/new-answer"}, method = RequestMethod.GET)
    public ModelAndView newAnswer(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("/new-answer");


        return modelAndView;

    }
   /* @RequestMapping(value = {"/appArea*//**//*", "home"}, method = RequestMethod.GET)
    public ModelAndView editAnswer(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("home");
        return modelAndView;
    }*/
}