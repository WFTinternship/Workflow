package com.workfront.internship.workflow.entity;

import com.workfront.internship.workflow.entity.converter.AppAreaConverter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "apparea")
public class AppArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Convert(converter = AppAreaConverter.class)
    private AppAreaEnum appAreaEnum;

    @OneToMany(mappedBy = "appArea")
    private List<Post> posts;

    @ManyToMany(mappedBy = "appAreas", cascade = {CascadeType.ALL})
    private List<User> users;

    public void setAppAreaEnum(AppAreaEnum appAreaEnum) {
        this.appAreaEnum = appAreaEnum;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public AppAreaEnum getAppAreaEnum() {
        return appAreaEnum;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public long getId() {
        return appAreaEnum.getId();
    }

    public String getName() {
        return appAreaEnum.getName();
    }

    public String getDescription() {
        return appAreaEnum.getDescription();
    }

    public String getTeamName() {
        return appAreaEnum.getTeamName();
    }

    public static AppAreaEnum getByName(String name) {
        AppAreaEnum[] appAreas = AppAreaEnum.values();
        for (AppAreaEnum appArea : appAreas) {
            if (appArea.getName().equals(name))
                return appArea;
        }
        return null;
    }

    public static AppArea getById(long id) {
        AppArea appArea = new AppArea();
        appArea.setAppAreaEnum(AppAreaEnum.getById(id));
        return appArea;
    }

    public static AppArea[] values() {
        int length = AppAreaEnum.values().length;
        AppArea[] appAreas = new AppArea[length];
        for (int i = 0; i < length; i++) {
            AppArea appArea = new AppArea();
            appArea.setAppAreaEnum(AppAreaEnum.values()[i]);
            appAreas[i] = appArea;
        }
        return appAreas;
    }

    public boolean isValid() {
        return !isEmpty(this.getName())
                && !isEmpty(this.getDescription())
                && !isEmpty(this.getTeamName());
    }

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

}
