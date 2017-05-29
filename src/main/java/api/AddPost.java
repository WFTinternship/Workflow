package api;

import dao.impl.PostDAOImpl;
import dao.PostDAO;
import dataModel.Post;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by nane on 5/27/17.
 */
public class AddPost extends HttpServlet{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        long userId = Long.valueOf(req.getParameter("userId"));
        long appAreaId = Long.valueOf(req.getParameter("appAreaId"));
        String title = req.getParameter("title");
        String content = req.getParameter("content");

        Post post = new Post();
        //post.setUserId(userId);
        //post.setAppAreaId(appAreaId);
        post.setTitle(title);
        post.setContent(content);

        PostDAO postDAO = new PostDAOImpl();
        boolean success = postDAO.add(post);
        if (!success){
            resp.setStatus(400);
        }else {
            resp.setStatus(200);
        }
    }
}
