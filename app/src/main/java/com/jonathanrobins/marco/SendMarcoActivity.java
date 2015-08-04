package com.jonathanrobins.marco;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class SendMarcoActivity extends ActionBarActivity {

    private Button backButton;
    private TextView mainText;
    private TextView recipientText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_send_marco);

        backButton = (Button) findViewById(R.id.backButton);
        mainText = (TextView) findViewById(R.id.titleText);
        recipientText = (TextView) findViewById(R.id.recipientText);

        Intent i = getIntent();
        ArrayList<String> list = i.getStringArrayListExtra("list");
        recipientText.setText("");
        for (String name : list) {
            recipientText.append(name + "\n");
        }

        Typeface typeface = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        mainText.setTypeface(typeface);
        recipientText.setTypeface(typeface);

        focusAndOnClickLogic();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_send_marco, menu);
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
    }
}
