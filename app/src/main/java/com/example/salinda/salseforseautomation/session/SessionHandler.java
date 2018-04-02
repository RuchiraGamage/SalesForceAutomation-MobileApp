package com.example.salinda.salseforseautomation.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.salinda.salseforseautomation.activity.LoginActivity;
import com.example.salinda.salseforseautomation.model.LoginModel;

public class SessionHandler {

    static SharedPreferences pref; // Shared Preferences
    Editor editor;  // Editor for Shared preferences
    Context _context;   // Context
    int PRIVATE_MODE = 0;   // Shared pref mode
    private static final String PREF_NAME = "Login";   // Sharedpref file name
    private static final String IS_LOGIN = "IsLoggedIn";    // All Shared Preferences Keys
    public static final String KEY_USERNAME = "name";   // User name (make variable public to access from outside)
    public static final String KEY_ID = "id"; // Email address (make variable public to access from outside)
    public static final String KEY_LASTNAME = "LastName";
    public static final String KEY_FIRSTNAME = "FirstName";
    public static final String KEY_EMAIL = "Email";
    public static final String KEY_IMAGE = "Image";
    public static final String KEY_TOKEN = "Token";
    public static final String IS_ONCLICK = "Isonclicked";

    // Constructor
    public SessionHandler(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    /**
     * Create onclick method
     */
    public void createOnclickSession(boolean onclick){
        editor.putBoolean(IS_ONCLICK, onclick);
        editor.commit();
    }

    /**
     * Quick check for clicked
     */
    public boolean isOnclicked(){
        return pref.getBoolean(IS_ONCLICK, false);
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String name, LoginModel respons){

        editor.putBoolean(IS_LOGIN, true);  // Storing login value as TRUE
        editor.putString(KEY_USERNAME, name);   // Storing name in pref
        editor.putInt(KEY_ID, respons.getId());   // Storing id in pref
        editor.putString(KEY_EMAIL, respons.getEmail());
        editor.putString(KEY_FIRSTNAME, respons.getFirstName());
        editor.putString(KEY_LASTNAME, respons.getLastName());
        editor.putString(KEY_IMAGE, respons.getImage());
        editor.putString(KEY_TOKEN, respons.getToken());
        editor.commit();    // commit changes
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){

            Intent i = new Intent(_context, LoginActivity.class);   // user is not logged in redirect him to Login Activity
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Closing all the Activities
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  // Add new Flag to start new Activity
            _context.startActivity(i);  // Staring Login Activity
        }
    }



    /**
     * Get stored session data
     * */
    /*public HashMap<String, String> getUserDetails(){

        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null)); // user name
        user.put(KEY_ID, Integer.toString(pref.getInt(KEY_ID, 0)));   // user id
        user.put(KEY_FIRSTNAME, pref.getString(KEY_FIRSTNAME, null));
        user.put(KEY_LASTNAME, pref.getString(KEY_LASTNAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        return user;    // return user
    }*/

    public static LoginModel getUserDetails(){
        LoginModel loginModel = new LoginModel();
        loginModel.setId(pref.getInt(KEY_ID, 0));
        loginModel.setEmail(pref.getString(KEY_EMAIL, null));
        loginModel.setFirstName(pref.getString(KEY_FIRSTNAME, null));
        loginModel.setLastName(pref.getString(KEY_LASTNAME, null));
        loginModel.setImage(pref.getString(KEY_IMAGE, null));
        loginModel.setToken(pref.getString(KEY_TOKEN,null));
        return loginModel;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        Intent i = new Intent(_context, LoginActivity.class);   // After logout redirect user to Login Activity
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Closing all the Activities
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  // Add new Flag to start new Activity
        _context.startActivity(i);  // Staring Login Activity
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
