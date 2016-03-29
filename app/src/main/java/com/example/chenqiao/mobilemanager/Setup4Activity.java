package com.example.chenqiao.mobilemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Setup4Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
        getSupportActionBar().hide();
    }


    public void previous(View v){
        startActivity(new Intent(this,Setup3Activity.class));
        overridePendingTransition(R.anim.slideoutright, R.anim.slideinleft);

    }

    public void next(View v){


        startActivity(new Intent(this, PhoneSafeActivity.class));
        overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);


    }
}
