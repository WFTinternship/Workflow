package com.workfront.internship.dataModel;

/**
 * Created by Karen on 5/26/2017.
 */
public class User {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
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

    public int getRating() {
        return rating;
    }

    public User setRating(int rating) {
        this.rating = rating;
        return this;
    }
}
