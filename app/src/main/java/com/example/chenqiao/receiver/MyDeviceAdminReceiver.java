package com.example.chenqiao.receiver;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by CHENQIAO on 2016/3/29.
 */
public class MyDeviceAdminReceiver extends DeviceAdminReceiver {


    void showToast(Context context, String msg) {
        String status = msg;
        Toast.makeText(context, status, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onEnabled(Context context, Intent intent) {
        showToast(context, "admin_receiver_status_enabled");
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        showToast(context, "admin_receiver_status_disabled");
    }
}