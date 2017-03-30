package com.alp.library.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Map;
import java.util.Set;

public final class Prefs {

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public static void init(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
    }

    private static void init(Context context, String name, int mode) {
        preferences = context.getSharedPreferences(name, mode);
        editor = preferences.edit();
    }

    public static void save(String key, boolean value) {
        editor.putBoolean(key, value).apply();
    }

    public static void save(String key, String value) {
        editor.putString(key, value).apply();
    }

    public static void save(String key, int value) {
        editor.putInt(key, value).apply();
    }

    public static void save(String key, float value) {
        editor.putFloat(key, value).apply();
    }

    public static void save(String key, long value) {
        editor.putLong(key, value).apply();
    }

    public static void save(String key, Set<String> value) {
        editor.putStringSet(key, value).apply();
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return preferences.getBoolean(key, defValue);
    }

    public static String getString(String key, String defValue) {
        return preferences.getString(key, defValue);
    }

    public static int getInt(String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    public static float getFloat(String key, float defValue) {
        return preferences.getFloat(key, defValue);
    }

    public static long getLong(String key, long defValue) {
        return preferences.getLong(key, defValue);
    }

    public static Set<String> getStringSet(String key, Set<String> defValue) {
        return preferences.getStringSet(key, defValue);
    }

    public static Map<String, ?> getAll() {
        return preferences.getAll();
    }

    public static void remove(String key) {
        editor.remove(key).apply();
    }

    public static void removeAll() {
        editor.clear().apply();
    }

    public static boolean contains(String key) {
        return preferences.contains(key);
    }
}