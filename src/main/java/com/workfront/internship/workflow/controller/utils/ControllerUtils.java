package com.workfront.internship.workflow.controller.utils;

import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.exceptions.service.ServiceLayerException;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.web.PageAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * Created by nane on 7/1/17
 */
public class ControllerUtils {


    private static final int NUMBER_OF_POST_PER_PAGE = 5;
    private static List<AppArea> appAreas = new ArrayList<>(Arrays.asList(AppArea.values()));

    public static List<Integer> getNumberOfPostsForAppArea(List<AppArea> appAreas, PostService postService) {
        List<Integer> sizeOfPostsBySameAppAreaID = new ArrayList<>();
        // getting and passing list of sizes of each posts by same appArea id to home page
        try {
            for (AppArea appArea : appAreas) {
                sizeOfPostsBySameAppAreaID.add(postService.getByAppAreaId(appArea.getId()).size());
            }
        } catch (ServiceLayerException e) {
            return null;
        }
        return sizeOfPostsBySameAppAreaID;
    }


    public static List<Integer> getNumberOfAnswers(List<Post> postList, PostService postService) {
        List<Integer> numbersOfAnswersForPosts = new ArrayList<>();
        // getting and passing list of sizes of each posts by same appArea id to home page
        try {
            for (Post post : postList) {
                numbersOfAnswersForPosts.add(postService.getNumberOfAnswers(post.getId()));
            }
        } catch (ServiceLayerException e) {
            return null;
        }
        return numbersOfAnswersForPosts;
    }

    public static List<Long> getNumberOfLikes(List<Post> postList, PostService postService) {
        List<Long> numbersOfLikesForPosts = new ArrayList<>();
        try {
            for (Post post : postList) {
                numbersOfLikesForPosts.add(postService.getLikesNumber(post.getId()));
            }
        } catch (ServiceLayerException e) {
            return numbersOfLikesForPosts;
        }
        return numbersOfLikesForPosts;
    }

    public static List<Long> getNumberOfDislikes(List<Post> postList, PostService postService) {
        List<Long> numbersOfDislikesForPosts = new ArrayList<>();
        try {
            for (Post post : postList) {
                numbersOfDislikesForPosts.add(postService.getDislikesNumber(post.getId()));
            }
        } catch (ServiceLayerException e) {
            return numbersOfDislikesForPosts;
        }
        return numbersOfDislikesForPosts;
    }

    public static List<Long> getDifOfLikesDislikes(List<Post> postList, PostService postService) {
        List<Long> numbersOfDislikesForPosts = new ArrayList<>();
        try {
            for (Post post : postList) {
                numbersOfDislikesForPosts.
                        add(postService.getLikesNumber(post.getId()) - postService.getDislikesNumber(post.getId()));
            }
        } catch (ServiceLayerException e) {
            return numbersOfDislikesForPosts;
        }
        return numbersOfDislikesForPosts;
    }

    public static List<Post> getMostDiscussedPosts(PostService postService, List<Post> posts) {
        List<Post> mostDiscussedPosts = new ArrayList<>(posts);

        try {
            mostDiscussedPosts.sort(Comparator.comparing(post ->
                    -postService.getAnswersByPostId(post.getId()).size()));
            return mostDiscussedPosts;
        } catch (ServiceLayerException e) {
            return mostDiscussedPosts;
        }
    }

    public static List<Post> getTopPosts(PostService postService, List<Post> posts) {
        List<Post> topPosts = new ArrayList<>(posts);

        try {
            topPosts.sort(Comparator.comparing(post ->
                    postService.getDislikesNumber(post.getId()) - postService.getLikesNumber(post.getId())));
            return topPosts;
        } catch (ServiceLayerException e) {
            return topPosts;
        }
    }

    public static void setDefaultAttributes(PostService postService, ModelAndView modelAndView) {
        List<Post> allPosts = postService.getAll();
        modelAndView.addObject(PageAttributes.ALLPOSTS, allPosts);
        setDefaultAttributes(postService, allPosts, modelAndView);
    }

    public static void setDefaultAttributes(PostService postService, List<Post> posts, ModelAndView modelAndView) {
        List<Post> topPosts = getTopPosts(postService, posts);
        List<Post> mostDiscussedPosts = getMostDiscussedPosts(postService, posts);

        if (mostDiscussedPosts.size() > 5) {
            mostDiscussedPosts = mostDiscussedPosts.subList(0, 4);
        }

        if (topPosts.size() > 5) {
            topPosts = topPosts.subList(0, 4);
        }

        modelAndView
                .addObject(PageAttributes.NUMOFANSWERS,
                        getNumberOfAnswers(posts, postService))
                .addObject(PageAttributes.NUMOFANSWERSFORMDP,
                        getNumberOfAnswers(mostDiscussedPosts, postService))
                .addObject(PageAttributes.DIFOFLIKESDISLIKES,
                        getDifOfLikesDislikes(topPosts, postService))
                .addObject(PageAttributes.APPAREAS, appAreas)
                .addObject(PageAttributes.POSTS_OF_APPAAREA, getNumberOfPostsForAppArea(appAreas, postService))
                .addObject(PageAttributes.TOPPOSTS, topPosts)
                .addObject(PageAttributes.MOSTDISCUSSEDPOSTS, mostDiscussedPosts);
    }

    public static List<Post> getPostsByPage(List<Post> allPosts, int page) {
        List<Post> posts;
        int total = allPosts.size();

        if (page == 1) {
            posts = total < NUMBER_OF_POST_PER_PAGE ?
                    allPosts.subList(0, total) : allPosts.subList(0, NUMBER_OF_POST_PER_PAGE);
        } else {
            int start = (page - 1) * NUMBER_OF_POST_PER_PAGE;
            int end = start + 5;
            posts = total < end ?
                    allPosts.subList(start, total) : allPosts.subList(start, end);
        }
        return posts;
    }

    public static List<Post> getFirstPagePosts(List<Post> allPosts){
        return getPostsByPage(allPosts, 1);
    }

}
