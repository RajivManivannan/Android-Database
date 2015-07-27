
package com.reeuse.db;

import android.provider.BaseColumns;

/**
 * UserContract.java

 */
public final class UserContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public UserContract() {}

    static final String DATABASE_NAME = "user_db";
    static final int DATABASE_VERSION = 1;

    /* Inner class that defines the table contents */
    public static abstract class UserTable implements BaseColumns {
         static final String TABLE_NAME = "user_entry";
         static final String COLUMN_NAME_USER_NAME= "user_name";
         static final String COLUMN_NAME_USER_DESIGNATION= "user_designation";
    }


}