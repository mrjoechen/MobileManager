package com.example.chenqiao.mobilemanager;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

public class SettingActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().hide();
    }
}
