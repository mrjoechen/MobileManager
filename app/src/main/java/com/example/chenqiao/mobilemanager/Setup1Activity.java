package com.example.chenqiao.mobilemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Setup1Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);
        getSupportActionBar().hide();
    }

    public void next(View v){
        startActivity(new Intent(this, Setup2Activity.class));
        overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);
    }

    @Override
    public void previous(View v) {

    }

}
