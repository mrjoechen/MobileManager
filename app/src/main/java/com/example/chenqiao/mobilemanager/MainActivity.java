package com.example.chenqiao.mobilemanager;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    private static final int CONTENT_NUM = 9 ;
    private GridView gv_home_content;



    private int[] iconarray ={R.drawable.safe,R.drawable.callmsgsafe,R.drawable.app
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
        getSupportActionBar().hide();

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
                        Toast.makeText(MainActivity.this, titles[position], Toast.LENGTH_SHORT).show();

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
}
