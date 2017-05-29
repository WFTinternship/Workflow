package com.workfront.internship.dataModel;

/**
 * Created by Karen on 5/26/2017.
 */
public class Team {
    private long id;
    private String name;
    private String office;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }
}
