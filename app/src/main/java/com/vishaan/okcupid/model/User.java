package com.vishaan.okcupid.model;

import java.util.List;

/**
 * User class
 */
public class User {
    int enemy;
    long relative;
    int last_login;
    int gender;
    Location location;
    String userid;
    int match;
    List<String> gender_tags;
    boolean liked;
    String state_code;
    int orientation;
    String country_name;
    Photo photo;
    String state_name;
    int age;
    String country_code;
    int friend;
    int is_online;
    String username;
    String city_name;
    String stoplight_color;
    List<Integer> last_contact_time;
    List<String> orientation_tags;

    public static class Location {
        String country_code;
        String city_name;
        String country_name;
        String state_name;
        String state_code;
    }

    public static class Photo {
        Full_Paths full_paths;
        String base_path;
        Original_Size original_size;
        Crop_Rect crop_rect;
        Thumb_Paths thumb_paths;
        int ordinal;
        String id;
        String caption;

        public static class Full_Paths {
            String large;
            String small;
            String medium;
            String original;
        }

        public static class Original_Size {
            int width;
            int height;
        }

        public static class Crop_Rect {
            int height;
            int y;
            int width;
            int x;
        }

        public static class Thumb_Paths {
            String large;
            String desktop_match;
            String small;
            String medium;
        }
    }

    public int getAge() {
        return age;
    }

    public String getCityName() {
        return city_name;
    }

    public String getCountryCode() {
        return country_code;
    }

    public String getCountryName() {
        return country_name;
    }

    public int getEnemy() {
        return enemy;
    }

    public int getFriend() {
        return friend;
    }

    public int getGender() {
        return gender;
    }

    public List<String> getGenderTags() {
        return gender_tags;
    }

    public int getIsOnline() {
        return is_online;
    }

    public List<Integer> getLastContactTime() {
        return last_contact_time;
    }

    public int getlastLogin() {
        return last_login;
    }

    public boolean isLiked() {
        return liked;
    }

    public Location getLocation() {
        return location;
    }

    public int getMatch() {
        return match;
    }

    public int getOrientation() {
        return orientation;
    }

    public List<String> getOrientationTags() {
        return orientation_tags;
    }

    public Photo getPhoto() {
        return photo;
    }

    public long getRelative() {
        return relative;
    }

    public String getStopLightColor() {
        return stoplight_color;
    }

    public String getStateCode() {
        return state_code;
    }

    public String getStateName() {
        return state_name;
    }

    public String getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCityName(String city_name) {
        this.city_name = city_name;
    }

    public void setCountryCode(String country_code) {
        this.country_code = country_code;
    }

    public void setCountryName(String country_name) {
        this.country_name = country_name;
    }

    public void setEnemy(int enemy) {
        this.enemy = enemy;
    }

    public void setFriend(int friend) {
        this.friend = friend;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setGenderTags(List<String> gender_tags) {
        this.gender_tags = gender_tags;
    }

    public void setIsOnline(int is_online) {
        this.is_online = is_online;
    }

    public void setLastContactTime(List<Integer> last_contact_time) {
        this.last_contact_time = last_contact_time;
    }

    public void setLastLogin(int last_login) {
        this.last_login = last_login;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setMatch(int match) {
        this.match = match;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public void setOrientationTags(List<String> orientation_tags) {
        this.orientation_tags = orientation_tags;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public void setRelative(long relative) {
        this.relative = relative;
    }

    public void setStateCode(String state_code) {
        this.state_code = state_code;
    }

    public void setStateName(String state_name) {
        this.state_name = state_name;
    }

    public void setStoplightColor(String stoplight_color) {
        this.stoplight_color = stoplight_color;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

