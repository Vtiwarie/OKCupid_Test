package com.vishaan.okcupid.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Vishaan on 2/14/2016.
 */
public class UserDataSource {
    private DBHelper mDBHelper;
    private SQLiteDatabase db;

    public UserDataSource(Context context) {
        mDBHelper = DBHelper.getInstance(context);
    }

    public void open() {
        db = mDBHelper.getReadableDatabase();
    }

    public boolean isOpen() {
        return db.isOpen();
    }

    public void close() {
        if(isOpen()) {
            db.close();
        }
    }
}
