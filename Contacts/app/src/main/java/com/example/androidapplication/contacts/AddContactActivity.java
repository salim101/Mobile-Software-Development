package com.example.androidapplication.contacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContactActivity extends Activity implements View.OnClickListener{
    //Declare EditText and Buttons
    EditText AddName, AddAddress1,AddAddress2,AddCounty,AddContactNo;
    Button AddContactButton, CancelContactAddButton;
    //Create database object
    final SampleDBManager db = new SampleDBManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        //initialize the EditText to the appropriate id
        AddName =(EditText)findViewById(R.id.editTextAddName);
        AddAddress1 =(EditText)findViewById(R.id.editTextAddAddress1);
        AddAddress2 =(EditText)findViewById(R.id.editTextAddAddress2);
        AddCounty =(EditText)findViewById(R.id.editTextAddCounty);
        AddContactNo =(EditText)findViewById(R.id.editTextAddContactNo);
        //initialize the buttons to the appropriate id
        AddContactButton = (Button)findViewById(R.id.buttonAddContact);
        CancelContactAddButton = (Button)findViewById(R.id.buttonCancelAddingContact);
        //set the listener on buttons
        AddContactButton.setOnClickListener(this);
        CancelContactAddButton.setOnClickListener(this);
    }//end onCreate

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.buttonAddContact){//if the add contact button is clicked

            if (AddName.getText().toString().equals("")){//check to make sure name edittext its not empty
                Toast.makeText(this, "Enter contact name", Toast.LENGTH_SHORT).show();
            }
            else if (AddContactNo.getText().toString().equals("")) {//check to make sure contact number edittext its not empty
                Toast.makeText(this, "Enter contact number", Toast.LENGTH_SHORT).show();
            }
            else{//once they are not empty  than
                db.open();//open database
                long result =db.insertRow(AddName.getText().toString(),AddAddress1.getText().toString(),//pass the data to the database
                        AddAddress2.getText().toString(),AddCounty.getText().toString(),AddContactNo.getText().toString());

                if(result >0){//if the data is inserted without any errors
                    Toast.makeText(getApplicationContext(), "Contact Successfully Added", Toast.LENGTH_LONG).show();
                    //clear the data from the edit text
                    AddName.setText("");
                    AddAddress1.setText("");
                    AddAddress2.setText("");
                    AddCounty.setText("");
                    AddContactNo.setText("");
                }
                else{//if there is error in adding conatct than prompt the user of the error
                    Toast.makeText(getApplicationContext(),"Error Adding Contact",Toast.LENGTH_LONG).show();
                }
                db.close();//close the database

                //bring the user to the view contacts activity
                Intent myintent = new Intent(AddContactActivity. this, ViewContactActivity.class);
                startActivity(myintent);
                finish();
            }//end else
        }//end buttonAddContact
        else if(v.getId() == R.id.buttonCancelAddingContact){//if the cancel button is clicked than bring the user out of that activity
            Toast.makeText(this, "contact cancelled", Toast.LENGTH_SHORT).show();
            finish();
        }

    }//end onClick

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_contact, menu);
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
