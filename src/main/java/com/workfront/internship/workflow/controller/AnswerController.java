package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.web.PageAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Angel on 6/27/2017
 */
@Controller
public class AnswerController {

    private List<AppArea> appAreas;
    private PostService postService;
    private Post post;
    private List<Post> answers;
    private List<Post> posts;


    public AnswerController() {
    }

    @Autowired
    public AnswerController(PostService postService) {
        this.postService = postService;
        appAreas = Arrays.asList(AppArea.values());
        post = new Post();
    }

    @RequestMapping(value = {"/new-answer/*"}, method = RequestMethod.POST)
    public ModelAndView newAnswer(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("post");

        String url = request.getRequestURL().toString();
        long postId = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));

        String content = request.getParameter("reply");

        if (StringUtils.isEmpty(content)){
            request.setAttribute(PageAttributes.MESSAGE, "The body is missing.");
            setAllPosts(modelAndView);
            return modelAndView;
        }

        post = postService.getById(postId);
        request.setAttribute(PageAttributes.POST, post);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(PageAttributes.USER);

        request.setAttribute(PageAttributes.APPAREAS, appAreas);

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
        } catch (RuntimeException e) {
            request.setAttribute(PageAttributes.MESSAGE,
                    "Sorry, your answer was not added. Please try again");
        }

        answers = postService.getAnswersByPostId(postId);
        request.setAttribute("answers", answers);
        return modelAndView;

    }

    private void setAllPosts(ModelAndView modelAndView) {
        modelAndView.addObject(PageAttributes.APPAREAS, appAreas);
        posts = postService.getAll();
        modelAndView.addObject(PageAttributes.ALLPOSTS, posts);
    }

   /* @RequestMapping(value = {"/appArea*//**//*", "home"}, method = RequestMethod.GET)
    public ModelAndView editAnswer(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("home");
        return modelAndView;
    }*/
}
