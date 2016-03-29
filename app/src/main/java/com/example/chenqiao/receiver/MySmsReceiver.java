package com.example.chenqiao.receiver;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.util.Log;

import com.example.chenqiao.application.MyApplication;
import com.example.chenqiao.mobilemanager.R;
import com.example.chenqiao.service.MyUpdateLocationService;

public class MySmsReceiver extends BroadcastReceiver {
    private static final String TAG ="MySmsReceiver" ;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i(TAG, "onReceive ");

        Object[] objs = (Object[]) intent.getExtras().get("pdus");
        for (Object obj : objs) {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
            String body = smsMessage.getMessageBody();
            String sender = smsMessage.getOriginatingAddress();

            Log.i(TAG,"onReceive " +body);

            if (body.equals("*#alarm#*")){
                playalarm(context);
            }
            else if (body.equals("*#location#*")){
                getlocation(context);
            }else if (body.equals("*#wipedata#*")){
                wipedata(context);
            }else if (body.equals("*#lockscreen#*")){
                lockscreen(context);
            }
        }

    }

    //激活管理员权限。
    private void lockscreen(Context ctx) {
        Log.i(TAG,"lockscrenn");
        //在此实现锁屏
        DevicePolicyManager mDPM =
                (DevicePolicyManager)ctx.getSystemService(Context.DEVICE_POLICY_SERVICE);
        mDPM.lockNow();
        mDPM.resetPassword("123",0);
    }

    //
    private void wipedata(Context ctx) {
        Log.i(TAG,"wipedata");
        //在此实现wipedata
        DevicePolicyManager mDPM =
                (DevicePolicyManager)ctx.getSystemService(Context.DEVICE_POLICY_SERVICE);

        mDPM.wipeData(0);
    }

    private void getlocation(Context ctx) {
        Log.i(TAG, "getlocation");
        //

        ctx.startService(new Intent(ctx, MyUpdateLocationService.class));


        final String longitude = MyApplication.config_sp.getString("longitude", "");

        final String latitude = MyApplication.config_sp.getString("latitude", "");

        Log.i("getlocation",longitude+"--"+latitude);
        //通过smsManager 把位置信息发给安全号码。

    }

    private void playalarm(Context ctx) {
        Log.i(TAG,"playalarm");
        MediaPlayer mediaPlayer =   MediaPlayer.create(ctx, R.raw.alarm);
        //开始播放音频
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }


}
