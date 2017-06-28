package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.service.UserService;
import com.workfront.internship.workflow.web.PageAttributes;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nane on 6/27/17
 */
@Controller
public class UserController {

    private UserService userService;

    private PostService postService;

    private List<AppArea> appAreas;

    private List<Post> posts;


    public UserController() {
    }

    @Autowired
    public UserController(UserService userService, PostService postService) {
        this.userService = userService;
        appAreas = Arrays.asList(AppArea.values());
        this.postService = postService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("login");
        request.setAttribute(PageAttributes.APPAREAS, appAreas);
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = authenticate(request, response);
        setAllPosts(modelAndView);
        return modelAndView;
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

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signUp(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("login");
        request.setAttribute(PageAttributes.APPAREAS, appAreas);
        return modelAndView;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView signUp(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam(value = "avatar", required = false) MultipartFile image) throws IOException {

        ModelAndView modelAndView = new ModelAndView("login");
        request.setAttribute(PageAttributes.APPAREAS, appAreas);

        User user = new User();

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String file = image.getOriginalFilename();
        String ext = file.substring(file.lastIndexOf("."));


        if (image != null) {
            byte[] imageBytes = image.getBytes();
            String uploadPath = "/images/uploads/users/" + email;
            String realPath = request.getServletContext().getRealPath(uploadPath);
            File uploadDir = new File(realPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String fileName = new File(firstName + "Avatar").getName();
            String filePath = realPath + File.separator + fileName + ext;
            FileUtils.writeByteArrayToFile(new File(filePath), imageBytes);
            user.setAvatarURL(uploadPath + File.separator + fileName + ext);
        }

        user.setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPassword(password);
        try {
            if (userService.getByEmail(email) != null){
                request.setAttribute("message", "The email is already used");
            }else {
                //userService.sendEmail(user);
                userService.add(user);
                modelAndView.setViewName("home");
            }
        } catch (RuntimeException e) {
            response.setStatus(405);
            request.setAttribute("message", "Your sign up was successfully canceled, please try again.");
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


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(PageAttributes.USER, null);
        session.invalidate();
        ModelAndView modelAndView = new ModelAndView("home");
        setAllPosts(modelAndView);
        return modelAndView;
    }

    private void setAllPosts(ModelAndView modelAndView) {
        modelAndView.addObject(PageAttributes.APPAREAS, appAreas);
        posts = postService.getAll();
        modelAndView.addObject(PageAttributes.ALLPOSTS, posts);
    }
}
