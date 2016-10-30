package com.android.example.udacity_project9;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.example.udacity_project9.data.HabitContract;
import com.android.example.udacity_project9.data.HabitContract.HabitEntry;
import com.android.example.udacity_project9.data.HabitDBHelper;

/**
 * Created by NUSNAFIF on 10/30/2016.
 */

public class EditorActivity extends AppCompatActivity {

    /** EditText field to enter the habit's name */
    private EditText mNameEditText;

    /** EditText field to enter the habit's description */
    private EditText mDescEditText;

    /** EditText field to enter the time habit's activity */
    private Spinner mTimeSpinner;

    private int mTime = HabitEntry.TIME_MORNING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_habit_name);
        mDescEditText = (EditText) findViewById(R.id.edit_habit_desc);
        mTimeSpinner = (Spinner) findViewById(R.id.spinner_time);

        setupSpinner();
    }

    /**
     * Setup the dropdown spinner.
     */
    private void setupSpinner() {
        // create the adapter for the spinner. The list options are from the String array
        // it will use to get the default layout
        ArrayAdapter timeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_time_options, android.R.layout.simple_spinner_item);

        // specify dropdown layout style - simple list with one item per line
        timeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // apply the adapter to the spinner
        mTimeSpinner.setAdapter(timeSpinnerAdapter);

        // set the integer mSelected to the constant values
        mTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.time_morning))) {
                        mTime = HabitEntry.TIME_MORNING;
                    } else if (selection.equals(getString(R.string.time_afternoon))) {
                        mTime = HabitEntry.TIME_AFTERNOON;
                    } else {
                        mTime = HabitEntry.TIME_EVENING;
                    }
                }
            }

            //Because AdapterView is an abstract class, set default
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mTime = HabitEntry.TIME_MORNING;
            }
        });
    }

    /**
     * Get user input from editor and save new habit into the database
     */
    private void insertHabit() {
        // read from input fields
        // use trim to eliminate leading or trailing white space
        String nameString = mNameEditText.getText().toString().trim();
        String descString = mDescEditText.getText().toString().trim();

        // create database helper
        HabitDBHelper mDbHelper = new HabitDBHelper(this);

        // get the database in write code
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // create a ContentValues object where column names are keys,
        ContentValues values = new ContentValues();
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_NAME, nameString);
        values.put(HabitEntry.COLUMN_HABIT_DESC, descString);
        values.put(HabitEntry.COLUMN_HABIT_TIME, mTime);

        // insert a new row for the habit in the database. This returns the ID of the new row.
        long newRowId = db.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);

        // Show a toast error message
        if (newRowId == -1) {
            // If the row ID is -1, then there is an error with insertion
            Toast.makeText(this, "Error with saving this habit", Toast.LENGTH_LONG).show();
        } else {
            // otherwise, insert data success, show success message
            Toast.makeText(this, "Habit saved with the row id: " + newRowId, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu option from the res/menu/menu_editor.xml file.
        // this adds menu items to the app bar
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // respond to a click on the "save" menu option
            case R.id.action_save:
                // save habit to database
                insertHabit();
                // exit activity
                finish();
                return true;
            // respond to a click on the "delete" menu option
            case R.id.action_delete:
                //do nothing for now
                return true;
            // respond to a click on the "up/back" arrow button in the app bar
            case android.R.id.home:
                // navigate back to the parent activity (MainActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
