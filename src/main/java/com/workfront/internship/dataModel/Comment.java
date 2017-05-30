package com.workfront.internship.dataModel;


import java.util.Date;

/**
 * Created by Karen on 5/26/2017.
 */
public class Comment {
    private long id;
    private User user;
    private Post post;
    private String content;
    private Date commentTime;

    public long getId() {
        return id;
    }

    public Comment setId(long id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Comment setUser(User user) {
        this.user = user;
        return this;
    }

    public Post getPost() {
        return post;
    }

    public Comment setPost(Post post) {
        this.post = post;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Comment setContent(String content) {
        this.content = content;
        return this;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public Comment setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
        return this;
    }
}
