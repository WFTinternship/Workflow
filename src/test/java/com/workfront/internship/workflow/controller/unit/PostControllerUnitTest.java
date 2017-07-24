package com.workfront.internship.workflow.controller.unit;

import com.workfront.internship.workflow.controller.PostController;
import com.workfront.internship.workflow.entity.Comment;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.service.CommentService;
import com.workfront.internship.workflow.service.UserService;
import com.workfront.internship.workflow.service.impl.PostServiceImpl;
import com.workfront.internship.workflow.util.DaoTestUtil;
import com.workfront.internship.workflow.controller.PageAttributes;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Angel on 7/17/2017
 */
public class PostControllerUnitTest extends BaseUnitTest {

    @InjectMocks
    private PostController postController;

    @Mock
    private PostServiceImpl postServiceMock;

    @Mock
    private UserService userServiceMock;

    @Mock
    private CommentService commentServiceMock;

    private Post post;
    private User user;
    private List<Post> likedPosts;
    private List<Post> dislikedPosts;
    private List<Comment> postComments;
    private List<Post> answers;

    @Before
    public void init() {
        post = DaoTestUtil.getRandomPost();
        user = DaoTestUtil.getRandomUser();
        post.setUser(user);

        likedPosts = new ArrayList<>();
        dislikedPosts = new ArrayList<>();
        postComments = new ArrayList<>();
        answers = new ArrayList<>();

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(postController)
                .build();
    }

    /**
     * @see PostController#post(HttpServletRequest)
     */
    @Test
    public void post() throws Exception {
        Long postId = 1L;

        String url = "/post/" + postId;
        doReturn(post).when(postServiceMock).getById(anyLong());

        doReturn(likedPosts).when(userServiceMock).getLikedPosts(anyLong());
        doReturn(dislikedPosts).when(userServiceMock).getDislikedPosts(anyLong());
        doReturn(postComments).when(commentServiceMock).getByPostId(anyLong());
        doReturn(answers).when(postServiceMock).getAnswersByPostId(anyLong());

        mockMvc.perform(get(url)
                .sessionAttr(PageAttributes.USER, user))
                .andExpect(view().name("post"))
                .andExpect(model().attribute(PageAttributes.POST, post))
                .andExpect(model().attribute(PageAttributes.POST_COMMENTS, postComments))
                .andExpect(model().attribute(PageAttributes.ANSWERS, answers))
                .andExpect(model().attribute(PageAttributes.LIKED_POSTS, likedPosts))
                .andExpect(model().attribute(PageAttributes.DISLIKED_POSTS, dislikedPosts))
                .andExpect(status().isOk());
    }

    /**
     * @see PostController#newPost()
     */
    @Test
    public void newPost() throws Exception {
        String appArea = "7";
        String title = "title";
        String content = "content";
        String notify = "off";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/new-post")
                .param(PageAttributes.TITLE, title)
                .param(PageAttributes.POST_CONTENT, content)
                .param(PageAttributes.APPAREA, appArea)
                .param(PageAttributes.NOTE, notify)
                .sessionAttr(PageAttributes.USER, user))
                .andExpect(view().name("redirect:/home"));
    }

    /**
     * @see PostController#newPost()
     */
    @Test
    public void newPost_RuntimeException() throws Exception {

        String appArea = "7";
        String title = "title";
        String content = "content";
        String notify = "off";

        doThrow(RuntimeException.class).when(postServiceMock).add(post);

        mockMvc.perform(MockMvcRequestBuilders.post("/new-post")
                .param(PageAttributes.TITLE, title)
                .param(PageAttributes.POST_CONTENT, content)
                .param(PageAttributes.APPAREA, appArea)
                .param(PageAttributes.NOTE, notify)
                .sessionAttr(PageAttributes.USER, user))
                .andExpect(view().name("new_post"))
                .andExpect(model().attribute(PageAttributes.MESSAGE,
                        "Sorry, your post was not added. Please try again"));
    }
}
