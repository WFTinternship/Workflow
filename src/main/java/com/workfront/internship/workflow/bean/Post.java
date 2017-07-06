package com.workfront.internship.workflow.bean;

import com.workfront.internship.workflow.entity.AppArea;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by nane on 7/5/17
 */
public class Post {

    private long id;
    private Post post;
    private User user;
    private AppArea appArea;
    private Timestamp postTime;
    private String title;
    private String content;



    private List<Comment> commentList;
    private boolean isCorrect;

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

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

    public boolean isCorrect() {
        return isCorrect;
    }

    public Post setCorrect(boolean correct) {
        isCorrect = correct;
        return this;
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
                && !isEmpty(this.getTitle())
                && !isEmpty(this.getContent())
                && this.postTime != null
                && this.getAppArea() != null;
    }
}
