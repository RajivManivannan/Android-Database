
package com.reeuse.db;

import android.provider.BaseColumns;

/**
 * UserContract.java

 */
public final class UserContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public UserContract() {}

    /* Inner class that defines the table contents */
    public static abstract class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user_entry";
        public static final String COLUMN_NAME_USER_NAME= "user_name";
        public static final String COLUMN_NAME_USER_DESIGNATION= "user_designation";
    }


}