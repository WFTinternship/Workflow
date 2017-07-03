package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.controller.utils.ControllerUtils;
import com.workfront.internship.workflow.service.CommentService;
import com.workfront.internship.workflow.service.PostService;
import org.springframework.web.bind.annotation.RequestMethod;
import com.workfront.internship.workflow.web.PageAttributes;
import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.Comment;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nane on 6/25/17
 */
@Controller
public class PostController {

    private List<Integer> sizeOfPostsBySameAppAreaID;

    private CommentService commentService;

    private PostService postService;

    private List<AppArea> appAreas;

    public PostController() {
    }

    @Autowired
    public PostController(PostService postService, CommentService commentService) {
        sizeOfPostsBySameAppAreaID = new ArrayList<>();
        this.postService = postService;
        appAreas = Arrays.asList(AppArea.values());
        this.commentService = commentService;
    }

    @RequestMapping(value = "/post/*", method = RequestMethod.GET)
    public ModelAndView post(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("post");

        String url = request.getRequestURL().toString();
        long id = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));

        Post post = postService.getById(id);
        request.setAttribute(PageAttributes.POST, post);

        List<Comment> postComments = commentService.getByPostId(id);
        request.setAttribute(PageAttributes.POSTCOMMENTS, postComments);

        List<Post> answers = postService.getAnswersByPostId(id);
        request.setAttribute(PageAttributes.ANSWERS, answers);

        List<Post> allPosts = new ArrayList<Post>(answers);
        allPosts.add(0, post);

        request.setAttribute(PageAttributes.NUMOFLIKES, ControllerUtils.getNumberOfLikes(allPosts, postService));

        for (Post answer : answers) {
            answer.setCommentList(commentService.getByPostId(answer.getId()));
        }

        for (AppArea appArea : appAreas) {
            sizeOfPostsBySameAppAreaID.add(postService.getByAppAreaId(appArea.getId()).size());
        }
        request.setAttribute(PageAttributes.POSTS_OF_APPAAREA, sizeOfPostsBySameAppAreaID);

        setAllPosts(modelAndView);

        return modelAndView;
    }

    @RequestMapping(value = "/new-post", method = RequestMethod.POST)
    public ModelAndView newPost(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("home");

        String title = request.getParameter(PageAttributes.TITLE);
        String content = request.getParameter(PageAttributes.POSTCONTENT);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(PageAttributes.USER);
        AppArea appArea = AppArea.getById(Integer.parseInt(request.getParameter(PageAttributes.APPAREA)));

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Post post = new Post();
        post.setTitle(title)
                .setAppArea(appArea)
                .setContent(content)
                .setUser(user)
                .setPostTime(timestamp);
        try {
            postService.add(post);
        } catch (RuntimeException e) {
            modelAndView.addObject(PageAttributes.MESSAGE,
                    "Sorry, your post was not added. Please try again");
            modelAndView.setViewName("new_post");
        }
        setAllPosts(modelAndView);
        return modelAndView;
    }

    @RequestMapping(value = {"/new-post"}, method = RequestMethod.GET)
    public ModelAndView newPost() {
        ModelAndView modelAndView = new ModelAndView("new_post");
        setAllPosts(modelAndView);
        return modelAndView;
    }

    @RequestMapping(value = {"/edit-post"}, method = RequestMethod.POST)
    public ModelAndView editPost(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("post");
        Post post = (Post) request.getAttribute(PageAttributes.POST);
        try {
            postService.update(post);
        }catch (RuntimeException e){
            modelAndView.addObject(PageAttributes.MESSAGE,
                    "Sorry, your post was not updated. Please try again");
            modelAndView.setViewName("post");
        }
        return modelAndView;
    }

    private void setAllPosts(ModelAndView modelAndView) {
        modelAndView.addObject(PageAttributes.APPAREAS, appAreas);
        List<Post> posts = postService.getAll();
        modelAndView.addObject(PageAttributes.ALLPOSTS, posts);
        modelAndView.addObject(PageAttributes.POSTS_OF_APPAAREA,sizeOfPostsBySameAppAreaID);
    }
}
