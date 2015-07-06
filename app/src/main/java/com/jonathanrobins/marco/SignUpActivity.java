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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignUpActivity extends ActionBarActivity {

    private TextView signUpTitle;
    private EditText usernameTextField;
    private EditText passwordTextField;
    private EditText confirmPasswordTextField;
    private Button signUpButton;
    private Button logInButton;
    private RelativeLayout mainLayout;

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

        //check if user already logged in
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            CurrentUser.setUsername(currentUser.getUsername());
            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
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

                //for checking special characters
                Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(username);
                boolean b = m.find();

                //sign up info checking
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(getApplicationContext(),
                            "Password don't match.", Toast.LENGTH_LONG)
                            .show();
                } else if (username.contains(" ")) {
                    Toast.makeText(getApplicationContext(),
                            "Username cannot contain spaces.", Toast.LENGTH_LONG)
                            .show();
                } else if (username.length() < 6) {
                    Toast.makeText(getApplicationContext(),
                            "Username must be atleast 6 characters.", Toast.LENGTH_LONG)
                            .show();
                }
                else if (username.length() > 20) {
                    Toast.makeText(getApplicationContext(),
                            "Username cannot be greater than 20 characters.", Toast.LENGTH_LONG)
                            .show();
                }
                else if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(),
                            "Password must be atleast 6 characters.", Toast.LENGTH_LONG)
                            .show();
                } else if (b) {
                    Toast.makeText(getApplicationContext(),
                            "Usernames may only consist of letters and numbers.", Toast.LENGTH_LONG)
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
                finish();
            }
        });
    }
}
