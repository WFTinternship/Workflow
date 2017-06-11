package com.workfront.internship.workflow.api;

import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.domain.User;
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

        String title = req.getParameter("title");
        String content = req.getParameter("content");

        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("user");

        AppArea appArea = AppArea.getById(Integer.parseInt(req.getParameter("appArea")));

        Post post = new Post();
        post.setTitle(title)
                .setAppArea(appArea)
                .setContent(content)
                .setUser(user);
        String jsp;
        try {
            postService.add(post);
            jsp = "/pages/home.jsp";
        }catch (RuntimeException e){
            jsp = "/pages/new_post.jsp";
        }


        getServletConfig().
                getServletContext().
                getRequestDispatcher(jsp).
                forward(req,resp);
    }
}
