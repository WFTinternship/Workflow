package com.workfront.internship.dataModel;

/**
 * Created by nane on 5/26/17.
 */
public class Post {
    private long id;
    private Post post;
    private User user;
    private AppArea appArea;
    private String postTime;
    private String title;
    private String content;
    private boolean isCorrect;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AppArea getAppArea() {
        return appArea;
    }

    public void setAppArea(AppArea appArea) {
        this.appArea = appArea;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
