package com.example.chenqiao.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by CHENQIAO on 2016/3/24.
 */
public class StreamToString{
       //把一个inputStream 转换成一个String
       public static String readStream(InputStream in) throws Exception{
         //定义一个内存输出流
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         int len = -1;
         byte[] buffer = new byte[1024]; //1kb
         while((len=in.read(buffer))!=-1){
             baos.write(buffer, 0, len);
              }
          in.close();
          String content = new String(baos.toByteArray());
          return content;
          }
      }