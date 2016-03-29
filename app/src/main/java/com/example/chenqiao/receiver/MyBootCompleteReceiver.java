package com.example.chenqiao.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.chenqiao.application.MyApplication;

public class MyBootCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        Log.i("MyBootCompleteReceiver", intent.getAction());

        final boolean anti_theif  = MyApplication.config_sp.getBoolean("anti_theif", true);

        Log.i("MyBootCompleteReceiver", anti_theif+"");


        if(anti_theif) {

            final String imsi_saved = MyApplication.config_sp.getString("imsi", "");

            TelephonyManager mTelmanager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);

            String imsi_current = mTelmanager.getSimSerialNumber(); //IMSI


            Log.i("MyBootCompleteReceiver", imsi_current + "----" + imsi_saved);

            if (!imsi_saved.equals(imsi_current)){


                SmsManager smsManager = SmsManager.getDefault();

                final String  safenum  = MyApplication.config_sp.getString("safenum", "");

                smsManager.sendTextMessage("5556",null,"你的手机被换卡了",null,null);

            }

        }
    }
}
