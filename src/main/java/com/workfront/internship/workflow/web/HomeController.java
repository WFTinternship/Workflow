package com.workfront.internship.workflow.web;

import com.workfront.internship.workflow.dao.PostDAO;
import com.workfront.internship.workflow.dao.impl.PostDAOImpl;
import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nane on 6/6/17
 */
public class HomeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostService postService = BeanProvider.getPostService();
        List<Post> posts = postService.getAll();
        req.setAttribute(PageAttributes.ALLPOSTS, posts);

        List<AppArea> appAreas = Arrays.asList(AppArea.values());
        req.setAttribute(PageAttributes.APPAREAS, appAreas);
        PostDAO postDAO = new PostDAOImpl();
        List<Integer> sizeOfPostsBySameAppAreaID = new ArrayList<>();
        for(AppArea appArea : appAreas){
            sizeOfPostsBySameAppAreaID.add(postDAO.getByAppAreaId(appArea.getId()).size());
        }
        req.setAttribute(PageAttributes.POSTS_OF_APPAREA,sizeOfPostsBySameAppAreaID);

        getServletConfig()
                .getServletContext()
                .getRequestDispatcher("/pages/home.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
