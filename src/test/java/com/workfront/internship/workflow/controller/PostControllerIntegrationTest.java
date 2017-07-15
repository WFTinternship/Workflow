package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.controller.utils.ControllerUtils;
import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.Comment;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.service.CommentService;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.service.UserService;
import com.workfront.internship.workflow.util.DaoTestUtil;
import com.workfront.internship.workflow.web.PageAttributes;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by nane on 7/15/17
 */
public class PostControllerIntegrationTest extends BaseControllerTest {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;
    private User user;

    @Autowired
    @Qualifier("postServiceImpl")
    private PostService postService;

    @Autowired
    @Qualifier("commentServiceImpl")
    private CommentService commentService;

    private Post post;
    private AppArea appArea;
    private List<User> userList;
    private List<Post> allPosts;
    private List<Post> answers;
    private List<Comment> postComments;
    private List<AppArea> appAreas;

    @Before
    public void setUp() {
        userList = new ArrayList<>();
        appArea = AppArea.values()[0];

        allPosts = postService.getAll();
        appAreas = Arrays.asList(AppArea.values());

        user = DaoTestUtil.getRandomUser();
        userList.add(user);
        userService.add(user);

        post = DaoTestUtil.getRandomPost(user, appArea);
    }


    @Test
    public void post() throws Exception {

        long id = postService.add(post);

        postComments = commentService.getByPostId(post.getId());
        answers = postService.getAnswersByPostId(post.getId());

        List<Post> allPosts = new ArrayList<>(answers);
        allPosts.add(0, post);

        mockMvc.perform(get("/post/" + id))
                .andExpect(view().name("post"))
                .andExpect(model().attribute(PageAttributes.POST, post))
                .andExpect(model().attribute(PageAttributes.POSTCOMMENTS, postComments))
                .andExpect(model().attribute(PageAttributes.ANSWERS, answers))
                .andExpect(model().attribute(PageAttributes.POST, post))
                .andExpect(model().attribute(PageAttributes.NUMOFLIKES,
                        ControllerUtils.getNumberOfLikes(allPosts, postService)))
                .andExpect(model().attribute(PageAttributes.NUMOFDISLIKES,
                        ControllerUtils.getNumberOfDislikes(allPosts, postService)))
                .andExpect(model().attribute(PageAttributes.APPAREAS, appAreas))
                .andExpect(model().attribute(PageAttributes.POSTS_OF_APPAAREA,
                        ControllerUtils.getNumberOfPostsForAppArea(appAreas, postService)))
                .andExpect(status().isOk());
    }


    @Test
    public void editPost() throws Exception {
    }

    @Test
    public void newPost_get() throws Exception {
        allPosts = postService.getAll();

        mockMvc.perform(get("/new-post"))
                .andExpect(view().name("new_post"))
                .andExpect(model().attribute(PageAttributes.APPAREAS, appAreas))
                .andExpect(model().attribute(PageAttributes.POSTS_OF_APPAAREA,
                        ControllerUtils.getNumberOfPostsForAppArea(appAreas, postService)))
                .andExpect(model().attribute(PageAttributes.NUMOFANSWERS, ControllerUtils.getNumberOfAnswers(allPosts, postService)))
                .andExpect(model().attribute(PageAttributes.ALLPOSTS, allPosts))
                .andExpect(model().attribute(PageAttributes.NUMOFANSWERS,
                        ControllerUtils.getNumberOfAnswers(allPosts, postService)))
                .andExpect(status().isOk());
    }

    @Test
    public void newPost_post() throws Exception {
        allPosts = postService.getAll();

        mockMvc.perform(MockMvcRequestBuilders.post("/new-post")
                .requestAttr(PageAttributes.TITLE, "A title")
                .requestAttr(PageAttributes.POSTCONTENT, "Some content")
                .requestAttr(PageAttributes.NOTE, "off")
                .requestAttr(PageAttributes.APPAREA, AppArea.AGILE)
                .sessionAttr(PageAttributes.USER, user))
                .andExpect(view().name("home"))
                .andExpect(model().attribute(PageAttributes.APPAREAS, appAreas))
                .andExpect(model().attribute(PageAttributes.POSTS_OF_APPAAREA,
                        ControllerUtils.getNumberOfPostsForAppArea(appAreas, postService)))
                .andExpect(model().attribute(PageAttributes.NUMOFANSWERS, ControllerUtils.getNumberOfAnswers(allPosts, postService)))
                .andExpect(model().attribute(PageAttributes.ALLPOSTS, allPosts))
                .andExpect(status().isOk());
    }

}

