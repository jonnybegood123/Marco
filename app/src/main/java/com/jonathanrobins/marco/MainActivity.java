package com.jonathanrobins.marco;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseObject;

import org.w3c.dom.Text;

import java.sql.Timestamp;

public class MainActivity extends ActionBarActivity {

    private Button marcoButton;
    private Button addAFriendButton;
    private TextView titleTextView;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        //references logic
        marcoButton = (Button) findViewById(R.id.marcoButton);
        addAFriendButton = (Button) findViewById(R.id.addAFriendButton);
        titleTextView = (TextView) findViewById(R.id.titleTextView);

        //title logic
        titleTextView.setText("Hey " + CurrentUser.getUsername().toUpperCase() + "!");

        //typography logic
        Typeface typeface = Typeface.createFromAsset(getAssets(), "PlayfairDisplaySC-Italic.ttf");
        marcoButton.setTypeface(typeface);
        typeface = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        addAFriendButton.setTypeface(typeface);
        mp = MediaPlayer.create(this, R.raw.marco);

        //database
        Parse.initialize(this, "UqB3jPRcavR8nxYZB1SlXismTfMZyGtBFzDHmBt0", "2WEttFIiHPJ7AzKffCEkXsG81mOxBlZvsq15mnQl");

        focusAndOnClickLogic();
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

    public void focusAndOnClickLogic() {
        marcoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mp.start();

                java.util.Date date = new java.util.Date();
                ParseObject testObject = new ParseObject("Test");
                testObject.put("Person", CurrentUser.getUsername());
                testObject.saveInBackground();
            }
        });

        addAFriendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddAFriendActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
