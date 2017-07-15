package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.dao.util.DAOUtil;
import com.workfront.internship.workflow.entity.Comment;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.exceptions.service.InvalidObjectException;
import com.workfront.internship.workflow.service.CommentService;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.util.DaoTestUtil;
import com.workfront.internship.workflow.web.PageAttributes;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.sql.Timestamp;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by Angel on 7/13/2017
 */
public class CommentControllerUnitTest {
    @Mock
    private CommentService commentService;

    private PostService postService;
    private HttpServletRequest testRequest;
    private HttpSession testSession;

    @InjectMocks
    private CommentController commentController;
    private MockMvc mockMvc;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();

    }
    @Test
    public void newComment_notValidComment() {
        Comment comment = DaoTestUtil.getRandomComment();
        comment.setContent(null);
        commentService.add(comment);

        when(commentService.add(comment))
                .thenThrow(new InvalidObjectException("not valid comment"));

    }
    @Test
    public void addNewComment_success() throws Exception {
        User user = (User) testSession.getAttribute(PageAttributes.USER);
        Post post = (Post) testSession.getAttribute(PageAttributes.POST);
        String content = (String) testSession.getAttribute(PageAttributes.COMMENTCONTENT);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Comment comment = new Comment();
        comment.setUser(user).setPost(post).setContent(content).setCommentTime(timestamp);

        Long comment_id = commentService.add(comment);
        when(commentService.add(comment)).thenReturn(comment_id);

        Long postId = comment.getPost().getId();

            this.mockMvc.perform(post("/new-comment/" + postId))
                    .andExpect(model().attribute(PageAttributes.COMMENTCONTENT, comment.getContent()))
                    .andExpect(model().attribute(PageAttributes.USER, comment.getUser()))
                    .andExpect(model().attribute(PageAttributes.POST, comment.getPost()))
                    .andExpect(status().isOk());

    }
}
