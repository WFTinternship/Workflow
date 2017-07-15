package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.service.UserService;
import com.workfront.internship.workflow.util.DaoTestUtil;
import com.workfront.internship.workflow.web.PageAttributes;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private Post post;

    private AppArea appArea;
    private List<User> userList;
    private List<Post> allPosts;

    @Before
    public void setUp() {
        userList = new ArrayList<>();
        appArea = AppArea.values()[0];

        user = DaoTestUtil.getRandomUser();
        userList.add(user);
        userService.add(user);

        post = DaoTestUtil.getRandomPost(user, appArea);
    }


    @Test
    public void post() {
        try {
            long id = postService.add(post);
            allPosts = postService.getAll();

            mockMvc.perform(get("/post/" + id))
                    .andExpect(view().name("post"))
                    .andExpect(model().attribute(PageAttributes.ALLPOSTS, allPosts))
                    .andExpect(status().isOk());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

