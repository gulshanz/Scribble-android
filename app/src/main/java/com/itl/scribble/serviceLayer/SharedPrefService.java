package com.itl.scribble.serviceLayer;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.itl.scribble.helperClasses.Keys;

public class SharedPrefService {
    private static SharedPrefService mInstance;
    public Object saveLoginStatus;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    public static String FAILURE_STRING;
    public static int FAILURE_INT;
    public static boolean FAILURE_BOOLEAN;
    public static long FAILURE_LONG;
    public static float FAILURE_FLOAT;
    private String TAG = "SharedPrefService";

    /**
     * Will be called only when there is no object of this class in memory
     */
    private SharedPrefService(Context context) {
        preferences = context.getApplicationContext().getSharedPreferences(Keys.PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        FAILURE_STRING = "N/A";
        FAILURE_INT = -1;
        FAILURE_BOOLEAN = false;
        FAILURE_LONG = -1;
        FAILURE_FLOAT = -1;
    }

    public static SharedPrefService getInstance(Context context) {
        if (mInstance == null)
            mInstance = new SharedPrefService(context);
        return mInstance;
    }

    public synchronized void storeSession(String session) {
        editor = preferences.edit();
        editor.putString(Keys.PREFERENCE_AUTH_KEY, session);
        editor.apply();
    }

    public String getSession() {
        try {
            return preferences.getString(Keys.PREFERENCE_AUTH_KEY, FAILURE_STRING);
        } catch (ClassCastException e) {
            Log.e(TAG, "Stored: session is NOT String");
        }
        return FAILURE_STRING;
    }

    public boolean isSessionPresent() {
        return !getSession().equals(SharedPrefService.FAILURE_STRING) && !getSession().isEmpty();
    }

    /**
     * Puts a String value into Shared Preference file
     *
     * @param key   The key for Shared Preferences
     * @param value Value to store
     */
    public synchronized void putStringValue(String key, String value) {
        editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Puts a boolean value into Shared Preference file
     *
     * @param key   The key for Shared Preferences
     * @param value Value to store
     */
    public synchronized void putBooleanValue(String key, boolean value) {
        editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * Puts a int value into Shared Preference file
     *
     * @param key   The key for Shared Preferences
     * @param value Value to store
     */
    public synchronized void putIntValue(String key, int value) {
        editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * Puts a long value into Shared Preference file
     *
     * @param key   The key for Shared Preferences
     * @param value Value to store
     */
    public synchronized void putLongValue(String key, long value) {
        editor = preferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * Puts a float value into Shared Preference file
     *
     * @param key   The key for Shared Preferences
     * @param value Value to store
     */
    public synchronized void putFloatValue(String key, float value) {
        editor = preferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    /**
     * Gets the String Value from the given Key
     *
     * @param key Key to find the value
     */
    public String getStringValue(String key) {
        try {
            return preferences.getString(key, FAILURE_STRING);
        } catch (ClassCastException e) {
            Log.e(TAG, "Stored: " + key + " is NOT String");
        }
        return FAILURE_STRING;
    }

    /**
     * Gets the boolean Value from the given Key
     *
     * @param key Key to find the value
     */
    public boolean getBooleanValue(String key) {
        try {
            return preferences.getBoolean(key, FAILURE_BOOLEAN);
        } catch (ClassCastException e) {
            Log.e(TAG, "Stored: " + key + " is NOT Boolean");
        }
        return FAILURE_BOOLEAN;
    }

    /**
     * Gets the int Value from the given Key
     *
     * @param key Key to find the value
     */
    public int getIntValue(String key) {
        try {
            return preferences.getInt(key, FAILURE_INT);
        } catch (ClassCastException e) {
            Log.e(TAG, "Stored: " + key + " is NOT Integer");
        }
        return FAILURE_INT;
    }

    /**
     * Gets the long Value from the given Key
     *
     * @param key Key to find the value
     */
    public long getLongValue(String key) {
        try {
            return preferences.getLong(key, FAILURE_LONG);
        } catch (ClassCastException e) {
            Log.e(TAG, "Stored: " + key + " is NOT Long");
            e.printStackTrace();
        }
        return FAILURE_LONG;
    }

    /**
     * Gets the float Value from the given Key
     *
     * @param key Key to find the value
     */
    public float getFloatValue(String key) {
        try {
            return preferences.getFloat(key, FAILURE_FLOAT);
        } catch (ClassCastException e) {
            Log.e(TAG, "Stored: " + key + " is NOT Float");
        }
        return FAILURE_FLOAT;
    }

    public void clearSharedPref() {
        editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * Registers the Given Listener to the Shared Preference
     *
     * @param listener Preference Change Listener to register
     */
    public void registerListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        preferences.registerOnSharedPreferenceChangeListener(listener);
    }

    /**
     * Un-Registers the Given Listener to the Shared Preference
     *
     * @param listener Preference Change Listener to un-register
     */
    public void unregisterListeners(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        preferences.unregisterOnSharedPreferenceChangeListener(listener);
    }

    public void saveLoginStatus() {
        putBooleanValue(Keys.LOGGED_IN, true);
    }

    public boolean getLoginStatus() {
        return getBooleanValue(Keys.LOGGED_IN);
    }
}
