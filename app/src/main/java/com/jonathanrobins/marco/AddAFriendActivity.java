package com.jonathanrobins.marco;

import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.Parse;


public class AddAFriendActivity extends ActionBarActivity {

    private RelativeLayout mainLayout;
    private TextView titleText;
    private EditText enterFriendsUsernameTextField;
    private Button addAFriendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_afriend);

        //references
        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        titleText = (TextView) findViewById(R.id.titleTextView);
        enterFriendsUsernameTextField = (EditText) findViewById(R.id.enterFriendsUsernameTextField);
        addAFriendButton = (Button) findViewById(R.id.addAFriendButton);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        titleText.setTypeface(typeface);
        enterFriendsUsernameTextField.setTypeface(typeface);
        addAFriendButton.setTypeface(typeface);

        //database
        Parse.initialize(this, "UqB3jPRcavR8nxYZB1SlXismTfMZyGtBFzDHmBt0", "2WEttFIiHPJ7AzKffCEkXsG81mOxBlZvsq15mnQl");

        addAFriendButton.setVisibility(View.INVISIBLE);
        enterFriendsUsernameTextField.addTextChangedListener(filterTextWatcher);

        focusAndOnClickLogic();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_afriend, menu);
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

    private TextWatcher filterTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (enterFriendsUsernameTextField.getText().length() > 0) {
                addAFriendButton.setVisibility(View.VISIBLE);
            } else {
                addAFriendButton.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    public void focusAndOnClickLogic() {
        mainLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });

        addAFriendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("AYYY LMAO");
            }
        });
    }

}
