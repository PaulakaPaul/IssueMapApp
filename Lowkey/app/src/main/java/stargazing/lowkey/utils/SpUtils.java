package stargazing.lowkey.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import stargazing.lowkey.LowkeyApplication;

public class SpUtils {
    private SharedPreferences sharedPreferences;

    public SpUtils() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
                LowkeyApplication.instance.getApplicationContext()
        );
    }

    public void save(String key,int integer){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, integer);
        editor.apply();
    }

    public void save(String key,String string){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, string);
        editor.apply();
    }

    public void save(String key, boolean bool){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, bool);
        editor.apply();
    }

    public int loadInt(String key){
        return sharedPreferences.getInt(key, 0);
    }

    public String loadString(String key){
        return sharedPreferences.getString(key, null);
    }

    public boolean loadBoolean(String key){
        return sharedPreferences.getBoolean(key, false);
    }

}
