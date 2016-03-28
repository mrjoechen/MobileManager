package com.example.chenqiao.mobilemanager;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chenqiao.application.MyApplication;

public class PhoneSafeActivity extends Activity {


    private TextView tv_phonesafe_safenum;
    private ImageView iv_phonesafe_enalbe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_safe);

        tv_phonesafe_safenum = (TextView) findViewById(R.id.tv_phonesafe_safenum);
        iv_phonesafe_enalbe =(ImageView)findViewById(R.id.iv_phonesafe_enalbe);
        //判断是否已经设置过防盗信息：通过设置向导会生成一个绑定sim卡信息，如果该信息存在则视为已经设置过
        //如果0xml里没有该项，返回默认值。
        String safenum= MyApplication.config_sp.getString("safenum","");
        if (safenum.isEmpty()){
            startActivity(new Intent(this,Setup1Activity.class));
        }else{
            //显示安全号码
            tv_phonesafe_safenum.setText(safenum);
            //显示是否已经开启本功能
            boolean flag= MyApplication.config_sp.getBoolean("anti_theif", true);
            if (flag){
                iv_phonesafe_enalbe.setImageResource(R.drawable.lock);
            }else {
                iv_phonesafe_enalbe.setImageResource(R.drawable.unlock);
            }
        }
    }
    public void resetup(View v){
        startActivity(new Intent(this, Setup1Activity.class));
    }
}
