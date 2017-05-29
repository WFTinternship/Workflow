package com.workfront.internship.app;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by nane on 5/23/17.
 */
public class App extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String message = "Hello Everybody";
        //resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();
        out.println("<h1>" + message + "</h1>");
    }
}