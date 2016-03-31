package com.example.chenqiao.mobilemanager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;

import com.example.chenqiao.application.MyApplication;

public class SettingActivity extends Activity {


    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        sp= MyApplication.config_sp;
        editor =sp.edit();



    }


    public void settoastlocation(View v){


        startActivity(new Intent(this, SetToastLocationActivity.class));
    }
}
