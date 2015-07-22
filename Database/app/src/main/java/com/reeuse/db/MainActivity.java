package com.reeuse.db;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.reeuse.db.R;


public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();

    private DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Database helper instance.
        dbHelper = new DatabaseHelper(this);

        // To see the result check the ADB LOG.

        //Insert Values
        Log.i(TAG, "******* Insert Query *******");
        dbHelper.insertUser("LarryPage","Co-founder"); // id - 1
        dbHelper.insertUser("AndyRuby","Architect"); // id - 2
        dbHelper.insertUser("Alan Eustace","VP"); // id - 3

       //Select Values
        Log.i(TAG, "******* Select Query *******");
        dbHelper.selectUser();

        //Update id -3 username as "sundram pichai"
        Log.i(TAG, "******* Update Query *******");
        dbHelper.updateUser("sundram pichai", 3);

        //Select Values
        Log.i(TAG, "******* Select Query *******");
        dbHelper.selectUser();

        //Delete id -2 value from table
        Log.i(TAG, "******* Delete Query *******");
        dbHelper.deleteUser(2);

        //Select Values
        Log.i(TAG, "******* Select Query *******");
        dbHelper.selectUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
