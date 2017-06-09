package com.workfront.internship.workflow.api;

import com.workfront.internship.workflow.domain.AppArea;

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

        getServletConfig()
                .getServletContext()
                .getRequestDispatcher("/pages/login.jsp")
                .forward(req, resp);
    }
}
