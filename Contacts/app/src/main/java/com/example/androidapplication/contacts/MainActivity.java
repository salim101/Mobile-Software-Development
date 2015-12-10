package com.example.androidapplication.contacts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener{
    //declare the image buttons
    ImageButton AddContact,ViewContact,SearchContact,EmergencyContacs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize the buttons to the appropriate id
        AddContact =(ImageButton)findViewById(R.id.imageButtonAdd);
        ViewContact =(ImageButton)findViewById(R.id.imageButtonView);
        SearchContact =(ImageButton)findViewById(R.id.imageButtonSearch);
        EmergencyContacs=(ImageButton)findViewById(R.id.imageButtonEmergency);

        //set the listener on buttons
        AddContact.setOnClickListener(this);
        ViewContact.setOnClickListener(this);
        SearchContact.setOnClickListener(this);
        EmergencyContacs.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.imageButtonAdd){//if the add contact button is pressed than intent the user to add contact activity
            Intent myintent = new Intent(MainActivity. this, AddContactActivity.class);
            startActivity(myintent);
        }
        else if(v.getId() == R.id.imageButtonView){//if the view contacts button is pressed than intent the user to view contact activity
            Intent myintent = new Intent(MainActivity. this, ViewContactActivity.class);
            startActivity(myintent);
        }
        else if(v.getId() == R.id.imageButtonSearch){//if the search contact button is pressed than intent the user to search contact activity
            Intent myintent = new Intent(MainActivity. this, SearchContactActivity.class);
            startActivity(myintent);
        }
        else if(v.getId() == R.id.imageButtonEmergency){//if the emergency info button is pressed than
            //check the internet connection
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if(networkInfo !=null && networkInfo.isConnected()){//if there is internet connection than
                //send an intent to browser to open up a webpage.
                Intent myintent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://goireland.about.com/od/preparingyourtrip/qt/irishemergency.htm"));
                startActivity(myintent);
            }else {//if there is no internet connection than notify the user
                Toast.makeText(MainActivity.this, "no network connection available", Toast.LENGTH_LONG).show();
            }
        }

    }//end OnClick

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


}//end class
