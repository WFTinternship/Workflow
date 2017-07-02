package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.service.PostService;
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
        long id = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));
        Post post;
        try {
            postService.like(id);
            post = postService.getById(id);
        } catch (RuntimeException e) {
            return (ResponseEntity) ResponseEntity.badRequest();
        }
        return ResponseEntity.ok(String.valueOf(post.getLikesNumber()));
    }

    @RequestMapping(value = "/dislike/*", method = RequestMethod.POST)
    public ResponseEntity dislike(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        long id = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));

        Post post;
        try {
            postService.dislike(id);
            post = postService.getById(id);
        } catch (RuntimeException e) {
            return (ResponseEntity) ResponseEntity.badRequest();
        }

        return ResponseEntity.ok(String.valueOf(post.getDislikesNumber()));
    }

}
