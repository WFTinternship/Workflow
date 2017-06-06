package com.workfront.internship.workflow.dataModel;


import java.sql.Timestamp;


public class Comment {
    private long id;
    private User user;
    private Post post;
    private String content;
    private Timestamp commentTime;

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

    public Timestamp getCommentTime() {
        return commentTime;
    }

    public Comment setCommentTime(Timestamp commentTime) {
        this.commentTime = commentTime;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        return this == o || (o instanceof Comment) && id == ((Comment) o).id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }


    public  boolean isValid(){
        return this != null
                && this.getUser().isValid()
                && this.getPost().isValid()
                && !isEmpty(this.getContent());
    }
    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
