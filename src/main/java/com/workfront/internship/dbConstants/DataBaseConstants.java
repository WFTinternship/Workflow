package com.workfront.internship.dbConstants;

/**
 * Created by nane on 5/28/17.
 */
public class DataBaseConstants {

    public static class Post{
        public static String id = "id";
        public static String postId = "post_id";
        public static String userId = "user_id";
        public static String appAreaId = "apparea_id";
        public static String dateTime = "post_time";
        public static String title = "title";
        public static String content  = "content";
        public static String isCorrect  = "is_correct";
    }

    public static class User{
        public static String id = "id";
        public static String firstName = "first_name";
        public static String lastName = "last_name";
        public static String email = "email";
        public static String password = "passcode";
        public static String rating = "rating";
    }

    public static class Team {
        public static String id = "id";
        public static String name = "name";
        public static String office = "office";
    }

    public static class AppArea{
        public static String id = "id";
        public static String teamId= "team_id";
        public static String name = "name";
        public static String description = "description";
    }
}
