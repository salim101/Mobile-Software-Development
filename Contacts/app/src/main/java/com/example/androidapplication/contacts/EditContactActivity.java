package com.example.androidapplication.contacts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.RowId;

public class EditContactActivity extends Activity implements View.OnClickListener {
    //Create database object
    final SampleDBManager db = new SampleDBManager(this);
    //Declare EditText and Buttons
    EditText idview,nameView,address1View, address2View,countyView, contactnoView;
    Button EditContactButton,GetLocationButton,DeleteContactButton,SaveEditButton,CancelEditButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        //get the intent
        String rowid =getIntent().getExtras().getString("rowid");
        String fullname =getIntent().getExtras().getString("fullname");
        String address1 =getIntent().getExtras().getString("address1");
        String address2 =getIntent().getExtras().getString("address2");
        String county =getIntent().getExtras().getString("county");
        String contactno =getIntent().getExtras().getString("contactno");

        //Toast.makeText(this, "row id "+rowid, Toast.LENGTH_SHORT).show();

        SaveEditButton = (Button)findViewById(R.id.buttonSaveEdit);
        CancelEditButton = (Button)findViewById(R.id.buttonCancelEdit);

        SaveEditButton.setVisibility(View.INVISIBLE);
        CancelEditButton.setVisibility(View.INVISIBLE);
        //initialize the buttons to the appropriate id
        EditContactButton = (Button)findViewById(R.id.buttonEdit);
        GetLocationButton = (Button)findViewById(R.id.buttonGetLocation);
        DeleteContactButton = (Button)findViewById(R.id.buttonDelete);

        //initialize the EditText and set the appropriate data to it
        idview =(EditText)findViewById(R.id.editTextID);
        idview.setText(rowid);

        nameView =(EditText)findViewById(R.id.editTextFullName);
        nameView.setText(fullname);

        address1View =(EditText)findViewById(R.id.editTextAddress1);
        address1View.setText(address1);

        address2View =(EditText)findViewById(R.id.editTextAddress2);
        address2View.setText(address2);

        countyView =(EditText)findViewById(R.id.editTextCounty);
        countyView.setText(county);

        contactnoView =(EditText)findViewById(R.id.editTextContactNo);
        contactnoView.setText(contactno);

        //make the edit text not to be editable
        nameView.setFocusable(false);
        address1View.setFocusable(false);
        address2View.setFocusable(false);
        countyView.setFocusable(false);
        contactnoView.setFocusable(false);

        //set the listener on buttons
        EditContactButton.setOnClickListener(this);
        GetLocationButton.setOnClickListener(this);
        DeleteContactButton.setOnClickListener(this);
        SaveEditButton.setOnClickListener(this);
        CancelEditButton.setOnClickListener(this);

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
        if(v.getId() == R.id.buttonEdit){//if edit button is pressed than

            //set all the edit text to be editable.
            nameView.setFocusable(true);
            nameView.setFocusableInTouchMode(true);

            address1View.setFocusable(true);
            address1View.setFocusableInTouchMode(true);

            address2View.setFocusable(true);
            address2View.setFocusableInTouchMode(true);

            countyView.setFocusable(true);
            countyView.setFocusableInTouchMode(true);

            contactnoView.setFocusable(true);
            contactnoView.setFocusableInTouchMode(true);

            //hide the other other button.
            EditContactButton.setVisibility(View.INVISIBLE);
            GetLocationButton.setVisibility(View.INVISIBLE);
            DeleteContactButton.setVisibility(View.INVISIBLE);

            //show the save and cancel button
            SaveEditButton.setVisibility(View.VISIBLE);
            CancelEditButton.setVisibility(View.VISIBLE);
        }//end buttonEdit
        else if(v.getId() == R.id.buttonSaveEdit){//if the save button is pressed than
            if (nameView.getText().toString().equals("")) {//check the name edit to make sure its not empty
                Toast.makeText(this, "Enter contact name", Toast.LENGTH_SHORT).show();
            }
            else if (contactnoView.getText().toString().equals("")) {//check the contact edit to make sure its not empty
                Toast.makeText(this, "Enter contact number", Toast.LENGTH_SHORT).show();
            }
            else{//once they are filled than
                db.open();//open the database
                //pass the edit text data to the database
                long result =db.updateRow(idview.getText().toString(), nameView.getText().toString(), address1View.getText().toString(),
                        address2View.getText().toString(), countyView.getText().toString(), contactnoView.getText().toString());

                if(result >0){//check if everything is updated correctly
                    Toast.makeText(getApplicationContext(), "Contact Updated", Toast.LENGTH_LONG).show();
                }
                else{//if not than notify the user with the error message
                    Toast.makeText(getApplicationContext(),"Error while updating Contact",Toast.LENGTH_LONG).show();
                }
                db.close();//close the database

                finish();//bring the user to the previous activity i.e view contacs

            }//end else

        }//end buttonSaveEdit
        else if(v.getId() == R.id.buttonCancelEdit){//if the cancel button is pressed than come out of that activity
            Toast.makeText(this, "Changes hasn't been made", Toast.LENGTH_SHORT).show();
            finish();
        }//end buttonCancelEdit
        else if(v.getId() == R.id.buttonGetLocation){// if the find on map button is pressed than

            String address= "";//declare and initialize address variable

            if (address1View.getText().toString().equals("")) {//check to make sure address line 1 edit text is filled
                Toast.makeText(this, "Enter address line 1", Toast.LENGTH_SHORT).show();
            }
            else if (address2View.getText().toString().equals("")) {//check to make sure address line 2 edit text is filled
                Toast.makeText(this, "Enter address line 2", Toast.LENGTH_SHORT).show();
            }
            else if (countyView.getText().toString().equals("")) {//check to make sure county edit text is filled
                Toast.makeText(this, "Enter County name", Toast.LENGTH_SHORT).show();
            }
            else {//once they are filled
                //Toast.makeText(this, "Clicked on Get Location", Toast.LENGTH_SHORT).show();

                //check the internet connection
                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if(networkInfo !=null && networkInfo.isConnected()){//if there is internet connection than
                    //get the full address from the edit texts
                    address +=address1View.getText().toString()+" "+address2View.getText().toString()+" "+countyView.getText().toString();

                    //pass the address to the map activity
                    Intent myintent = new Intent(EditContactActivity. this, MapsActivity.class);
                    myintent.putExtra("address", address);
                    startActivity(myintent);
                }else {//if there is no internet connection than notify the user
                    Toast.makeText(this, "no network connection available", Toast.LENGTH_LONG).show();
                }


            }//end else




        }//end buttonGetLocation
        else if(v.getId() == R.id.buttonDelete){//if the delete contact button is pressed

            //create a dialogue
            AlertDialog.Builder DeleteContactAlert = new AlertDialog.Builder(EditContactActivity.this);
            DeleteContactAlert.setTitle("Deleting Contact");
            DeleteContactAlert.setMessage("Confirm to delete " +  nameView.getText().toString()+ " from contacts");

            DeleteContactAlert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {//if the user confirm to delete than
                    db.open();//open the database
                    long result =db.deleteRow(idview.getText().toString());//pass that row id to the database

                    if(result >0){//check to make sure the row is deleted correctly
                        Toast.makeText(getApplicationContext(), "Contact Deleted", Toast.LENGTH_LONG).show();
                    }
                    else{//if it didnt than prompt the user of the error
                        Toast.makeText(getApplicationContext(),"Error while deleting Contact",Toast.LENGTH_LONG).show();
                    }
                    db.close();//close the database

                    finish();//finish the current activity
                    dialog.dismiss();//dismiss the delete dialogue
                }
            });
            DeleteContactAlert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {//if the user press No on the dialogue than simply
                    dialog.dismiss();//dismiss the delete dialogue
                }
            });
            DeleteContactAlert.show();//show the dialogue

        }//end buttonDelete

    }//end onclick

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_contact, menu);
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
