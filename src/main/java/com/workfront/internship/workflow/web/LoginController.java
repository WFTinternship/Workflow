package com.workfront.internship.workflow.web;

import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nane on 6/9/17
 */
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String url = req.getRequestURL().toString();
        String requestType = url.substring(url.lastIndexOf('/') + 1);

        List<AppArea> appAreas = Arrays.asList(AppArea.values());
        req.setAttribute(PageAttributes.APPAREAS, appAreas);

        if (requestType.equals("login")){
            getServletConfig()
                    .getServletContext()
                    .getRequestDispatcher("/pages/login.jsp")
                    .forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String url = req.getRequestURL().toString();
        String requestType = url.substring(url.lastIndexOf('/') + 1);

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserService userService = BeanProvider.getUserService();

        String jsp;
        User user;
        try {
            user = userService.authenticate(email, password);

            HttpSession session = req.getSession();
            //setting the maximum inactive to be 30 minutes.
            //session.setMaxInactiveInterval(1800);
            session.setAttribute(PageAttributes.USER, user);
            req.setAttribute(PageAttributes.USER, user);

            resp.setStatus(200);
            if (requestType.equals("new-post")){
                jsp = "/pages/new_post.jsp";
            }else {
                jsp = "/home";
            }

            List<AppArea> appAreas = Arrays.asList(AppArea.values());
            req.setAttribute(PageAttributes.APPAREAS, appAreas);

            PostService postService = BeanProvider.getPostService();
            List<Post> posts = postService.getAll();
            req.setAttribute(PageAttributes.ALLPOSTS, posts);

            getServletConfig()
                    .getServletContext()
                    .getRequestDispatcher(jsp)
                    .forward(req, resp);

        } catch (RuntimeException e) {
            //when the user was not found in database or query failed.
            req.setAttribute(PageAttributes.USER, null);
            req.setAttribute("message", "The email or password is incorrect. Please try again.");
            resp.setStatus(405);
            doGet(req, resp);
        }
    }

}
