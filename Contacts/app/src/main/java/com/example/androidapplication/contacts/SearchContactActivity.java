package com.example.androidapplication.contacts;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SearchContactActivity extends ListActivity implements View.OnClickListener{
    //Create database object
    final SampleDBManager db = new SampleDBManager(this);
    //Declare EditText, Button and TextView
    EditText SearchContactEditText;
    Button SearchContactButton;
    TextView NoContactToSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_contact);

        //initialize them to appropriate id
        NoContactToSearch = (TextView) findViewById(R.id.textViewNoContactToSearch);
        SearchContactEditText =(EditText)findViewById(R.id.editTextSearchContact);
        SearchContactButton = (Button)findViewById(R.id.buttonSeachContact);
        SearchContactButton.setOnClickListener(this);

        db.open();//open the database
        Cursor cursor = db.getRow();//create a cursor to get all rows
        if (cursor.getCount()==0) {//if there is no rows in the table than prompt the user
            NoContactToSearch.setText("Nothing to search as Your contact list is empty :)");
            SearchContactButton.setEnabled(false);
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

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.buttonSeachContact){// if the search button is pressed than
            if (SearchContactEditText.getText().toString().equals("")){//check to makse sure edit text is not empty
                Toast.makeText(this, "Enter name to search for", Toast.LENGTH_SHORT).show();
            }
            else{//once its not empty than
                db.open();//open the database
                Cursor cursor = db.searchRow(SearchContactEditText.getText().toString());//create a cursor to search for a row

                if (cursor.getCount()!=0) {//if the input string is found than set in the list
                    String[] columns = new String[]{"contact_name"};
                    int[] to = new int[]{R.id.textViewDisplayContactname};

                    SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,R.layout.row_layout,cursor,columns,to);
                    setListAdapter(simpleCursorAdapter);
                }
                else{//if the input data is not found than notify the user
                    //NoContactToSearch.setText("Contact " + SearchContactEditText.getText().toString()+ " does not exists");
                    Toast.makeText(this, "contact " + SearchContactEditText.getText().toString() +" does not exists", Toast.LENGTH_SHORT).show();
                }
                db.close();//close the databse

            }//end else

        }//end imageButtonAdd


    }//end onClick

    protected void onListItemClick(ListView l, View v, int position, long id) {//if the user clicked on name from the list
        Cursor cursor = ((SimpleCursorAdapter)l.getAdapter()).getCursor();
        cursor.moveToPosition(position);

        //pass the corresponding data of the selected row to the edit contact activity
        Intent myintent = new Intent(SearchContactActivity. this, EditContactActivity.class);
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
        getMenuInflater().inflate(R.menu.menu_search_contact, menu);
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
