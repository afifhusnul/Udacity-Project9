package com.android.example.udacity_project9;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.example.udacity_project9.data.HabitContract;
import com.android.example.udacity_project9.data.HabitDBHelper;

import static com.android.example.udacity_project9.data.HabitContract.HabitEntry.TABLE_NAME;

public class MainActivity extends AppCompatActivity {

    /** Database helper that will provide us access to the database */
    private HabitDBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        // to access the database we have to instantiate our SQLiteOpener subclass
        // and pass the context of the current activity
        mDbHelper = new HabitDBHelper(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about
     * the state of the habit database.
     */
    private void displayDatabaseInfo() {
        // create and/or open a database to read it from
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        //define a projection that specifies which columns from the database
        //you will actually use after the query.
        String[] projection = {
                HabitContract.HabitEntry._ID,
                HabitContract.HabitEntry.COLUMN_HABIT_NAME,
                HabitContract.HabitEntry.COLUMN_HABIT_DESC,
                HabitContract.HabitEntry.COLUMN_HABIT_TIME };

        // Perform a query on the habits in the table
        Cursor cursor = db.query(
                TABLE_NAME,    // the table to query
                projection,                             // The columns to return
                null,                                   // The columns for the WHERE clause
                null,                                   // The values for the WHERE clause
                null,                                   // Don't group the rows
                null,                                   // Don't filter by row groups
                null);                                  // The sort order

        // As per direction from reviewer, changed as belo
        // There is a single read method that returns a Cursor object. It should get the data repository in read and use the query()
        // method to retrieve at least one column of data.
        // Need to do : Please perform the query in a read() method. The read method should perform the query and return the Cursor obtained.

        TextView displayView = (TextView) findViewById(R.id.text_view_habit);
        displayView.setText("The habits table contains " + cursor.getCount() + " habits.\n\n");
        if( cursor != null) {
            cursor.moveToFirst();
            for ( int i = 0; i<cursor.getCount(); i++) {
                displayView.append("\n"+cursor.getString(0)+"_"+ cursor.getString(1)+"_"+cursor.getString(2)+"_"+cursor.getString(3));
                cursor.moveToNext();
            }
        }
        cursor.close();
    }

    /**
     * Helper method to insert hardcoded habit data into the database for debugging purposes only
     */
    private void insertHabit() {
        //gets the database to writing mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        //Create a ContentValues object where column names are the key
        // and habit attributes are the values
        ContentValues values = new ContentValues();
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_NAME, "Swimming");
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_DESC, "Swimming in the morning 7:AM");
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_TIME, 0);

        // insert a new row of habits into the database, returning the id of that new row.
        // the first argument for db.insert() is the habit table name.
        // the second argument provides the name of the column in which the framework
        // can insert NULL in the event that the ContentsValue is empty
        // (if this is set to "null", then the framework will not insert a row when there is no value)
        // The third argument id the ContentsValue object containing the info for teh habit.
        long newRowId = db.insert(TABLE_NAME, null, values);
    }

    /**
     * Helper method to delete all habits in the database.
     */
    private void deleteAllHabits() {
        //gets the database to writing mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        //Execute delete all data
        db.delete(TABLE_NAME,null,null);
        //Close trx
        db.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                // Do nothing for now
                insertHabit();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                deleteAllHabits();
                displayDatabaseInfo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
