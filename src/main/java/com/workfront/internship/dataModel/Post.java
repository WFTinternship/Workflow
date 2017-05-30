package com.workfront.internship.dataModel;

import java.sql.Timestamp;

/**
 * Created by nane on 5/26/17.
 */
public class Post {
    private long id;
    private Post post;
    private User user;
    private AppArea appArea;
    private Timestamp postTime;
    private String title;
    private String content;
    private boolean isCorrect;

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

    public boolean isCorrect() {
        return isCorrect;
    }

    public Post setCorrect(boolean correct) {
        isCorrect = correct;
        return this;
    }
}
