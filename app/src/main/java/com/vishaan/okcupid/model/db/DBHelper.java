package com.vishaan.okcupid.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Database open helper class definition
 */
public class DBHelper extends SQLiteOpenHelper {
    static final String DB_NAME = "db_okcupid";
    public static final int DB_VERSION = 1;

    /**
     * Singleton instance
     */
    private static DBHelper mInstance;

    private DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Only create the db helper as a thread-safe singleton object, to avoid multiple database instantiations
     *
     * @param context
     * @return DBHelper
     */
    public static synchronized DBHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DBHelper(context);
        }

        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String sql = "CREATE TABLE IF NOT EXISTS " + UserContract.TABLE_NAME + " (" +
                UserContract.Columns.USER_ID + " TEXT PRIMARY KEY ON CONFLICT REPLACE, " +
                UserContract.Columns.ENEMY + " INTEGER NOT NULL, " +
                UserContract.Columns.RELATIVE + " INTEGER NOT NULL, " +
                UserContract.Columns.LAST_LOGIN + " INTEGER NOT NULL, " +
                UserContract.Columns.GENDER + " INTEGER NOT NULL, " +
                UserContract.Columns.LOCATION + " TEXT NOT NULL, " +
                UserContract.Columns.MATCH + " INTEGER NOT NULL, " +
                UserContract.Columns.GENDER_TAGS + " TEXT DEFAULT NULL, " +
                UserContract.Columns.LIKED + " INTEGER NOT NULL, " +
                UserContract.Columns.ORIENTATION + " INTEGER NOT NULL, " +
                UserContract.Columns.PHOTO + " TEXT NOT NULL, " +
                UserContract.Columns.AGE + " INTEGER NOT NULL, " +
                UserContract.Columns.FRIEND + " INTEGER NOT NULL, " +
                UserContract.Columns.IS_ONLINE + " INTEGER NOT NULL, " +
                UserContract.Columns.USERNAME + " TEXT NOT NULL, " +
                UserContract.Columns.STOPLIGHT_COLOR + " TEXT DEFAULT NULL, " +
                UserContract.Columns.LAST_CONTACT_TIME + " TEXT NOT NULL, " +
                UserContract.Columns.ORIENTATION_TAGS + " TEXT NOT NULL " +
            ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserContract.TABLE_NAME);
        onCreate(db);
    }
}
