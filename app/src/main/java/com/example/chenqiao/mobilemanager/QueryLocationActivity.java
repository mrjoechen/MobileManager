package com.example.chenqiao.mobilemanager;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chenqiao.dao.NumberLoactionDao;

public class QueryLocationActivity extends ActionBarActivity {

    private EditText et_querylocation_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_location);
        et_querylocation_num = (EditText) findViewById(R.id.et_querylocation_num);
    }


    public void query(View v){

        final String s = et_querylocation_num.getText().toString();
        final String numberLocation = NumberLoactionDao.getNumberLocation(s, this);
        Toast.makeText(QueryLocationActivity.this, numberLocation, Toast.LENGTH_SHORT).show();

    }
}
