package com.workfront.internship.workflow.entity;


import javax.persistence.*;
import java.util.List;

public class User {

    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private List<AppArea> appAreas;

    private List<Post> posts;

    private List<Comment> comments;

    private String avatarURL;

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
