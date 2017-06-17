package com.workfront.internship.workflow.web;

import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.service.impl.PostServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nane on 6/15/17
 */
public class LogoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.setAttribute("user", null);
        session.invalidate();

//        PostService postService = new PostServiceImpl();
//        List<Post> posts = postService.getAll();
//        req.setAttribute(PageAttributes.allPosts, posts);
//
//        List<AppArea> appAreas = Arrays.asList(AppArea.values());
//        req.setAttribute(PageAttributes.appAreas, appAreas);

        getServletContext().
                getRequestDispatcher("/home").
                forward(req, resp);
    }
}
