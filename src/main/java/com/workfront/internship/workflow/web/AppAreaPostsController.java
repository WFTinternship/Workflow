package com.workfront.internship.workflow.web;

import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.service.impl.PostServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Vahag on 6/9/2017
 */
public class AppAreaPostsController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostServiceImpl postService = new PostServiceImpl();

        String url = req.getRequestURL().toString();
        long id = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));

        List<Post> posts = postService.getByAppAreaId(id);
        req.setAttribute(PageAttributes.allPosts, posts);

        List<AppArea> appAreas = Arrays.asList(AppArea.values());
        req.setAttribute(PageAttributes.appAreas, appAreas);

        getServletConfig()
                .getServletContext()
                .getRequestDispatcher("/pages/home.jsp")
                .forward(req, resp);
    }
}

