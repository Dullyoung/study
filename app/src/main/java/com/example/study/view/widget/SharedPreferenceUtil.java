package com.example.study.view.widget;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {
    public static void write(Context context,String key, boolean value){
     SharedPreferences   sharedPreferences=context.getSharedPreferences("UserData",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }



    public static boolean readBoolean(Context context,String key){
       SharedPreferences sharedPreferences=context.getSharedPreferences("UserData",Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key,false);
    }

}
