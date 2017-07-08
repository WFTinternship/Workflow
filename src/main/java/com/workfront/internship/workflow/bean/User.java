package com.workfront.internship.workflow.bean;

/**
 * Created by nane on 7/5/17
 */
public class User {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
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
