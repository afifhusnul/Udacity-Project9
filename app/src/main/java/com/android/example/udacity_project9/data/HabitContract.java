package com.android.example.udacity_project9.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by NUSNAFIF on 10/30/2016.
 */

public final class HabitContract {
    //Empty Constructor
    private HabitContract(){}

    public static final String CONTENT_AUTHORITY = "com.android.example.udacity_project9";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_HABITS = "habits";


    /**
     * Inner class that defines constant values for the habits database table.
     * Each entry in the table represents a single habit.
     */
    public static final class HabitEntry implements BaseColumns {

        /** The content URI to access the habit data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_HABITS);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of habits.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_HABITS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single habit.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_HABITS;

        /** Name of database table for habits */
        public final static String TABLE_NAME = "habits";

        /**
         * Unique ID number for the habit (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the habit.
         *
         * Type: TEXT
         */
        public final static String COLUMN_HABIT_NAME ="name";

        /**
         * Description of the habit.
         *
         * Type: TEXT
         */
        public final static String COLUMN_HABIT_DESC = "description";

        /**
         * TIME (Morning-Afternoon-Evening) of the habit.
         *
         * The only possible values are {@link #TIME_MORNING}, {@link #TIME_AFTERNOON},
         * or {@link #TIME_EVENING}.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_HABIT_TIME = "time";


        /**
         * Possible values for the gender of the habit.
         */
        public static final int TIME_MORNING = 0;
        public static final int TIME_AFTERNOON = 1;
        public static final int TIME_EVENING = 2;

        /**
         * Returns whether or not the given gender is {@link #TIME_MORNING}, {@link #TIME_AFTERNOON},
         * or {@link #TIME_EVENING}.
         */
        public static boolean isValidtIME(int time) {
            if (time == TIME_MORNING || time == TIME_AFTERNOON || time == TIME_EVENING) {
                return true;
            }
            return false;
        }
    }
}
