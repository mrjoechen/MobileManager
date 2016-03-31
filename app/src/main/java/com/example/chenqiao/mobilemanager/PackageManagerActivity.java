package com.example.chenqiao.mobilemanager;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.chenqiao.bean.Appinfo;
import com.example.chenqiao.utils.PackageUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PackageManagerActivity extends ActionBarActivity {
    private ListView lv_package_appinfo;
    private List<Appinfo> appinfolist;
    private List<Appinfo> userAppinfolist;
    private List<Appinfo> systemAppinfolist;
    private TextView tv_applist_apptype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_manager);
        getSupportActionBar().hide();

        TextView  tv_pacakge_rom = (TextView) findViewById(R.id.tv_package_rom);
        TextView  tv_pacakge_sdcard = (TextView) findViewById(R.id.tv_package_sdcard);
        tv_pacakge_rom.setText("ROM剩余空间:\r\n"+  Formatter.formatFileSize(this, PackageUtils.getAvaliableROMSize()));
        tv_pacakge_sdcard.setText("SDCard剩余空间:\r\n" + Formatter.formatFileSize(this, PackageUtils.getAvailableSDcardSize()));
        tv_applist_apptype = (TextView) findViewById(R.id.tv_applist_apptype);
        lv_package_appinfo = (ListView) findViewById(R.id.lv_package_appinfo);
        userAppinfolist = new ArrayList<>();
        systemAppinfolist = new ArrayList<>();
        MyAsyncTast3 mytask= new MyAsyncTast3() ;
        mytask.execute();
    }


    class MyAsyncTast3 extends AsyncTask<URL, Integer, Float> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Float doInBackground(URL...  params) {
            appinfolist= PackageUtils.getAllAppInfo(PackageManagerActivity.this);

            for (Appinfo app:appinfolist) {
                if (app.isSystem()){

                    systemAppinfolist.add(app);
                }else
                {
                    userAppinfolist.add(app);
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Float aFloat) {
            lv_package_appinfo.setAdapter(new MyAdapter());

            //页面加载的时候，系统设置setOnScrollListener 会call一次onScroll。
            lv_package_appinfo.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                    if (firstVisibleItem >= userAppinfolist.size() + 1) {

                        tv_applist_apptype.setText("系统应用:" + systemAppinfolist.size() + "个");
                    } else {
                        tv_applist_apptype.setText("用户应用:" + userAppinfolist.size() + "个");

                    }
                }
            });
            super.onPostExecute(aFloat);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }



    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return appinfolist.size();
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


            if (position==0){

                TextView tv = new TextView(PackageManagerActivity.this);
                int num= userAppinfolist.size();
                tv.setText("用户应用:"+num+"个");
                tv.setTextColor(Color.BLACK);
                return tv;

            }else if(position==userAppinfolist.size()+1){

                TextView tv = new TextView(PackageManagerActivity.this);
                int num= systemAppinfolist.size();
                tv.setText("系统应用:"+num+"个");
                tv.setTextColor(Color.BLACK);
                return tv;

            }


            Appinfo appInfo;
            if (position<userAppinfolist.size()+2){
                appInfo = userAppinfolist.get(position-1);
            }else
            {
                appInfo = systemAppinfolist.get(position-userAppinfolist.size()-2);
            }

            View item;
            ViewHolder holder;
            if (convertView!=null&&convertView instanceof RelativeLayout){

                item=convertView;
                holder = (ViewHolder) item.getTag();

            }else {
                item = View.inflate(PackageManagerActivity.this, R.layout.item_applist, null);
                ImageView iv_applist_icon = (ImageView) item.findViewById(R.id.iv_applist_icon);
                TextView tv_applist_appname = (TextView) item.findViewById(R.id.tv_applist_appname);
                TextView tv_applist_location = (TextView) item.findViewById(R.id.tv_applist_location);

                holder = new ViewHolder();
                holder.iv_applist_icon=iv_applist_icon;
                holder.tv_applist_appname =tv_applist_appname;
                holder.tv_applist_location=tv_applist_location;

                item.setTag(holder);
            }


            holder.iv_applist_icon.setImageDrawable(appInfo.getIcon());
            holder.tv_applist_appname.setText(appInfo.getAppname());

            if (appInfo.isSdcard())
                holder.tv_applist_location.setText("安装在sdcard上");
            else
                holder.tv_applist_location.setText("安装在ROM上");

            return item;
        }

        class ViewHolder {

            ImageView iv_applist_icon;
            TextView tv_applist_appname;
            TextView tv_applist_location;
        }
    }


}
