package com.vishaan.okcupid.model.db;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.ApplicationTestCase;

/**
 * Database testing.
 */
public class TestDB extends ApplicationTestCase<Application> {
    public TestDB() {
        super(Application.class);
    }

    /**
     * Database open helper
     */
    private DBHelper mDBHelper;

    /**
     * The SQL lite database handle
     */
    private SQLiteDatabase db;

    /**
     * Key/Value pairs for test insertion into users table
     */
    private ContentValues mUserInsertValues;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mDBHelper = DBHelper.getInstance(getContext());
        db = mDBHelper.getWritableDatabase();
        mUserInsertValues = retrieveTestUserValues();
    }

    /**
     * Test user insert into database
     */
    public void testUserInsert() {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        long id = db.insertOrThrow(UserContract.TABLE_NAME, null, mUserInsertValues);
        assertTrue("Error: user insert failed.", id != -1);

        Cursor cursor = db.query(
                UserContract.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        //assert that the table is not empty after insert
        assertTrue("Error: No records found: " + cursor.getCount(), cursor.moveToFirst());

        //validate that the record set found in the database matches the values inserted
        TestUtils.validateCurrentRecord("Error: Test data does not matches values in table", cursor, mUserInsertValues);

        // Move the cursor to demonstrate that there is only one record in the database
        assertFalse("Error: More than one record returned from query", cursor.moveToNext());
        cursor.close();
        db.close();
    }

    /**
     * Key/Value pair of data to be inserted in table
     *
     * @return ContentValues
     */
    private ContentValues retrieveTestUserValues() {
        ContentValues values = new ContentValues();

        values.put(UserContract.Columns.ENEMY, 1);
        values.put(UserContract.Columns.RELATIVE, 1);
        values.put(UserContract.Columns.LAST_LOGIN, 1);
        values.put(UserContract.Columns.GENDER, "gender");
        values.put(UserContract.Columns.LOCATION, "location");
        values.put(UserContract.Columns.USER_ID, 1);
        values.put(UserContract.Columns.MATCH, 1);
        values.put(UserContract.Columns.GENDER_TAGS, "gender tags");
        values.put(UserContract.Columns.LIKED, 1);
        values.put(UserContract.Columns.ORIENTATION, 1);
        values.put(UserContract.Columns.PHOTO, "photo");
        values.put(UserContract.Columns.AGE, 26);
        values.put(UserContract.Columns.FRIEND, 1);
        values.put(UserContract.Columns.IS_ONLINE, 1);
        values.put(UserContract.Columns.USERNAME, "username");
        values.put(UserContract.Columns.SPOTLIGHT_COLOR, "spotlight_color");
        values.put(UserContract.Columns.LAST_CONTACT_TIME, "last_contact_time");
        values.put(UserContract.Columns.ORIENTATION_TAGS, "orientation_tags");

        return values;
    }

    @Override
    protected void tearDown() throws Exception {
        db.close();
        super.tearDown();
    }
}