package com.example.androidapplication.contacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends Activity {
    //Declare TextView and ImageView
    TextView AppName1,AppName2,CopyRight;
    ImageView AppImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //initialize them to appropriate id
        AppName1= (TextView) findViewById(R.id.textViewAppName1);
        AppName2= (TextView) findViewById(R.id.textViewAppName2);
        CopyRight= (TextView) findViewById(R.id.textViewCopyRight);
        AppImg =(ImageView)findViewById(R.id.imageViewAppImg);

        Thread thread = new Thread(){//create a thread
            public void run(){
                try {
                    sleep(2000);//show the splash screen for 2 seconds than show main activity
                    Intent myintent = new Intent(SplashActivity. this, MainActivity.class);
                    startActivity(myintent);
                    finish();//finish this activity
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }//end run
        };
        thread.start();//start the thread

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
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
