package com.example.androidapplication.contacts;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewContactActivity extends ListActivity {
    //Create database object
    final SampleDBManager db = new SampleDBManager(this);
    //Declare TextView
    TextView NoContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);
        //initialize the TextView to the appropriate id
        NoContacts = (TextView) findViewById(R.id.textViewNoContacts);

        db.open();//open the database
        Cursor cursor = db.getRow();//create a cursor to get all rows
        if (cursor.moveToFirst()) {//if there rows in the table than get the name column and display it
            String[] columns = new String[]{"contact_name"};
            int[] to = new int[]{R.id.textViewDisplayContactname};

            SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.row_layout,cursor, columns,to);
            setListAdapter(simpleCursorAdapter);
        }
        else {//if there are no rows than show the message on the screen
            NoContacts.setText("Your contact list is empty :(\n\n Add Contact :)");
            //Toast.makeText(this, "Your contact list is empty :( ", Toast.LENGTH_SHORT).show();
        }
        db.close();//close the database

    }//end onCreate


    // Reference: The following code is from
    //http://stackoverflow.com/questions/5545217/back-button-and-refreshing-previous-activity
    @Override
    public void onRestart() {//restart the activity to get the updated data when back button is pressed.
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
    // Reference complete


    protected void onListItemClick(ListView l, View v, int position, long id) {
        Cursor cursor = ((SimpleCursorAdapter)l.getAdapter()).getCursor();
        cursor.moveToPosition(position);
        //Toast.makeText(this, "Clicked on task id "+cursor.getString(cursor.getColumnIndex("_id")), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "Clicked on contact name at position "+cursor.getPosition(), Toast.LENGTH_SHORT).show();

        //pass the data of the clicked row to edit contact activity
        Intent myintent = new Intent(ViewContactActivity. this, EditContactActivity.class);
        myintent.putExtra("rowid", cursor.getString(cursor.getColumnIndex("_id")).toString());
        myintent.putExtra("fullname", cursor.getString(cursor.getColumnIndex("contact_name")).toString());
        myintent.putExtra("address1", cursor.getString(cursor.getColumnIndex("contact_address1")).toString());
        myintent.putExtra("address2", cursor.getString(cursor.getColumnIndex("contact_address2")).toString());
        myintent.putExtra("county", cursor.getString(cursor.getColumnIndex("contact_county")).toString());
        myintent.putExtra("contactno", cursor.getString(cursor.getColumnIndex("contact_contactno")).toString());
        startActivity(myintent);

    }//end onListItemClick

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_contact, menu);
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


}//end class
