package com.example.chenqiao.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import com.example.chenqiao.bean.Appinfo;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CHENQIAO on 2016/3/31.
 */
public class PackageUtils {




    public  static long getAvailableSDcardSize(){

        File SDCard = Environment.getExternalStorageDirectory();

        StatFs statFs = new StatFs(SDCard.getAbsolutePath());

        long availableBlocks;
        long blockSize;

        if (Build.VERSION.SDK_INT>=18){

            availableBlocks = statFs.getAvailableBlocksLong();
            blockSize = statFs.getBlockSizeLong();
        }else {
            availableBlocks = statFs.getAvailableBlocks();
            blockSize = statFs.getBlockSize();
        }
        return availableBlocks*blockSize;
    }


    public static long getAvaliableROMSize(){

        File dataDirectory = Environment.getDataDirectory();
        StatFs statFs = new StatFs(dataDirectory.getAbsolutePath());

        long availableBlocks;
        long blockSize;
        if(Build.VERSION.SDK_INT>=18){
            availableBlocks = statFs.getAvailableBlocksLong();
            blockSize = statFs.getBlockSizeLong();
        }else {
            availableBlocks = statFs.getAvailableBlocks();
            blockSize = statFs.getBlockSize();
        }

        return availableBlocks*blockSize;
    }


    public static List<Appinfo> getAllAppInfo(Context context){


        List<Appinfo> appinfolist = new ArrayList<Appinfo>();
        PackageManager packageManager = context.getPackageManager();
        List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(0);

        for (ApplicationInfo appinfo:installedApplications) {
            CharSequence label = appinfo.loadLabel(packageManager);
            Drawable icon = appinfo.loadIcon(packageManager);
            boolean isSDCARD;
            boolean isSystem;

            if( (appinfo.flags &   appinfo.FLAG_SYSTEM )!=0 ) //=   1 | 4;
            {
                //系统应用
                isSystem=true;
            }else
            {
                //用户自己安装的应用
                isSystem=false;
            }

            if( (appinfo.flags &   appinfo.FLAG_EXTERNAL_STORAGE )!=0 ) //=   1 | 4;
            {
                //SDCARD
                isSDCARD=true;
            }else
            {
                //非SDcard安装
                isSDCARD=false;
            }

            Appinfo appInfo = new Appinfo(label.toString(),icon,isSDCARD,isSystem);

            appinfolist.add(appInfo);
        }

        return  appinfolist;

    }

}
