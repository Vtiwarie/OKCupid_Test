package com.vishaan.okcupid.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.vishaan.okcupid.model.User;

import java.util.List;

/**
 * Data source class used as a middleman to communicate
 * with the database and perform CRUD operations.
 */
public class UserDataSource extends AbstractDataSource {

    private static final String TAG = UserDataSource.class.getSimpleName();

    public UserDataSource(Context context) {
        super(context);
    }

    /**
     * Insert User into Database
     *
     * @param user to insert
     * @return long
     */
    public long insertUser(User user) {

        Gson gson = new Gson();
        ContentValues values = new ContentValues();
        values.put(UserContract.Columns.USER_ID, user.getUserid());
        values.put(UserContract.Columns.ENEMY, user.getEnemy());
        values.put(UserContract.Columns.RELATIVE, user.getRelative());
        values.put(UserContract.Columns.LAST_LOGIN, user.getlastLogin());
        values.put(UserContract.Columns.GENDER, user.getGender());
        values.put(UserContract.Columns.LOCATION, gson.toJson(user.getLocation()));
        values.put(UserContract.Columns.MATCH, user.getMatch());
        values.put(UserContract.Columns.GENDER_TAGS, gson.toJson(user.getGenderTags()));
        values.put(UserContract.Columns.LIKED, user.isLiked());
        values.put(UserContract.Columns.ORIENTATION, user.getOrientation());
        values.put(UserContract.Columns.PHOTO, gson.toJson(user.getPhoto()));
        values.put(UserContract.Columns.AGE, user.getAge());
        values.put(UserContract.Columns.FRIEND, user.getFriend());
        values.put(UserContract.Columns.IS_ONLINE, user.getIsOnline());
        values.put(UserContract.Columns.USERNAME, user.getUsername());
        values.put(UserContract.Columns.STOPLIGHT_COLOR, user.getStopLightColor());
        values.put(UserContract.Columns.LAST_CONTACT_TIME, gson.toJson(user.getLastContactTime()));
        values.put(UserContract.Columns.ORIENTATION_TAGS, gson.toJson(user.getOrientationTags()));

        long id = db.insertOrThrow(UserContract.TABLE_NAME, null, values);
        return id;
    }

    /**
     * Insert a list of users in bulk
     *
     * @param users to insert
     * @return int
     */
    public int bulkInsert(List<User> users) {
        int count = 0;
        db.beginTransaction();
        try {
            for(User user : users) {
                insertUser(user);
                count++;
            }
            db.setTransactionSuccessful();
        } catch(Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

        return count;
    }

}
