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
 * Created by nane on 6/12/17
 */
public class NewAnswerController extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PostService postService = BeanProvider.getPostService();

        String url = req.getRequestURL().toString();
        long postId = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));

        String content = req.getParameter("reply");

        Post post = postService.getById(postId);
        req.setAttribute(PageAttributes.POST, post);

        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("user");

        List<AppArea> appAreas = Arrays.asList(AppArea.values());
        req.setAttribute(PageAttributes.APPAREAS, appAreas);

        AppArea appArea = post.getAppArea();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Post answer = new Post();
        answer.setUser(user)
                .setContent(content)
                .setAppArea(appArea)
                .setTitle(post.getTitle())
                .setPostTime(timestamp)
                .setPost(post);

        try {
            postService.add(answer);
        }catch (RuntimeException e){
            //TODO: send message that the answer was not added.
        }

        List<Post> answers = postService.getAnswersByPostId(postId);
        req.setAttribute("answers", answers);

        getServletConfig().
                getServletContext().
                getRequestDispatcher("/pages/post.jsp").
                forward(req,resp);
    }
}
