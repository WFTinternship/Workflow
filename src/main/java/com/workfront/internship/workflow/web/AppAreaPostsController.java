package com.workfront.internship.workflow.web;

import com.workfront.internship.workflow.dao.PostDAO;
import com.workfront.internship.workflow.dao.impl.PostDAOImpl;
import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.Post;
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
 * Created by Vahag on 6/9/2017
 */
public class AppAreaPostsController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostService postService = BeanProvider.getPostService();

        String url = req.getRequestURL().toString();
        long id = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));

        List<Post> posts = postService.getByAppAreaId(id);
        req.setAttribute(PageAttributes.ALLPOSTS, posts);

       /* int size = posts.size();
        req.setAttribute(PageAttributes.COUNT_OF_POSTS,size);*/


        List<AppArea> appAreas = Arrays.asList(AppArea.values());
        req.setAttribute(PageAttributes.APPAREAS, appAreas);

        PostDAO postDAO = new PostDAOImpl();
        List<Integer> sizeOfPostsBySameAppAreaID = new ArrayList<>();
        for(AppArea appArea : appAreas){
            sizeOfPostsBySameAppAreaID.add(postDAO.getByAppAreaId(appArea.getId()).size());
        }
        req.setAttribute(PageAttributes.POSTS_OF_APPAAREA,sizeOfPostsBySameAppAreaID);

        getServletConfig()
                .getServletContext()
                .getRequestDispatcher("/pages/home.jsp")
                .forward(req, resp);
    }
}

