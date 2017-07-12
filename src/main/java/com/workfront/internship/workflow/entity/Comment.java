package com.workfront.internship.workflow.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    @Column(name = "content", length = 1000, nullable = false)
    private String content;

    @Column(name = "comment_time", nullable = false)
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
        return getUser() != null
                && getUser().isValid()
                && getPost() != null
                && getPost().isValid()
                && !isEmpty(content)
                && commentTime != null;
    }
    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
