package com.workfront.internship.workflow.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
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

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private AppArea appArea;

    @ManyToMany(mappedBy = "likedPosts", targetEntity = User.class,
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

    @ManyToMany(mappedBy = "notifyPosts",
            cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<User> notificationRecepiants;

    public Post() {
        answerList = new ArrayList<>();
        commentList = new ArrayList<>();
        likers = new ArrayList<>();
        dislikers = new ArrayList<>();
    }

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

    public List<User> getNotificationRecepiants() {
        return notificationRecepiants;
    }

    public void setNotificationRecepiants(List<User> notificationRecepiants) {
        this.notificationRecepiants = notificationRecepiants;
    }

//    public void addLiker(User liker) {
//        if (getLikers() == null) {
//            setLikers(new ArrayList<>());
//        }
//        if (liker.getLikedPosts() == null) {
//            liker.setLikedPosts(new ArrayList<>());
//        }
//        if (!getLikers().contains(liker)) {
//            getLikers().add(liker);
//        }
//        if (!liker.getLikedPosts().contains(this)) {
//            liker.getLikedPosts().add(this);
//        }
//    }
//
//    public void addDisliker(User disliker) {
//        if (getDislikers() == null) {
//            setDislikers(new ArrayList<>());
//        }
//        if (disliker.getDislikedPosts() == null) {
//            disliker.setDislikedPosts(new ArrayList<>());
//        }
//        if (!getDislikers().contains(disliker)) {
//            getDislikers().add(disliker);
//        }
//        if (!disliker.getDislikedPosts().contains(this)) {
//            disliker.getDislikedPosts().add(this);
//        }
//    }

    public void addNotificationRecipient(User notificationRecipient) {
        if (getNotificationRecepiants() == null) {
            setNotificationRecepiants(new ArrayList<>());
        }
        if (notificationRecipient.getNotifyPosts() == null) {
            notificationRecipient.setNotifyPosts(new ArrayList<>());
        }
        if (!getNotificationRecepiants().contains(notificationRecipient)) {
            getNotificationRecepiants().add(notificationRecipient);
        }
        if (!notificationRecipient.getNotifyPosts().contains(this)) {
            notificationRecipient.getNotifyPosts().add(this);
        }
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