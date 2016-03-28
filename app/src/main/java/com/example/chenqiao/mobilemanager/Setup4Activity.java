package com.example.chenqiao.mobilemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Setup4Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
    }


    public void previous(View v){
        startActivity(new Intent(this,Setup3Activity.class));

    }

    public void next(View v){


        startActivity(new Intent(this, PhoneSafeActivity.class));


    }
}
