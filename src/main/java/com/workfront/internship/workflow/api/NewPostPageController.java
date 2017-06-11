package com.workfront.internship.workflow.api;

import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.service.impl.PostServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Vahag on 6/8/2017
 */
public class NewPostPageController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<AppArea> appAreas = Arrays.asList(AppArea.values());
        req.setAttribute("appAreas", appAreas);

        PostService postService = new PostServiceImpl();

        List<Post> posts = postService.getAll();
        req.setAttribute("allPosts", posts);

        getServletConfig().
                getServletContext().
                getRequestDispatcher("/pages/new_post.jsp").
                forward(req,resp);
    }
}
