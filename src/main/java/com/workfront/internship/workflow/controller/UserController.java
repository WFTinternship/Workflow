package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.controller.utils.ControllerUtils;
import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.service.UserService;
import com.workfront.internship.workflow.service.util.ServiceUtils;
import com.workfront.internship.workflow.web.PageAttributes;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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

    private static final String DEFAULT_AVATAR_URL = "/images/default/user-avatar.png";
    private UserService userService;
    private PostService postService;
    private List<AppArea> appAreas;

    public UserController() {
    }

    @Autowired
    public UserController(UserService userService, PostService postService) {
        this.userService = userService;
        appAreas = new ArrayList<>(Arrays.asList(AppArea.values()));
        this.postService = postService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView
                .addObject(PageAttributes.APPAREAS, appAreas)
                .addObject(PageAttributes.POSTS_OF_APPAAREA,
                        ControllerUtils.getNumberOfPostsForAppArea(appAreas, postService));
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = authenticate(request, response);
        List<Post> posts = postService.getAll();

        modelAndView
                .addObject(PageAttributes.ALLPOSTS, posts)
                .addObject(PageAttributes.APPAREAS, appAreas)
                .addObject(PageAttributes.POSTS_OF_APPAAREA,
                        ControllerUtils.getNumberOfPostsForAppArea(appAreas, postService));
        return modelAndView;
    }

    @RequestMapping(value = "/login/new-post", method = RequestMethod.POST)
    public ModelAndView loginAndRedirect(HttpServletRequest request,
                                         HttpServletResponse response) {
        ModelAndView modelAndView = authenticate(request, response);

        if (!modelAndView.getViewName().equals("login")) {
            modelAndView.setViewName("new_post");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signUp(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("login");
        request.setAttribute(PageAttributes.APPAREAS, appAreas);
        modelAndView.setStatus(HttpStatus.GONE);
        return modelAndView;
    }

    @RequestMapping(value = "/signup/verify", method = RequestMethod.POST)
    public ModelAndView verify(@RequestParam("emailajax") String email,
                               @RequestParam("verify") String code) {
        User user = userService.getByEmail(email);
        String verificationCode = ServiceUtils.hashString(user.getPassword()).substring(0, 6);

        ModelAndView modelAndView = new ModelAndView("login");

        if (!code.equals(verificationCode)) {
            userService.deleteById(user.getId());
            modelAndView.addObject(PageAttributes.MESSAGE,
                    "Sorry, the code is invalid.");
            return modelAndView;
        }
        modelAndView
                .addObject(PageAttributes.APPAREAS, appAreas)
                .addObject(PageAttributes.MESSAGE,
                "Congratulations! Your sign up was successful!");
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

            String avatar = user.getAvatarURL();

            session.setAttribute(PageAttributes.USER, user);
            session.setAttribute(PageAttributes.AVATAR, avatar);

            modelAndView
                    .addObject(PageAttributes.USER, user)
                    .addObject(PageAttributes.AVATAR, avatar);

            response.setStatus(200);
            modelAndView.setViewName("home");
        } catch (RuntimeException e) {
            modelAndView
                    .addObject(PageAttributes.USER, null)
                    .addObject(PageAttributes.MESSAGE,
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

        List<Post> posts = postService.getAll();

        modelAndView
                .addObject(PageAttributes.ALLPOSTS, posts)
                .addObject(PageAttributes.APPAREAS, appAreas)
                .addObject(PageAttributes.POSTS_OF_APPAAREA,
                        ControllerUtils.getNumberOfPostsForAppArea(appAreas, postService));
        return modelAndView;
    }

    @RequestMapping(value = "/users/*", method = RequestMethod.GET)
    public ModelAndView profile(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("user");

        String url = request.getRequestURL().toString();
        long userId = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));

        List<Post> postList = postService.getByUserId(userId);
        List<AppArea> myAppAreas = userService.getAppAreasById(userId);

        List<AppArea> allAppAreas = new ArrayList<>(Arrays.asList(AppArea.values()));
        allAppAreas.removeAll(myAppAreas);

        modelAndView
                .addObject(PageAttributes.ALLPOSTS, postList)
                .addObject(PageAttributes.MYAPPAREAS, myAppAreas)
                .addObject(PageAttributes.APPAREAS, allAppAreas)
                .addObject(PageAttributes.POSTS_OF_APPAAREA,
                        ControllerUtils.getNumberOfPostsForAppArea(appAreas, postService))
                .addObject(PageAttributes.PROFILEOWNERID, userId);
        return modelAndView;
    }

    @RequestMapping(value = "/updateAvatar", method = RequestMethod.POST)
    public ModelAndView updateAvatar(HttpServletRequest request, HttpServletResponse response,
                                     @RequestParam(value = "avatar", required = false) MultipartFile image)
            throws IOException {
        ModelAndView modelAndView = new ModelAndView("user");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(PageAttributes.USER);

        if (!image.isEmpty()) {
            String file = image.getOriginalFilename();
            String ext = file.substring(file.lastIndexOf("."));

            byte[] imageBytes = image.getBytes();
            String uploadPath = "/images/uploads/users/" + user.getEmail();
            String realPath = request.getServletContext().getRealPath(uploadPath);
            File uploadDir = new File(realPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String fileName = new File(user.getFirstName() + "Avatar").getName();
            String filePath = realPath + "/" + fileName + ext;
            FileUtils.writeByteArrayToFile(new File(filePath), imageBytes);
            user.setAvatarURL(uploadPath + "/" + fileName + ext);
        } else {
            user.setAvatarURL(DEFAULT_AVATAR_URL);
        }
        try {
            userService.updateAvatar(user);
        } catch (RuntimeException e) {
            modelAndView.addObject(PageAttributes.MESSAGE,
                    "Sorry your avatar was not updated");
        }
        List<Post> posts = postService.getByUserId(user.getId());

        List<AppArea> myAppAreas = userService.getAppAreasById(user.getId());

        List<AppArea> allAppAreas = new ArrayList<>(Arrays.asList(AppArea.values()));
        allAppAreas.removeAll(myAppAreas);

        modelAndView
                .addObject(PageAttributes.ALLPOSTS, posts)
                .addObject(PageAttributes.MYAPPAREAS, myAppAreas)
                .addObject(PageAttributes.APPAREAS, allAppAreas)
                .addObject(PageAttributes.POSTS_OF_APPAAREA,
                        ControllerUtils.getNumberOfPostsForAppArea(appAreas, postService))
                .addObject(PageAttributes.PROFILEOWNERID, user.getId());
        return modelAndView;
    }
}
