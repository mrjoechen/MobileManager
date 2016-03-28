package com.example.chenqiao.mobilemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chenqiao.application.MyApplication;
import com.example.chenqiao.utils.Md5Utils;

public class MainActivity extends Activity {

    private static final int CONTENT_NUM = 9 ;
    private GridView gv_home_content;


    private int[] iconarray ={R.drawable.safe,R.drawable.callmsgsafe,R.drawable.appmanager
            ,    R.drawable.taskmanager,R.drawable.netmanager,R.drawable.trojan,
            R.drawable.sysoptimize,R.drawable.atools,R.drawable.settings};

    private  String[] titles={"手机防盗","通讯卫士","软件管理",
            "进程管理","流量统计","手机杀毒",
            "缓存清理","高级工具","设置中心"};
    private TextView tv_home_welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        tv_home_welcome = (TextView) findViewById(R.id.tv_home_welcome);
        tv_home_welcome.setText("天气情况：");

        gv_home_content = (GridView) findViewById(R.id.gv_home_content);
        gv_home_content.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return CONTENT_NUM;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = View.inflate(MainActivity.this,R.layout.gridview_item,null);
                ImageView iv_gv_icon = (ImageView) view.findViewById(R.id.iv_gv_icon);
                TextView tv = (TextView) view.findViewById(R.id.tv_gv_name);
                iv_gv_icon.setImageResource(iconarray[position]);
                tv.setText(titles[position]);


                return view;
            }
        });

        gv_home_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
//                        Toast.makeText(MainActivity.this, titles[position], Toast.LENGTH_SHORT).show();
                        String safe_pwd = MyApplication.config_sp.getString("safe_pwd", "");
                        if (safe_pwd.isEmpty()){
                            //如果配置信息中安全密码为空，则弹出设置密码对话框
                            showSetpwdDialog();
                        }
                        else
                        {
                            //如果已经设置过密码，弹出输入密码对话框
                            showinputpwdDialog();
                        }
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this,titles[position],Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this,titles[position],Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(MainActivity.this,titles[position],Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(MainActivity.this,titles[position],Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Toast.makeText(MainActivity.this,titles[position],Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        Toast.makeText(MainActivity.this,titles[position],Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        Toast.makeText(MainActivity.this,titles[position],Toast.LENGTH_SHORT).show();
                        break;
                    case 8:
                        //Toast.makeText(MainActivity.this,titles[position],Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                        break;
                }
            }
        });


    }

    //设置安全密码
    private void showSetpwdDialog() {

        View view = View.inflate(this, R.layout.setpwd_dialog, null);
        final EditText et_setpwddialog_input = (EditText) view.findViewById(R.id.et_setpwddialog_input);
        final EditText  et_setpwddialog_confirm = (EditText) view.findViewById(R.id.et_setpwddialog_confirm);
        final AlertDialog setpwd_alertDialog = new AlertDialog.Builder(this).setTitle("请设置密码").setView(view).create();
        et_setpwddialog_input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    setpwd_alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });
        et_setpwddialog_confirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    setpwd_alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });
        setpwd_alertDialog.show();

        view.findViewById(R.id.bt_setpwddialog_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputpwd = et_setpwddialog_input.getText().toString();
                String confirmpwd = et_setpwddialog_confirm.getText().toString();

                if (!inputpwd.isEmpty() & !confirmpwd.isEmpty()) {
                    if (inputpwd.equals(confirmpwd)) {
                        MyApplication.saveConfig("safe_pwd", Md5Utils.getMd5Digest(inputpwd));
                        setpwd_alertDialog.dismiss();
                    } else {
                        Toast.makeText(MainActivity.this, "密码不一致！", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(MainActivity.this, "密码不能为空!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        view.findViewById(R.id.bt_setpwddialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setpwd_alertDialog.dismiss();
            }
        });

    }


    //输入密码，检查与配置信息是否匹配
    private void showinputpwdDialog() {

        View view = View.inflate(this, R.layout.inputpwd_dialog,null);
        final EditText et_inputdialog_input = (EditText) view.findViewById(R.id.et_inputdialog_inputpwd);
        final AlertDialog inputpwd_alertDialog = new AlertDialog.Builder(this).setTitle("请输入密码").setView(view).create();
        inputpwd_alertDialog.show();
        et_inputdialog_input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    inputpwd_alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });
        view.findViewById(R.id.bt_inputpwddialog_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!et_inputdialog_input.getText().toString().isEmpty()){

                    if(MyApplication.config_sp.getString("safe_pwd","").equals(Md5Utils.getMd5Digest(et_inputdialog_input.getText().toString()))){
                        inputpwd_alertDialog.dismiss();
                        startActivity(new Intent(MainActivity.this,PhoneSafeActivity.class));
                    }else {
                        Toast.makeText(MainActivity.this, "密码错误，请重新输入", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(MainActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

        view.findViewById(R.id.bt_inputpwddialog_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputpwd_alertDialog.dismiss();
            }
        });


    }
}
