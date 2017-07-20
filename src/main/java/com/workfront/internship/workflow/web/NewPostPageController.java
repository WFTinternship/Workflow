package com.workfront.internship.workflow.web;

import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Vahag on 6/8/2017
 */
public class NewPostPageController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<AppArea> appAreas = Arrays.asList(AppArea.values());
        req.setAttribute(PageAttributes.APPAREAS, appAreas);

        PostService postService = BeanProvider.getPostService();
        List<Post> posts = postService.getAll();
        req.setAttribute(PageAttributes.ALLPOSTS, posts);

        getServletConfig().
                getServletContext().
                getRequestDispatcher("/pages/new_post.jsp").
                forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<AppArea> appAreas = Arrays.asList(AppArea.values());
        req.setAttribute(PageAttributes.APPAREAS, appAreas);

        String title = req.getParameter(PageAttributes.TITLE);
        String content = req.getParameter(PageAttributes.POST_CONTENT);

        HttpSession session = req.getSession();
        User user = (User)session.getAttribute(PageAttributes.USER);

        AppArea appArea = AppArea.getById(Integer.parseInt(req.getParameter(PageAttributes.APPAREA)));

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        PostService postService = BeanProvider.getPostService();
        Post post = new Post();
        post.setTitle(title)
                .setAppArea(appArea)
                .setContent(content)
                .setUser(user)
                .setPostTime(timestamp);
        String jsp;
        try {
            postService.add(post);
            jsp = "/pages/home.jsp";
        }catch (RuntimeException e){
            jsp = "/pages/new_post.jsp";
        }

        List<Post> posts = postService.getAll();
        req.setAttribute(PageAttributes.ALLPOSTS, posts);

        getServletConfig().
                getServletContext().
                getRequestDispatcher(jsp).
                forward(req,resp);
    }


}
