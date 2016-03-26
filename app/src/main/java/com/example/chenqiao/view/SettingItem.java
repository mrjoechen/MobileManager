package com.example.chenqiao.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.chenqiao.application.MyApplication;
import com.example.chenqiao.mobilemanager.R;

/**
 * Created by CHENQIAO on 2016/3/25.
 */
public class SettingItem extends RelativeLayout {


    private CheckBox cb_setting_status;
    private TextView tv_setting_itemstatus;
    private TextView tv_setting_itemtitle;
    private String itemtitle;
    private String on_string;
    private String off_string;
    private String sp_keyname;
    private String TAG = "SettingItem";


    public SettingItem(Context context) {
        super(context);
        initial(null);
    }



    public SettingItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initial(attrs);
    }


    private void initial(AttributeSet attrs) {

        View view=  View.inflate(getContext(), R.layout.check_item,null);
        tv_setting_itemtitle = (TextView) view.findViewById(R.id.tv_setting_itemtitle);
        tv_setting_itemstatus = (TextView) view.findViewById(R.id.tv_setting_itemstatus);
        cb_setting_status = (CheckBox) view.findViewById(R.id.cb_setting_status);


        if (attrs!=null){
            itemtitle = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "itemtitle");
            on_string = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "on_string");
            off_string = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "off_string");
            sp_keyname = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "sp_keyname");


            //初始化控件内的子控件
            tv_setting_itemtitle.setText(itemtitle);
            if(MyApplication.config_sp.getBoolean(sp_keyname,true)){
                tv_setting_itemstatus.setText(on_string);
                cb_setting_status.setChecked(true);
            }
            else{
                tv_setting_itemstatus.setText(off_string);
                cb_setting_status.setChecked(false);
            }
        }
        addView(view);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取的当前的状态
                boolean checked = cb_setting_status.isChecked();
                Log.i(TAG, checked + "");
                if (checked){
                    cb_setting_status.setChecked(false);
                    tv_setting_itemstatus.setText(off_string);
                    Log.i(TAG, checked + "取消");
//                    editor.putBoolean(sp_keyname,false);
//                    editor.commit();
                    MyApplication.saveConfig(sp_keyname,false);
                }
                else {
                    cb_setting_status.setChecked(true);
                    tv_setting_itemstatus.setText(on_string);
                    Log.i(TAG, checked + "开启");
//                    editor.putBoolean(sp_keyname, true);
//                    editor.commit();
                    MyApplication.saveConfig(sp_keyname,true);

                }
            }
        });
    }

}
