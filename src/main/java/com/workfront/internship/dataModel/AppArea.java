package com.workfront.internship.dataModel;


public enum  AppArea {
    REPORTING(1, "Reporting", "Some rep description", "Team1");

    AppArea(long id, String name, String description, String teamName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.teamName = teamName;
    }

    private long id;
    private String name;
    private String description;
    private String teamName;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTeamName() {
        return teamName;
    }

    public static AppArea getById(long id){
        AppArea [] appAreas = AppArea.values();
        for (AppArea appArea: appAreas) {
            if(appArea.getId() == id)
                return appArea;
        }
        return null;
    }
}
