package com.workfront.internship.workflow.entity;


import javax.persistence.*;
import java.util.List;

@Entity(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "passcode")
    private String password;

    @ElementCollection(targetClass = AppArea.class)
    @CollectionTable(name = "user_apparea",
            joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "apparea_id")
    private List<AppArea> appAreas;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "user_post_likes", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id"))
    private List<Post> likedPosts;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "user_post_dislikes", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id"))
    private List<Post> dislikedPosts;

    @Column(name = "avatar_url")
    private String avatarURL;

    @Column(name = "rating")
    private int rating;

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

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
