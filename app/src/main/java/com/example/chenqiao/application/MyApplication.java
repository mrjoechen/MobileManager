package com.example.chenqiao.application;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by CHENQIAO on 2016/3/25.
 */

public class MyApplication extends Application{



    public static String SERVER_PATH = "http://192.168.3.16:8080/Test/MobileManager";
    public static SharedPreferences config_sp ;
    private static SharedPreferences.Editor editor;

    @Override
    public void onCreate() {
        super.onCreate();

        config_sp = getSharedPreferences("config", MODE_PRIVATE);
        editor = config_sp.edit();
    }

    public static void saveConfig(String key ,String value){
        editor.putString(key,value);
        editor.commit();
    }

    public static void saveConfig(String key ,int value){
        editor.putInt(key, value);
        editor.commit();
    }

    public static void saveConfig(String key ,boolean value){
        editor.putBoolean(key, value);
        editor.commit();
    }
}
