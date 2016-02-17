package com.vishaan.okcupid.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * List of Users, required for Gson parsing,
 * and any other functions that
 * pertain to a group of users.
 */
public class UserList {
    @SerializedName("data")
    List<User> users;

    public UserList() {
        users = new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }

    /**
     * Parse a list of users from a JSON string,
     * into a list of users
     *
     * @param json string to parse
     * @return
     */
    public static List<User> fromJSON(String json) {
        Gson gson = new GsonBuilder().create();
        List<User> users = gson.fromJson(json, UserList.class).getUsers();
        return users;
    }
}
