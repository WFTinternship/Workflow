package com.workfront.internship.workflow.controller.utils;

import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.service.PostService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nane on 7/1/17
 */
public class ControllerUtils {

    private static List<AppArea> appAreas = new ArrayList<>(Arrays.asList(AppArea.values()));

    public static List<Integer> getNumberOfPostsForAppArea(List<AppArea> appAreas, PostService postService) {
        List<Integer> sizeOfPostsBySameAppAreaID = new ArrayList<>();
        // getting and passing list of sizes of each posts by same appArea id to home page
        for (AppArea appArea : appAreas) {
            sizeOfPostsBySameAppAreaID.add(postService.getByAppAreaId(appArea.getId()).size());
        }
        return sizeOfPostsBySameAppAreaID;
    }

    public static List<Integer> getNumberOfAnswers(List<Post> postList, PostService postService) {
        List<Integer> numbersOfAnswersForPosts = new ArrayList<>();
        // getting and passing list of sizes of each posts by same appArea id to home page
        for (Post post : postList) {
            numbersOfAnswersForPosts.add(postService.getAnswersByPostId(post.getId()).size());
        }
        return numbersOfAnswersForPosts;
    }

    public static List<Long> getNumberOfLikes(List<Post> postList, PostService postService) {
        List<Long> numbersOfLikesForPosts = new ArrayList<>();
        // getting and passing list of sizes of each posts by same appArea id to home page
        for (Post post : postList) {
            numbersOfLikesForPosts.add(postService.getLikesNumber(post.getId()));
        }
        return numbersOfLikesForPosts;
    }

    public static List<Long> getNumberOfDislikes(List<Post> postList, PostService postService) {
        List<Long> numbersOfDislikesForPosts = new ArrayList<>();
        // getting and passing list of sizes of each posts by same appArea id to home page
        for (Post post : postList) {
            numbersOfDislikesForPosts.add(postService.getDislikesNumber(post.getId()));
        }
        return numbersOfDislikesForPosts;
    }

    public static List<Post> getTopPosts(PostService postService, List<Post> posts){
        List<Post> topPosts = new ArrayList<>();
        for (Post post: posts) {
            if(postService.getAnswersByPostId(post.getId()).size()  > 4){
                topPosts.add(post);
            }
        }
        return topPosts;
    }

//    public static void setAppAreas(ModelAndView modelAndView) {
//        modelAndView.addObject(PageAttributes.APPAREAS, appAreas);
//
//    }
}
