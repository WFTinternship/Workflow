package com.workfront.internship.workflow.entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "passcode", nullable = false)
    private String password;

    @ElementCollection(targetClass = AppArea.class)
    @CollectionTable(name = "user_apparea",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "apparea_id"})})
    @Column(name = "apparea_id", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<AppArea> appAreas;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    private List<Comment> comments;

    @ManyToMany(targetEntity = Post.class)
    @JoinTable(name = "user_post_likes",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "post_id"})})
    private List<Post> likedPosts;

    @ManyToMany(targetEntity = Post.class)
    @JoinTable(name = "user_post_dislikes",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "post_id"})})
    private List<Post> dislikedPosts;

    @ManyToMany(targetEntity = Post.class)
    @JoinTable(name = "notifications",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false))
    private List<Post> notifyPosts;

    @Column(name = "avatar_url", nullable = false)
    private String avatarURL;

    @Column(name = "rating")
    private int rating;

    public User() {
        appAreas = new ArrayList<>();
        posts = new ArrayList<>();
        comments = new ArrayList<>();
        likedPosts = new ArrayList<>();
        dislikedPosts = new ArrayList<>();
        notifyPosts = new ArrayList<>();
    }

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public long getId() {
        return id;
    }

    public User setId(long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public List<AppArea> getAppAreas() {
        return appAreas;
    }

    public User setAppAreas(List<AppArea> appAreas) {
        this.appAreas = appAreas;
        return this;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public User setPosts(List<Post> posts) {
        this.posts = posts;
        return this;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public User setComments(List<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public List<Post> getLikedPosts() {
        return likedPosts;
    }

    public User setLikedPosts(List<Post> likedPosts) {
        this.likedPosts = likedPosts;
        return this;
    }

    public List<Post> getDislikedPosts() {
        return dislikedPosts;
    }

    public User setDislikedPosts(List<Post> dislikedPosts) {
        this.dislikedPosts = dislikedPosts;
        return this;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public User setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
        return this;
    }

    public int getRating() {
        return rating;
    }

    public User setRating(int rating) {
        this.rating = rating;
        return this;
    }

    public List<Post> getNotifyPosts() {
        return notifyPosts;
    }

    public void setNotifyPosts(List<Post> notifyPosts) {
        this.notifyPosts = notifyPosts;
    }

    @Override
    public boolean equals(Object o) {
        return this == o || (o instanceof User) && id == ((User) o).id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public boolean isValid() {
        return !isEmpty(this.getFirstName())
                && !isEmpty(this.getLastName())
                && !isEmpty(this.getEmail())
                && !isEmpty(this.getPassword());
    }
}
