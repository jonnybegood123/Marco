package com.jonathanrobins.marco;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.text.ParseException;


public class SignUpActivity extends ActionBarActivity {

    TextView signUpTitle;
    EditText usernameTextField;
    EditText passwordTextField;
    EditText confirmPasswordTextField;
    Button signUpButton;
    Button logInButton;
    RelativeLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        //references
        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        usernameTextField = (EditText) findViewById(R.id.usernameTextField);
        passwordTextField = (EditText) findViewById(R.id.passwordTextField);
        confirmPasswordTextField = (EditText) findViewById(R.id.confirmPasswordTextField);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        logInButton = (Button) findViewById(R.id.logInButton);
        signUpTitle = (TextView) findViewById(R.id.signUpTitle);

        //text modifications
        Typeface typeface = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        usernameTextField.setTypeface(typeface);
        passwordTextField.setTypeface(typeface);
        confirmPasswordTextField.setTypeface(typeface);
        signUpTitle.setTypeface(typeface);
        signUpButton.setTypeface(typeface);
        logInButton.setTypeface(typeface);

        //set focus/click methods
        focusAndOnClickLogic();

        //database
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "UqB3jPRcavR8nxYZB1SlXismTfMZyGtBFzDHmBt0", "2WEttFIiHPJ7AzKffCEkXsG81mOxBlZvsq15mnQl");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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
        mainLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String username = usernameTextField.getText().toString();
                final String password = passwordTextField.getText().toString();
                final String confirmPassword = confirmPasswordTextField.getText().toString();
                ParseUser user = new ParseUser();
                user.setUsername(username.toLowerCase());
                user.setPassword(password);
                //sign up info checking
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(getApplicationContext(),
                            "Password don't match.", Toast.LENGTH_LONG)
                            .show();
                } else if (username.contains(" ")) {
                    Toast.makeText(getApplicationContext(),
                            "Username cannot contain spaces.", Toast.LENGTH_LONG)
                            .show();
                } else if (username.length() < 8) {
                    Toast.makeText(getApplicationContext(),
                            "Username must be atleast 8 characters.", Toast.LENGTH_LONG)
                            .show();
                } else if (password.length() < 8) {
                    Toast.makeText(getApplicationContext(),
                            "Password must be atleast 8 characters.", Toast.LENGTH_LONG)
                            .show();
                } else {
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(com.parse.ParseException e) {
                            if (e == null) {
                                // Show a simple Toast message upon successful registration
                                Toast.makeText(getApplicationContext(),
                                        "Successfully signed up, please log in.",
                                        Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Username is already taken.", Toast.LENGTH_LONG)
                                        .show();
                            }
                        }
                    });
                }
            }
        });
        logInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });
    }
}
