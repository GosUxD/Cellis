package com.example.daniel.mystory;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;

import com.parse.Parse;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    MyStoryApp MyStoryApp = (MyStoryApp) getApplication();

    private BroadcastReceiver mLoggedOutReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "On Receive:" + intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registerReceiver(mLoggedOutReceiver, new IntentFilter(MyStoryApp.INTENT_ACTION_LOGED_OUT));
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        if(ParseUser.getCurrentUser() == null) {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        } else {
            Log.i(TAG, "User " + ParseUser.getCurrentUser().getUsername().toString());
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mLoggedOutReceiver);
    }
}
