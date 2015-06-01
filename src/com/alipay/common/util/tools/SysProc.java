package com.alipay.common.util.tools;

import android.os.Environment;
import android.util.Log;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by xianyu.hxy on 2015/5/29.
 */
public class SysProc {
    public static final String CACHE_DIR_NAME = "Alipay";
    public static final String CACHE_DIR_NAME_DETAIL = "Alipay/SysDump";
    public static final String TAG = "Alipay";


    public static String getMemInfo() {
        String str1 = "/proc/meminfo";
        String str2="";
        StringBuffer stringBuffer=new StringBuffer("************memInfo*********\n");
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            while ((str2 = localBufferedReader.readLine()) != null) {
                stringBuffer.append(str2+"\n");
                Log.i(TAG, "---" + str2);
            }
        } catch (IOException e) {
        }
        return stringBuffer.toString()+"\n";
    }

    public static String getCpuInfo() {
        String str1 = "/proc/cpuinfo";
        String str2="";
        StringBuffer stringBuffer=new StringBuffer("************cpuInfo*********\n");
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            while ((str2 = localBufferedReader.readLine()) != null) {
                stringBuffer.append(str2+"\n");
                Log.i(TAG, "---" + str2);
            }
        } catch (IOException e) {
        }
        return stringBuffer.toString()+"\n";
    }

    public static String getZoneInfo() {
        String str1 = "/proc/zoneInfo";
        String str2="";
        StringBuffer stringBuffer=new StringBuffer("************zoneInfo*********\n");
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            while ((str2 = localBufferedReader.readLine()) != null) {
                stringBuffer.append(str2+"\n");
                Log.i(TAG, "---" + str2);
            }
        } catch (IOException e) {
        }
        return stringBuffer.toString()+"\n";
    }

    public static String getVmstat() {
        String str1 = "/proc/vmstat";
        String str2="";
        StringBuffer stringBuffer=new StringBuffer("************vmStat*********\n");
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            while ((str2 = localBufferedReader.readLine()) != null) {
                stringBuffer.append(str2+"\n");
                Log.i(TAG, "---" + str2);
            }
        } catch (IOException e) {
        }
        return stringBuffer.toString()+"\n";
    }

    public static String getVersion() {
        String str1 = "/proc/version";
        String str2="";
        StringBuffer stringBuffer=new StringBuffer("************version*********\n");
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            while ((str2 = localBufferedReader.readLine()) != null) {
                stringBuffer.append(str2+"\n");
                Log.i(TAG, "---" + str2);
            }
        } catch (IOException e) {
        }
        return stringBuffer.toString()+"\n";
    }



    /*
    private static ArrayList filelist = new ArrayList();
   // public final static String string="/proc";
    public static void refreshFileList(String strPath) {
        File dir = new File(strPath);
        File[] files = dir.listFiles();

        if (files == null)
            return;
        for (int i = 0; i < files.length; i++) {
            if(!files[i].getPath().trim().startsWith("proc")){
               // continue;
            }
            if (files[i].isDirectory()) {
                refreshFileList(files[i].getAbsolutePath());
            } else {
                String strFileName = files[i].getAbsolutePath().toLowerCase();
                System.out.println("---"+strFileName);
                Log.e(TAG,strFileName);
                filelist.add(files[i].getAbsolutePath());
            }
        }
    }

    public static synchronized void write(String content)
    {
        try
        {
            FileWriter writer = new FileWriter(getFile(), true);
            writer.write(content+"\n");
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static String getFile()
    {
        File sdDir = null;

        if (Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
            sdDir = Environment.getExternalStorageDirectory();

        File cacheDir = new File(sdDir + File.separator + CACHE_DIR_NAME_DETAIL);

        if (!cacheDir.exists())
            cacheDir.mkdir();

        File filePath = new File(cacheDir + File.separator + date() + ".txt");

        return filePath.toString();
    }

    private static String date()
    {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(System
                .currentTimeMillis()));
    }

*/
}
