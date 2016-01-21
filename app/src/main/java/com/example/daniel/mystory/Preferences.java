package com.example.daniel.mystory;

import android.content.SharedPreferences;

/**
 * Created by daniel on 18.1.16.
 */
public class Preferences {

    //Credentials related preferences
    public static final String PREFERENCE_USERNAME = "username";
    public static final String PREFERENCE_PASSWORD = "password";

    //Log related preferences
    public static final String PREFERENCE_LOGGED = "logged";


    public static final String getIsLogged(SharedPreferences prefs) {
        return prefs.getString(PREFERENCE_LOGGED, "no");
    }


}
