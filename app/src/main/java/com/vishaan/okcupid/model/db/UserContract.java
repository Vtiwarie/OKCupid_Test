package com.vishaan.okcupid.model.db;

/**
 * User table contract class to enforce certain restrictions on
 * the user table, such as predefined column names.
 */
public class UserContract {
    public static final String TABLE_NAME = "tbl_user";

    public static final class Columns {
        public static final String ENEMY = "enemy";
        public static final String RELATIVE = "relative";
        public static final String LAST_LOGIN = "last_login";
        public static final String GENDER = "gender";
        public static final String LOCATION = "location";
        public static final String USER_ID = "userid";
        public static final String MATCH = "match";
        public static final String GENDER_TAGS = "gender_tags";
        public static final String LIKED = "liked";
        public static final String ORIENTATION = "orientation";
        public static final String PHOTO = "photo";
        public static final String AGE = "age";
        public static final String FRIEND = "friend";
        public static final String IS_ONLINE = "is_online";
        public static final String USERNAME = "username";
        public static final String STOPLIGHT_COLOR = "stoplight_color";
        public static final String LAST_CONTACT_TIME = "last_contact_time";
        public static final String ORIENTATION_TAGS = "orientation_tags";
    }
}
