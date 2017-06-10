package com.workfront.internship.workflow.api;

import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.service.UserService;
import com.workfront.internship.workflow.service.impl.UserServiceImpl;

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
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<AppArea> appAreas = Arrays.asList(AppArea.values());
        req.setAttribute("appAreas", appAreas);

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserService userService = new UserServiceImpl();

        String jsp = req.getRequestURL().toString();
        try {
            User user = userService.authenticate(email, password);

            HttpSession session = req.getSession();
            //setting the maximum inactive to be 30 minutes.
            session.setMaxInactiveInterval(1800);
            session.setAttribute("user", user);

           // resp.setHeader("location", "http://localhost:8080");
            resp.setStatus(200);
            jsp = "/pages/home.jsp";
        } catch (RuntimeException e) {
            //when the user was not found in database or query failed.
            resp.setStatus(405);
            jsp = "/pages/login.jsp";
        }

        getServletConfig()
                .getServletContext()
                .getRequestDispatcher(jsp)
                .forward(req, resp);
    }

}
