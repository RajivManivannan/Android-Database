package com.reeuse.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.reeuse.db.UserContract.UserEntry;

/**
 * DatabaseHelper.java
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private String TAG = DatabaseHelper.class.getSimpleName();


    static final String DATABASE_NAME = "user_db";
    static final int DATABASE_VERSION = 1;
    private static DatabaseHelper dbHelper;
    private static SQLiteDatabase readableDatabase;
    private static SQLiteDatabase writableDatabase;
    private static Context mContext;

    /**
     * Instantiates a new DB helper.
     *
     * @param context the context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE " + UserEntry.TABLE_NAME + "(" +
                UserEntry._ID + " INTEGER PRIMARY KEY," +
                UserEntry.COLUMN_NAME_USER_NAME + " TEXT," +
                UserEntry.COLUMN_NAME_USER_DESIGNATION + " TEXT" + ")";
        Log.i(TAG, createQuery);
        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropQuery = "DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME;
        db.execSQL(dropQuery);
    }

    //------------------------------------------------------------- Required methods----------------------------------------------------------------//
    /**
     * Returns an instance of DBHelper class.
     *
     * @return Returns DatabaseHelper instance.
     */
    public static synchronized DatabaseHelper getInstance() {
        if (dbHelper == null) {
            dbHelper = new DatabaseHelper(mContext);
        }
        return dbHelper;
    }

    /**
     * Returns an instance of readable database.
     *
     * @return Returns readable SQLiteDatabase instance.
     */
    private synchronized SQLiteDatabase getReadableDb() {
        if (readableDatabase == null) {
            readableDatabase = this.getReadableDatabase();
        }
        return readableDatabase;
    }

    /**
     * Returns an instance of writable database.
     *
     * @return Returns writable SQLiteDatabase instance.
     */
    private synchronized SQLiteDatabase getWritableDb() {
        if (writableDatabase == null) {
            writableDatabase = this.getWritableDatabase();
        }
        return writableDatabase;
    }
 //-------------------------------------------------------------Insert----------------------------------------------------------------//
    /**
     * To insert users in the table
     *
     * @param userName
     * @param designation
     * @return last inserted id.
     */
    public long insertUser(String userName, String designation) {
        SQLiteDatabase database = getWritableDb();
        ContentValues values = new ContentValues();
        values.put(UserEntry.COLUMN_NAME_USER_NAME, userName);
        values.put(UserEntry.COLUMN_NAME_USER_DESIGNATION, designation);
        return database.insert(UserEntry.TABLE_NAME, null, values);
    }

    //-------------------------------------------------------------Select----------------------------------------------------------------//
    /**
     * To select all the user from database.
     */
    public void selectUser() {
        SQLiteDatabase database = getReadableDb();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                UserEntry._ID,
                UserEntry.COLUMN_NAME_USER_NAME,
                UserEntry.COLUMN_NAME_USER_DESIGNATION
        };
        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                UserEntry.COLUMN_NAME_USER_NAME + " DESC";
        Cursor cursor = database.query(
                UserEntry.TABLE_NAME,  // The table to query
                projection,    // The columns to return
                null,   // The columns for the WHERE clause (selection)
                null,   // The values for the WHERE clause (selectionArgs)
                null,  // don't group the rows
                null,  // don't filter by row groups
                sortOrder  // The sort order
        );
        cursor.moveToFirst();
        int i = 0;
        while (!cursor.isAfterLast()) {
            Log.i(TAG, cursor.getLong(cursor.getColumnIndexOrThrow(UserEntry._ID))+"");
            Log.i(TAG, cursor.getString(cursor.getColumnIndexOrThrow(UserEntry.COLUMN_NAME_USER_NAME)));
            Log.i(TAG, cursor.getString(cursor.getColumnIndexOrThrow(UserEntry.COLUMN_NAME_USER_DESIGNATION)));
            cursor.moveToNext();
            i++;
        }
        cursor.close(); // Close cursor object.
    }

    //-------------------------------------------------------------Update----------------------------------------------------------------//
    /**
     * To update the username using user id.
     *
     * @param userName
     * @param rowId
     */
    public void updateUser(String userName, int rowId) {
        SQLiteDatabase db = getWritableDb();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(UserEntry.COLUMN_NAME_USER_NAME, userName);

        // Which row to update, based on the ID
        String selection = UserEntry._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(rowId)};

        int count = db.update(
                UserEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    //-------------------------------------------------------------Delete----------------------------------------------------------------//
    /**
     * To delete the particular user using user id.
     * @param userId
     */
    public void deleteUser(int userId) {
        SQLiteDatabase db = getWritableDb();
        // Define 'where' part of query.
        String selection = UserEntry._ID + " LIKE ?";
       // Specify arguments in placeholder order.
        String[] selectionArgs = {String.valueOf(userId)};
         // Issue SQL statement.
        int count = db.delete(UserEntry.TABLE_NAME, selection, selectionArgs);
    }

}
