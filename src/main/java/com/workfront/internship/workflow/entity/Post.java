package com.workfront.internship.workflow.entity;

import com.workfront.internship.workflow.util.StringUtils;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by nane on 5/26/17
 */

public class Post {

    private long id;

    private Post post;

    private User user;

    private AppArea appArea;

    private Timestamp postTime;

    private String title;

    private String content;

    private List<Post> answerList;

    private List<Comment> commentList;

    private Post bestAnswer;

    public long getId() {
        return id;
    }

    public Post setId(long id) {
        this.id = id;
        return this;
    }

    public Post getPost() {
        return post;
    }

    public Post setPost(Post post) {
        this.post = post;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Post setUser(User user) {
        this.user = user;
        return this;
    }

    public AppArea getAppArea() {
        return appArea;
    }

    public Post setAppArea(AppArea appArea) {
        this.appArea = appArea;
        return this;
    }

    public Timestamp getPostTime() {
        return postTime;
    }

    public Post setPostTime(Timestamp postTime) {
        this.postTime = postTime;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Post setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Post setContent(String content) {
        this.content = content;
        return this;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public Post setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
        return this;
    }

    public List<Post> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Post> answerList) {
        this.answerList = answerList;
    }

    public Post getBestAnswer() {
        return bestAnswer;
    }

    public void setBestAnswer(Post bestAnswer) {
        this.bestAnswer = bestAnswer;
    }

    @Override
    public boolean equals(Object o) {
        return this == o || (o instanceof Post) && id == ((Post) o).id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public boolean isValid() {
        return user != null
                && this.getUser().isValid()
                && !StringUtils.isEmpty(this.getTitle())
                && !StringUtils.isEmpty(this.getContent())
                && this.postTime != null
                && this.getAppArea() != null;
    }
}