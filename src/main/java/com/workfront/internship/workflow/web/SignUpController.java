package com.workfront.internship.workflow.web;

import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nane on 6/10/17
 */
public class SignUpController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<AppArea> appAreas = Arrays.asList(AppArea.values());
        req.setAttribute(PageAttributes.APPAREAS, appAreas);

        String url = req.getRequestURL().toString();
        String requestType = url.substring(url.lastIndexOf('/') + 1);

        if (requestType.equals("login")) {
            getServletConfig()
                    .getServletContext()
                    .getRequestDispatcher("/pages/login.jsp")
                    .forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<AppArea> appAreas = Arrays.asList(AppArea.values());
        req.setAttribute(PageAttributes.APPAREAS, appAreas);

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = new User();
        user.setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPassword(password);
        UserService userService = BeanProvider.getUserService();

        String jsp;
        try {
            if (userService.getByEmail(email) != null){
                req.setAttribute("message", "The email is already used");
            }else {
                userService.sendEmail(user);
                userService.add(user);
                resp.setStatus(200);
            }
            jsp = "/pages/login.jsp";

        } catch (RuntimeException e) {
            resp.setStatus(405);
            // need to try again, go to sign up page
            jsp = "/pages/login.jsp";
        }

        getServletConfig()
                .getServletContext()
                .getRequestDispatcher(jsp)
                .forward(req, resp);
    }
}
