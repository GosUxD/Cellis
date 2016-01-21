package com.example.daniel.mystory;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.parse.Parse;

/**
 * Created by daniel on 18.1.16.
 */
public class MyStoryApp extends Application {

    public static final String INTENT_ACTION_LOGED_OUT = "com.example.daniel.mystory.intent.action.logged.out";
    public static final String INTENT_ACTION_LOGED_IN = "com.example.daniel.mystory.intent.action.logged.in";

    private static SharedPreferences mPrefs;


    @Override
    public void onCreate() {
        super.onCreate();
        //Parse
        Parse.enableLocalDatastore(this);

        Parse.initialize(this);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public static String getIsLogged() {
        return Preferences.getIsLogged(mPrefs);
    }


}
