package com.workfront.internship.workflow.api;

import com.workfront.internship.workflow.dao.PostDAO;
import com.workfront.internship.workflow.dao.impl.PostDAOImpl;
import com.workfront.internship.workflow.domain.Post;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Vahag on 6/9/2017
 */
public class AppAreaPostsController extends HttpServlet{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostDAO postDAO = new PostDAOImpl();

        String url = req.getRequestURL().toString();
        long id = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));

        List<Post> posts = postDAO.getByAppAreaId(id);
        req.setAttribute("appAreaPosts", posts);

        getServletConfig()
                .getServletContext()
                .getRequestDispatcher("/home.jsp")
                .forward(req, resp);
    }
}
