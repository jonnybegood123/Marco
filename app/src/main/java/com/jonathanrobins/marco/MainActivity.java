package com.jonathanrobins.marco;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    private Button marcoButton;
    private Button addAFriendButton;
    private Button settingsButton;
    private TextView titleTextView;
    private MediaPlayer mp;
    private ListView friendsList;
    private CustomListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        //checks if logging out
        boolean finish = getIntent().getBooleanExtra("finish", false);
        if (finish) {
            startActivity(new Intent(MainActivity.this, LogInActivity.class));
            finish();
            return;
        }

        //references logic
        marcoButton = (Button) findViewById(R.id.marcoButton);
        addAFriendButton = (Button) findViewById(R.id.addAFriendButton);
        settingsButton = (Button) findViewById(R.id.settingsButton);
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        friendsList = (ListView) findViewById(R.id.friendsList);

        //title logic
        titleTextView.setText(CurrentUser.getUsername().toUpperCase() + " !");

        //typography logic
        Typeface typeface = Typeface.createFromAsset(getAssets(), "PlayfairDisplaySC-Italic.ttf");
        marcoButton.setTypeface(typeface);
        typeface = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        addAFriendButton.setTypeface(typeface);
        mp = MediaPlayer.create(this, R.raw.marco);

        //friends list configurations
        friendsList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayList<RowItem> list = new ArrayList<RowItem>();
        RowItem rowItem1 = new RowItem(R.drawable.icon, "Jonathan Robins", R.drawable.icon);
        list.add(rowItem1);
        list.add(rowItem1);
        list.add(rowItem1);
        list.add(rowItem1);
        list.add(rowItem1);
        adapter = new CustomListViewAdapter(this, R.layout.row_item, list);
        adapter.selectedRowsItems = new int[list.size()];
        friendsList.setAdapter(adapter);

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
            }
        });
        settingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        friendsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                //ListEntry entry = (ListEntry) parent.getItemAtPosition(position);

                //retrieve row item parent layout
                RelativeLayout layoutParent = (RelativeLayout) view;
                //retrieve checkbox imageview from layout
                ImageView checkbox = (ImageView) layoutParent.getChildAt(2);

                //set checkbox. If 0, unselected. If 1, selected.
                if (adapter.selectedRowsItems[position] == 0) {
                    checkbox.setImageResource(R.drawable.pepeicon);
                    adapter.selectedRowsItems[position] = 1;
                } else {
                    checkbox.setImageResource(R.drawable.icon);
                    adapter.selectedRowsItems[position] = 0;
                }
            }
        });
    }
}
