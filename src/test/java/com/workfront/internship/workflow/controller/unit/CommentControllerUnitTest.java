package com.workfront.internship.workflow.controller.unit;

import com.workfront.internship.workflow.controller.CommentController;
import com.workfront.internship.workflow.entity.Comment;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.exceptions.service.InvalidObjectException;
import com.workfront.internship.workflow.service.CommentService;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.service.UserService;
import com.workfront.internship.workflow.util.DaoTestUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;




import java.sql.Timestamp;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by Angel on 7/13/2017
 */
public class CommentControllerUnitTest extends BaseUnitTest {

    @InjectMocks
    private CommentController commentController;

    @Mock
    private CommentService commentService;

    @Mock
    private PostService postService;

    @Mock
    private UserService userService;


    private Post post;
    private Comment comment;
    private User user;

    @Before
    public void init() {
        user = DaoTestUtil.getRandomUser();

        post = DaoTestUtil.getRandomPost();
        comment = DaoTestUtil.getRandomComment(user, post);

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
    public void newComment_success() throws Exception{
        userService.add(post.getUser());

        postService.add(post);

        userService.add(user);

        String content = "content";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Comment comment = new Comment();
        comment.setUser(user)
                .setPost(post)
                .setContent(content)
                .setCommentTime(timestamp);
        commentService.add(comment);

        Long postId = comment.getPost().getId();

        doReturn(post).when(postService).getById(anyInt());

        mockMvc.perform(post("/new-comment/" + postId))
                .andExpect(view().name("redirect:/post/" + postId));

    }
}
