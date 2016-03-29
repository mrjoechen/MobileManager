package com.example.chenqiao.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chenqiao.dao.NumberLoactionDao;
import com.example.chenqiao.mobilemanager.R;

public class MyNumberLocationService extends Service {
    private static final String TAG = "MyNumberLocationService";
    private WindowManager mWM;
    private View v;
    public MyNumberLocationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {

        final TelephonyManager telmar = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        telmar.listen(new MyPhoneStateListner(), PhoneStateListener.LISTEN_CALL_STATE);
        Log.i(TAG, "onCreate");
        super.onCreate();
    }
    class MyPhoneStateListner extends PhoneStateListener {

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);

            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    hideLocationVIew();
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    String location = NumberLoactionDao.getNumberLocation(incomingNumber, MyNumberLocationService.this);
                    Log.i(TAG, location);
                    //号码显示
                    Toast.makeText(MyNumberLocationService.this, location, Toast.LENGTH_SHORT).show();
                    showLocationView(location);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
            }
        }
    }

    private void showLocationView(String location) {

        LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflate.inflate(R.layout.mynumberlocation, null);
        TextView message = (TextView) v.findViewById(R.id.message);
        message.setText(location);
        mWM = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.format = PixelFormat.TRANSLUCENT;
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        mWM.addView(v, params);
    }

    private void hideLocationVIew() {
        if (mWM != null) {
            mWM.removeView(v);
        }
    }
}
