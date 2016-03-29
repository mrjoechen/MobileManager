package com.example.chenqiao.mobilemanager;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.chenqiao.application.MyApplication;
import com.example.chenqiao.view.SettingItem;

public class Setup2Activity extends BaseActivity {

    private String Tag = "Setup2Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);
        getSupportActionBar().hide();
        SettingItem si_setup2_bindsim = (SettingItem) findViewById(R.id.si_setup2_bindsim);

        si_setup2_bindsim.setMyOnclickListenr(new SettingItem.MyOnclickListen() {
            @Override
            public void myCheckOnclick() {
                Log.i(Tag, "myonclick executed");
                //绑定SIM卡的业务逻辑
                //如何判断两个sim卡不一样
                TelephonyManager mTelmanager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                String imsi = mTelmanager.getSimSerialNumber(); //IMSI
                //Toast.makeText(Setup2Activity.this, ims, Toast.LENGTH_SHORT).show();
                MyApplication.saveConfig("imsi", imsi);
            }
            @Override
            public void myCancleOnclick() {
                MyApplication.saveConfig("imsi", "");
            }
        });
    }


    public  void previous(View v){

        startActivity(new Intent(this,Setup1Activity.class));
        overridePendingTransition( R.anim.slideinleft,R.anim.slideoutright);

    }

    public  void next(View v){
        String imsi = MyApplication.config_sp.getString("imsi", "");
        if (!imsi.isEmpty()) {
            startActivity(new Intent(this, Setup3Activity.class));
            overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);
        }
        else
            Toast.makeText(Setup2Activity.this, "请绑定sim卡！否则无法使用本功能!", Toast.LENGTH_SHORT).show();
    }
}
