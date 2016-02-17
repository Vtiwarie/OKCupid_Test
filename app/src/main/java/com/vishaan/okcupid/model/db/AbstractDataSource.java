package com.vishaan.okcupid.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Contract to enforce that all data sources implement
 * these methods.
 */
abstract public class AbstractDataSource {

    /**
     * Dataase helper object
     */
    protected DBHelper mDBHelper;

    /**
     * Database object ot be used in subclasses
     */
    protected SQLiteDatabase db;

    public AbstractDataSource(Context context) {
        mDBHelper = DBHelper.getInstance(context);
    }

    /**
     * Open a database handler
     */
    public void open() {
        db = mDBHelper.getWritableDatabase();
    }

    /**
     * Check if database connection is open
     *
     * @return boolean
     */
    public boolean isOpen() {
        return db.isOpen();
    }

    /**
     * Close database connection
     */
    public void close() {
        if(isOpen()) {
            db.close();
        }
    }
}
