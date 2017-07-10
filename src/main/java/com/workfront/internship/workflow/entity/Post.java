package com.workfront.internship.workflow.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by nane on 5/26/17
 */

@Entity(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Enumerated(EnumType.STRING)
    private AppArea appArea;

    @ManyToMany(mappedBy = "likedPosts",
            cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<User> likers;

    @ManyToMany(mappedBy = "dislikedPosts",
            cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<User> dislikers;

    @Column(name = "post_time", nullable = false)
    private Timestamp postTime;

    @Column(name = "title", length = 45, nullable = false)
    private String title;

    @Column(name = "content", length = 1000, nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Post> answerList;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> commentList;

    @OneToOne
    private Post bestAnswer;

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

    public List<User> getLikers() {
        return likers;
    }

    public Post setLikers(List<User> likers) {
        this.likers = likers;
        return this;
    }

    public List<User> getDislikers() {
        return dislikers;
    }

    public Post setDislikers(List<User> dislikers) {
        this.dislikers = dislikers;
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

    public List<User> getLikers() {
        return likers;
    }

    public void setLikers(List<User> likers) {
        this.likers = likers;
    }

    public List<User> getDislikers() {
        return dislikers;
    }

    public void setDislikers(List<User> dislikers) {
        this.dislikers = dislikers;
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