package com.example.daniel.mystory;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by daniel on 18.1.16.
 */
public class LoginActivity extends Activity {


    private static final String TAG = "LoginActivity";

    private Button mLoginButton;
    private TextView mSignUp;
    private EditText mUsername, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUI();
    }

    private void initUI() {
        mUsername = (EditText) findViewById(R.id.activity_login_username_edit_text);
        mPassword = (EditText) findViewById(R.id.activity_login_pass_edit_text);

        mLoginButton = (Button) findViewById(R.id.activity_login_login_button);
        mLoginButton.setTransformationMethod(null);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logInInBackground(
                        mUsername.getText().toString(),
                        mPassword.getText().toString(),
                        new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if (e == null) {
                                    Log.i(TAG, "Hello " + mUsername.getText().toString());
                                } else {
                                    Log.i(TAG, "Error " + e.toString());

                                }
                            }
                        });
            }
        });
        mSignUp = (TextView) findViewById(R.id.activity_login_signup_text_view);
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null) {
                            Log.i(TAG, "Logged out");
                        } else {
                            Log.i(TAG, "Error " + e.toString());
                        }
                    }
                });
            }
        });
    }

}
