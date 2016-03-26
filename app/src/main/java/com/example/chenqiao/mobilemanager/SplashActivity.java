package com.example.chenqiao.mobilemanager;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.chenqiao.application.MyApplication;
import com.example.chenqiao.utils.StreamToString;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SplashActivity extends Activity {

    private static final int MSG_ERROR_INTERSEVER = -1;
    final int OK = 1;
    private String current_versionName;
    private int  current_versionCode;
    private PackageInfo packageInfo;
    private PackageManager packageManager;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){

                case OK:
                    String[] info_string = (String[]) msg.obj;
                    String version_name = info_string[0];
                    String version_code = info_string[1];
                    String version_description = info_string[2];
                    String download_url = info_string[3];
                    if(Float.parseFloat(version_code)>current_versionCode){
                        update(info_string);
                    }
                    break;
                case MSG_ERROR_INTERSEVER :
                    Toast.makeText(SplashActivity.this,"网络错误",Toast.LENGTH_LONG).show();
                    enterhome();
                    break;

            }

        }
    };


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);


        packageManager = getPackageManager();
        current_versionName = getVersionName();
        current_versionCode = getVersionCode();


        if (!isNetworkAvailable(SplashActivity.this)&MyApplication.config_sp.getBoolean("autoupdate",true)) {
            //如果没有可用网络则直接进入mainactivity
            Toast.makeText(getApplicationContext(), "当前无可用网络", Toast.LENGTH_LONG).show();
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        } else {
                //若网络可用且自动更新已开启，检测版本更新，从服务器获取json判断和本地的版本信息
            if(MyApplication.config_sp.getBoolean("autoupdate",true))
                getNewVersion();
            else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        enterhome();
                        finish();
                    }

                }).start();

            }
        }
    }


    //判断当前网络状态
    public boolean isNetworkAvailable(Activity activity) {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    /*
                    System.out: 0===状态===DISCONNECTED
                    System.out: 0===类型===MOBILE
                    System.out: 1===状态===CONNECTED
                    System.out: 1===类型===WIFI
                     */
                    System.out.println(i + "===状态===" + networkInfo[i].getState());
                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private void update(final String[] info_string) {

        new AlertDialog.Builder(this).setTitle("发现新版本").setMessage( "版本名：" +"\n"+ info_string[0] + "\n版本号;" +"\n"+ info_string[1] + "\n新版本特性：" +"\n"+ info_string[2])
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                                asyncHttpClient.get(info_string[3], new AsyncHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                        super.onSuccess(statusCode, headers, responseBody);
                                        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/MobileManager.apk");
                                        try {
                                            FileOutputStream fos = new FileOutputStream(file);
                                            fos.write(responseBody);
                                            fos.close();
                                            Intent intent = new Intent();
                                            intent.setAction("android.intent.action.VIEW")
                                                    .addCategory("android.intent.category.DEFAULT")
                                                    .setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                                            startActivity(intent);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    @Override
                                    public void onFailure(int statusCode, Header[] headers,
                                                          byte[] responseBody, Throwable error) {
                                        super.onFailure(statusCode, headers, responseBody, error);
                                        Toast.makeText(SplashActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
                                        enterhome();
                                    }
                                });
                            }
                        }
                ).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterhome();
            }}).
                show();
    }


    public void enterhome(){
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();

    }


    //获取当前版本名
    public String getVersionName() {

        String versionName = "";
        try {
            packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;

    }
    //获取当前版本号
    public int  getVersionCode() {
        int versionCode = 1;
        try {
            packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public void getNewVersion(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                String path = MyApplication.SERVER_PATH+"/Version.json";
                try {
                //(1) 创建一个url对象 参数就是网址
                URL url = new URL(path);
                //(2)获取HttpURLConnection 链接对象
                HttpURLConnection conn = null;
                conn = (HttpURLConnection) url.openConnection();
                //(3)设置参数  发送get请求
                conn.setRequestMethod("GET"); //坑：必须大写
                //(4)设置链接网络的超时时间
                conn.setConnectTimeout(5000);
                //(5)获取服务器返回的状态码
                int code = conn.getResponseCode();
                conn.connect();
                if (code == 200){
                    //获取服务器返回的数据流
                    InputStream inputStream = conn.getInputStream();
                    String info = StreamToString.readStream(inputStream);
                    JSONObject jsonObject = new JSONObject(info);
                    //json解析
                    String version_name = jsonObject.getString("version_name");
                    String version_code = jsonObject.getString("version_code");
                    String version_description = jsonObject.getString("version_description");
                    String download_url = jsonObject.getString("download_url");
                    Message msg = Message.obtain();
                    String[] info_string = {version_name,version_code,version_description,download_url};
                    msg.obj = info_string;
                    msg.what = OK;
                    handler.sendMessage(msg);
                }else {

                    if (code==500){
                        Message msg = Message.obtain();
                        msg.what=MSG_ERROR_INTERSEVER;
                        handler.sendMessage(msg);
                    }

                }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

}
