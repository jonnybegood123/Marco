package com.jonathanrobins.marco;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.parse.ParseUser;


public class SettingsActivity extends ActionBarActivity {

    private Button backButton;
    private Button resetPasswordButton;
    private Button logOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_settings);

        //references
        backButton = (Button) findViewById(R.id.backButton);
        resetPasswordButton = (Button) findViewById(R.id.resetPasswordButton);
        logOutButton = (Button) findViewById(R.id.logOutButton);

        focusAndOnClickLogic();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("RESETTING PASSWORD!!");
            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                backDialog();
            }
        });

    }

    public void backDialog() {
        new AlertDialog.Builder(this)
                .setCancelable(false)
                        //.setTitle("Are you sure you want to log out?")
                .setMessage("Are you sure you want to log out?")
                        //yes
                .setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ParseUser.logOut();
                        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                        intent.putExtra("finish", true);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                })
                        //no
                .setNegativeButton("No.", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //stays
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
