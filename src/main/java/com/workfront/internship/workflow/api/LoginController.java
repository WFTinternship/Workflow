package com.workfront.internship.workflow.api;

import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.service.UserService;
import com.workfront.internship.workflow.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

//        User user = new User();
//        UserService userService = new UserServiceImpl();
//
//        String jsp;
//        try {
//            userService.add(user);
//            jsp = "/pages/home.jsp";
//        }catch (RuntimeException e){
//            e.printStackTrace();
//            jsp = "/pages/login.jsp";
//        }


        getServletConfig()
                .getServletContext()
                .getRequestDispatcher("/pages/login.jsp")
                .forward(req, resp);
    }

}
