package com.example.chenqiao.application;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by CHENQIAO on 2016/3/25.
 */

public class MyApplication extends Application{


    public SharedPreferences config_sp;
    public static String SERVER_PATH = "http://192.168.3.10:8080/Test/MobileManager";

    @Override
    public void onCreate() {
        super.onCreate();

        config_sp = getSharedPreferences("config", MODE_PRIVATE);
    }
}
