package dataModel;

/**
 * Created by nane on 5/26/17.
 */
public enum  AppArea {
    REPORTING(1, "Reporting", "Some rep description", new Team());

    private AppArea(long id, String name, String description, Team team) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.team = team;
    }

    private long id;
    private String name;
    private String description;
    private Team team;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Team getTeam() {
        return team;
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
