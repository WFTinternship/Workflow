package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.controller.utils.ControllerUtils;
import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.exceptions.service.ServiceLayerException;
import com.workfront.internship.workflow.exceptions.service.InvalidObjectException;
import com.workfront.internship.workflow.service.CommentService;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.service.UserService;
import com.workfront.internship.workflow.service.util.ServiceUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
public class UserController extends BaseController {

    private static final String DEFAULT_AVATAR_URL = "/images/default/user-avatar.png";
    private UserService userService;
    private PostService postService;

    public UserController() {
    }

    @Autowired
    public UserController(UserService userService, PostService postService, CommentService commentService) {
        this.userService = userService;
        this.postService = postService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("login");
        ControllerUtils.setDefaultAttributes(postService, modelAndView);
        modelAndView.addObject(PageAttributes.LOGIN_REQUEST, true);
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
        ControllerUtils.setDefaultAttributes(postService, modelAndView);

        if (!modelAndView.getViewName().equals("login")) {
            modelAndView.setViewName("redirect:/new-post");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signUp() {
        ModelAndView modelAndView = new ModelAndView("login");
        ControllerUtils.setDefaultAttributes(postService, modelAndView);
        modelAndView.addObject(PageAttributes.LOGIN_REQUEST, false);
        return modelAndView;
    }

    @RequestMapping(value = "/signup/verify", method = RequestMethod.POST)
    public ModelAndView verify(@RequestParam("emailajax") String email,
                               @RequestParam("verify") String code,
                               RedirectAttributes redirectAttributes) {
        User user = userService.getByEmail(email);

        String verificationCode = ServiceUtils.hashString(user.getPassword()).substring(0, 6);

        if (code.isEmpty()){
            redirectAttributes.addFlashAttribute(PageAttributes.MESSAGE,
                    "Your sign up was canceled.");
            return new ModelAndView("redirect:/signup");
        }

        if (!code.equals(verificationCode)) {
            userService.deleteById(user.getId());
            redirectAttributes.addFlashAttribute(PageAttributes.MESSAGE,
                    "Sorry, the code is invalid.");
            return new ModelAndView("redirect:/signup");
        }
        redirectAttributes.addFlashAttribute(PageAttributes.MESSAGE,
                "Congratulations! Your sign up was successful!");
        return new ModelAndView("redirect:/login");
    }

    private ModelAndView authenticate(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter(PageAttributes.EMAIL);
        String password = request.getParameter(PageAttributes.PASSWORD);

        ModelAndView modelAndView = new ModelAndView();
        User user;
        try {
            user = userService.authenticate(email, password);
            HttpSession session = request.getSession();
            session.setAttribute(PageAttributes.USER, user);

            modelAndView
                    .addObject(PageAttributes.USER, user);

            response.setStatus(200);
            modelAndView.setViewName("redirect:/home");
        } catch (RuntimeException e) {
            modelAndView
                    .addObject(PageAttributes.USER, null)
                    .addObject(PageAttributes.MESSAGE,
                            "The email or password is incorrect. Please try again.");
            response.setStatus(405);
            modelAndView.setViewName("redirect:/login");
        }
        return modelAndView;
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(PageAttributes.USER, null);
        session.invalidate();

        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(value = "/users/*", method = RequestMethod.GET)
    public ModelAndView profile(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("user");

        String url = request.getRequestURL().toString();
        long userId = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));

        User user = userService.getById(userId);

        List<Post> postList = postService.getByUserId(userId);
        List<AppArea> myAppAreas = userService.getAppAreasById(userId);

        List<AppArea> allAppAreas = new ArrayList<>(Arrays.asList(AppArea.values()));
        allAppAreas.removeAll(myAppAreas);

        long numOfUsersAnswers = postService.getAnswersByUserId(userId).size();
        long numOfUsersPosts = postList.size();

        ControllerUtils.setDefaultAttributes(postService, postList, modelAndView);

        modelAndView
                .addObject(PageAttributes.ALLPOSTS, postList)
                .addObject(PageAttributes.MY_APPAREAS, myAppAreas)
                .addObject(PageAttributes.APPAREAS, allAppAreas)
                .addObject(PageAttributes.NUM_OF_USERS_POSTS, numOfUsersPosts)
                .addObject(PageAttributes.NUM_OF_USERS_ANSWERS, numOfUsersAnswers)
                .addObject(PageAttributes.PROFILE_OWNER, user);
        return modelAndView;
    }

    @RequestMapping(value = "/updateAvatar", method = RequestMethod.POST)
    public ModelAndView updateAvatar(HttpServletRequest request,
                                     @RequestParam(value = "avatar", required = false) MultipartFile image)
            throws IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(PageAttributes.USER);

        ModelAndView modelAndView = new ModelAndView("redirect:/users/" + user.getId());

        if (!image.isEmpty()) {
            String file = image.getOriginalFilename();
            String ext = file.substring(file.lastIndexOf("."));

            byte[] imageBytes = image.getBytes();
            String uploadPath = "/images/uploads/users/" + user.getEmail();
            String realPath = request.getServletContext().getRealPath(uploadPath);
            File uploadDir = new File(realPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            } else {
                FileUtils.cleanDirectory(uploadDir);
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
        return modelAndView;
    }

    @RequestMapping(value = "/login/post/*", method = RequestMethod.POST)
    public ModelAndView loginAndRedirectToPost(HttpServletRequest request,
                                               HttpServletResponse response) {
        ModelAndView modelAndView = authenticate(request, response);

        String url = request.getRequestURL().toString();
        long postId = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));

        modelAndView.setViewName("redirect:/post/" + postId);

        return modelAndView;
    }

    @RequestMapping(value = "/edit/*", method = RequestMethod.GET)
    public ModelAndView viewProfile(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("edit_profile");

        String url = request.getRequestURL().toString();
        long userId = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));

        User user = userService.getById(userId);

        List<Post> postList = postService.getByUserId(userId);
        List<AppArea> myAppAreas = userService.getAppAreasById(userId);

        List<AppArea> allAppAreas = new ArrayList<>(Arrays.asList(AppArea.values()));
        allAppAreas.removeAll(myAppAreas);

        ControllerUtils.setDefaultAttributes(postService, postList, modelAndView);

        modelAndView
                .addObject(PageAttributes.ALLPOSTS, postList)
                .addObject(PageAttributes.MY_APPAREAS, myAppAreas)
                .addObject(PageAttributes.APPAREAS, allAppAreas)
                .addObject(PageAttributes.PROFILE_OWNER, user);
        return modelAndView;
    }

    @RequestMapping(value = "/edit-profile", method = RequestMethod.POST)
    public ModelAndView editProfile(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(PageAttributes.USER);

        ModelAndView modelAndView = new ModelAndView("redirect:/users/" + user.getId());

        String firstName = request.getParameter(PageAttributes.FIRST_NAME);
        String lastName = request.getParameter(PageAttributes.LAST_NAME);
        String email = request.getParameter(PageAttributes.EMAIL);

        user
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email);

        try {
            userService.updateProfile(user);
        } catch (RuntimeException e) {
            modelAndView.addObject(PageAttributes.MESSAGE,
                    "Sorry, there has been a problem.");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/update-password", method = RequestMethod.POST)
    public ModelAndView updatePassword(HttpServletRequest request,
                                       RedirectAttributes redirectAttributes) {
        User user = (User) request.getSession().getAttribute(PageAttributes.USER);

        ModelAndView modelAndView = new ModelAndView("redirect:/users/" + user.getId());

        String oldPassword = request.getParameter(PageAttributes.PASSWORD);
        String newPassword = request.getParameter(PageAttributes.NEW_PASSWORD);
        String confirmPassword = request.getParameter(PageAttributes.CONFIRM_PASSWORD);
        String password;
        try {
            password = userService.verifyNewPassword(user, oldPassword,
                    newPassword, confirmPassword);
        } catch (ServiceLayerException e) {
            redirectAttributes.addFlashAttribute(PageAttributes.MESSAGE,
                    "Your password is not correct.");
            return modelAndView;
        }

        user.setPassword(password);

        try {
            userService.updateProfile(user);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute(PageAttributes.MESSAGE,
                    "Sorry, there has been a problem.");
        }
        return modelAndView;
    }
}
