package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.web.PageAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Vahag on 7/2/2017
 */
@RestController
public class PostRestController {

    private PostService postService;

    @Autowired
    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @RequestMapping(value = "/like/*", method = RequestMethod.POST)
    public ResponseEntity like(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        long postId = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));

        User user = (User) request.getSession().getAttribute(PageAttributes.USER);

        try {
            postService.like(user.getId(), postId);
        } catch (RuntimeException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(String.valueOf(postService.getLikesNumber(postId)));
    }

    @RequestMapping(value = "/dislike/*", method = RequestMethod.POST)
    public ResponseEntity<?> dislike(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        long postId = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));

        User user = (User) request.getSession().getAttribute(PageAttributes.USER);

        try {
            postService.dislike(user.getId(), postId);
        } catch (RuntimeException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(String.valueOf(postService.getDislikesNumber(postId)));
    }

}
