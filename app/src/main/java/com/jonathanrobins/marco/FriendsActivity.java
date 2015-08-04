package com.jonathanrobins.marco;

import android.content.Intent;
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
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class FriendsActivity extends ActionBarActivity {

    private RelativeLayout mainLayout;
    private TextView titleText;
    private EditText enterFriendsUsernameTextField;
    private Button backButton;
    private Button addAFriendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_friends);

        //references
        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        titleText = (TextView) findViewById(R.id.titleTextView);
        enterFriendsUsernameTextField = (EditText) findViewById(R.id.enterFriendsUsernameTextField);
        addAFriendButton = (Button) findViewById(R.id.addAFriendButton);
        backButton = (Button) findViewById(R.id.backButton);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        titleText.setTypeface(typeface);
        enterFriendsUsernameTextField.setTypeface(typeface);
        addAFriendButton.setTypeface(typeface);

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
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        addAFriendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ParseQuery<ParseUser> query = ParseUser.getQuery();
                String friendToAdd = enterFriendsUsernameTextField.getText().toString().trim().toLowerCase();
                ParseUser currentUser = ParseUser.getCurrentUser();
                //checks that you didn't enter your own name
                if (!currentUser.getUsername().equalsIgnoreCase(friendToAdd)) {
                    //finds user with username entered in
                    query.whereEqualTo("username", friendToAdd);
                    query.findInBackground(new FindCallback<ParseUser>() {
                        public void done(final List<ParseUser> users, ParseException e) {
                            if (e == null) {
                                if (users.size() > 0) {
                                    //finds friends of entered user
                                    ParseQuery<ParseObject> query = ParseQuery.getQuery("FriendsList");
                                    query.whereEqualTo("friends", users.get(0));
                                    query.findInBackground(new FindCallback<ParseObject>() {
                                        public void done(List<ParseObject> friends, ParseException e) {
                                            if (e == null) {
                                                if (friends.size() == 0) {
                                                    //check that they're not already friends
                                                    ParseQuery<ParseObject> query = ParseQuery.getQuery("FriendsList");
                                                    query.include("friend1");
                                                    query.include("friend2");
                                                    query.findInBackground(new FindCallback<ParseObject>() {
                                                        public void done(List<ParseObject> friendsList, ParseException e) {
                                                            boolean alreadyFriends = false;
                                                            ParseUser currentUser = ParseUser.getCurrentUser();
                                                            //ACL
                                                            ParseACL postACL = new ParseACL(ParseUser.getCurrentUser());
                                                            postACL.setPublicReadAccess(false);
                                                            postACL.setPublicWriteAccess(false);
                                                            postACL.setReadAccess(currentUser, true);
                                                            postACL.setWriteAccess(currentUser, true);
                                                            postACL.setReadAccess(users.get(0), true);
                                                            postACL.setWriteAccess(users.get(0), true);
                                                            if (e == null) {
                                                                for (ParseObject friend : friendsList) {
                                                                    ParseUser user1 = (ParseUser) friend.get("friend1");
                                                                    String userName1 = user1.getUsername();
                                                                    ParseUser user2 = (ParseUser) friend.get("friend2");
                                                                    String userName2 = user2.getUsername();
                                                                    if (((userName1.equalsIgnoreCase(currentUser.getUsername()) && userName2.equalsIgnoreCase(users.get(0).getUsername()))
                                                                            ||
                                                                            (userName1.equalsIgnoreCase(users.get(0).getUsername()) && userName2.equalsIgnoreCase(currentUser.getUsername())))) {
                                                                        alreadyFriends = true;
                                                                        break;
                                                                    }
                                                                }
                                                                if (!alreadyFriends) {
                                                                    //add new friend relation for users if not already friends
                                                                    ParseObject newFriendRelation = new ParseObject("FriendsList");
                                                                    newFriendRelation.put("friend1", currentUser);
                                                                    newFriendRelation.put("friend2", users.get(0));
                                                                    newFriendRelation.setACL(postACL);

                                                                    try {
                                                                        newFriendRelation.save();
                                                                        Intent intent = new Intent(FriendsActivity.this, MainActivity.class);
                                                                        startActivity(intent);
                                                                        finish();
                                                                        Toast.makeText(getApplicationContext(),
                                                                                "Person has been added to your friends list!",
                                                                                Toast.LENGTH_LONG).show();
                                                                    } catch (ParseException e2) {
                                                                        //   e.printStackTrace();
                                                                    }
                                                                } else {
                                                                    Toast.makeText(getApplicationContext(),
                                                                            "User is already added to your friends list!",
                                                                            Toast.LENGTH_LONG).show();
                                                                }
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    });
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),
                                            "Couldn't find user you're looking for.",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(),
                            "You can't add yourself to your friends list, silly!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
