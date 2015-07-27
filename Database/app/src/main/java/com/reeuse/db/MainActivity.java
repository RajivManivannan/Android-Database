package com.reeuse.db;

import android.database.Cursor;
import com.reeuse.db.UserContract.UserTable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {
    private String TAG = MainActivity.class.getSimpleName();

    private SimpleCursorAdapter mAdapter;
    private DatabaseHelper dbHelper;
    private EditText userName;
    private EditText designation;
    private EditText whereCondition;
    private CoordinatorLayout mCoordinatorLayout;
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCoordinatorLayout = (CoordinatorLayout)findViewById(R.id.main_coordinate_layout);
        mListView = (ListView) findViewById(R.id.main_list_view);
        //Database helper instance.
        dbHelper = new DatabaseHelper(this);
        //SimpleCursorAdapter to load the user data
        mAdapter = new SimpleCursorAdapter(getBaseContext(),
                R.layout.listview_item_layout,
                null,
                new String[] { UserTable._ID, UserTable.COLUMN_NAME_USER_NAME, UserTable.COLUMN_NAME_USER_DESIGNATION},
                new int[] { R.id.user_id , R.id.name, R.id.designation }, 0);

        mListView.setAdapter(mAdapter);

        userName = (EditText) findViewById(R.id.main_username_etxt);
        designation = (EditText) findViewById(R.id.main_designation_etxt);
        whereCondition = (EditText) findViewById(R.id.main_where_condition_etxt);
        (findViewById(R.id.main_insert_btn)).setOnClickListener(this);
        (findViewById(R.id.main_update_btn)).setOnClickListener(this);
        (findViewById(R.id.main_delete_btn)).setOnClickListener(this);

        /** Creating a loader for populating listview from sqlite database */
        /** This statement, invokes the method onCreatedLoader() */
        getSupportLoaderManager().initLoader(0, null, this);
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(MainActivity.this) {
            @Override
            public Cursor loadInBackground() {
                return dbHelper.selectUser();
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data.getColumnCount()!=0)
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onClick(View v) {
        int itemID = v.getId();
        switch (itemID) {
            case R.id.main_insert_btn:
                insertData();
                break;
            case R.id.main_update_btn:
                updateData();
                break;
            case R.id.main_delete_btn:
                deleteData();
                break;
            default:
                break;
        }
    }


    /**
     * To update the cursor value.
     */
    private void refresh(){
        getSupportLoaderManager().restartLoader(0, null, this);
    }

    /**
     * To insert new user
     */
    private void insertData() {
        String userNameValue = userName.getText().toString();
        String designationValue = designation.getText().toString();
        if (TextUtils.isEmpty(userNameValue)) {
            userName.setError("");
        } else if (TextUtils.isEmpty(userNameValue)) {
            designation.setError("");
        } else {
           long result =  dbHelper.insertUser(userNameValue, designationValue);
            if(result>0) {
                refresh();
                showSnackBar(getString(R.string.main_successfully_inserted));
            }
            else
                showSnackBar(getString(R.string.main_error_occurred));
        }
    }

    /**
     * To update the user designation
     */
    private void updateData() {
        String designationValue = designation.getText().toString();
        String where = whereCondition.getText().toString();
        if (TextUtils.isEmpty(designationValue)) {
            designation.setError("");
        } else if (TextUtils.isEmpty(where)) {
            whereCondition.setError("");
        } else {
            int result = dbHelper.updateUser(designationValue, Integer.parseInt(where));
            if(result>0) {
                refresh();
                showSnackBar(getString(R.string.main_successfully_updated));
            }
            else
                showSnackBar(getString(R.string.main_error_occurred));
        }
    }

    /**
     * To delete the user
     */
    private void deleteData() {
        String where = whereCondition.getText().toString();
        if (TextUtils.isEmpty(where)) {
            whereCondition.setError("");
        } else {
           int result = dbHelper.deleteUser(Integer.parseInt(where));
            if(result>0) {
                refresh();
                showSnackBar(getString(R.string.main_successfully_deleted));
            }
            else
                showSnackBar(getString(R.string.main_error_occurred));
        }
    }

    /**
     * To show the message.
     * @param message
     */
    private void showSnackBar(String message){
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();

    }

}
