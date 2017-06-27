package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.service.UserService;
import com.workfront.internship.workflow.web.PageAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nane on 6/27/17
 */
@Controller
public class UserController {
    private UserService userService;

    private List<AppArea> appAreas;

    private List<Post> posts;

    public UserController() {
    }

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
        appAreas = Arrays.asList(AppArea.values());
        posts = new ArrayList<>();
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("login");
        request.setAttribute(PageAttributes.APPAREAS, appAreas);
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
        return authenticate(request, response);
    }

    @RequestMapping(value = "/login/new-post", method = RequestMethod.POST)
    public ModelAndView loginAndRedirect(HttpServletRequest request,
                                         HttpServletResponse response) {
        ModelAndView modelAndView = authenticate(request, response);
        if (!modelAndView.getViewName().equals("login")){
            modelAndView.setViewName("new_post");
        }
        return modelAndView;
    }

    private ModelAndView authenticate(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(PageAttributes.APPAREAS, appAreas);

        String email = request.getParameter(PageAttributes.EMAIL);
        String password = request.getParameter(PageAttributes.PASSWORD);

        ModelAndView modelAndView = new ModelAndView();
        User user;
        try {
            user = userService.authenticate(email, password);
            HttpSession session = request.getSession();

            session.setAttribute(PageAttributes.USER, user);
            request.setAttribute(PageAttributes.USER, user);
            response.setStatus(200);
            modelAndView.setViewName("home");
        } catch (RuntimeException e) {
            request.setAttribute(PageAttributes.USER, null);
            request.setAttribute(PageAttributes.MESSAGE,
                    "The email or password is incorrect. Please try again.");
            response.setStatus(405);
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }
}
