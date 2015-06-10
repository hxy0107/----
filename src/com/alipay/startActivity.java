/*
 * *
 *  * Alipay.com Inc.
 *  * Copyright (c) 2004-2012 All Rights Reserved.
 *
 */

package com.alipay;

import android.app.*;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.*;
import android.provider.Settings;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.alipay.activity.BrowseProcessInfoActivity;
import com.alipay.common.service.runningService.CaptureService;
import com.alipay.common.service.runningService.LogcatService;
import com.alipay.common.service.runningService.SysInfoService;
import com.alipay.common.util.tools.L;
import com.alipay.common.util.tools.NetworkUtil;
import com.alipay.net.CmdStrings;
import com.alipay.net.CommandsHelper;
import com.alipay.net.NetStatDetail;
import com.alipay.store.FileStore;
import com.alipay.test.IntentService1;
import com.alipay.test.IntentService2;
import com.alipay.test.startService1;
import com.alipay.IsRoot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class startActivity extends Activity implements Runnable{
    private static String TAG = "AM_MEMORYIPROCESS";
    private Handler handler;
    private ActivityManager mActivityManager = null;

    private TextView tvAvailMem;
    private TextView netInternet;
    private Button btProcessInfo;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvAvailMem = (TextView) findViewById(R.id.tvAvailMemory);
        netInternet=(TextView)findViewById(R.id.netInternet);
        final TextView textInfo=(TextView)findViewById(R.id.textInfo);
        btProcessInfo = (Button) findViewById(R.id.btProcessInfo);
        // 跳转到显示进程信息界面
        btProcessInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(startActivity.this,
                        BrowseProcessInfoActivity.class);
                startActivity(intent);
            }
        });

        // 获得ActivityManager服务的对象
        mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        // 获得可用内存信息
        String availMemStr = getSystemAvaialbeMemorySize();
        Log.i(TAG, "The Availabel Memory Size is" + availMemStr);
        // 显示
        tvAvailMem.setText("系统可用内存为: " + availMemStr);
        //tvAvailMem.setText(NetworkUtil.getAPNType(this) + "");
        IsRoot isRooted=new IsRoot();
        Boolean isRoot = isRooted.isDeviceRooted();
        String s = isRoot ? "System is Root" : "System is Not Root";
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(s + "\n");

        if (!NetworkUtil.isNetWorkAvilable(this)) {
            Toast.makeText(this, "无网络连接", Toast.LENGTH_LONG);
        } else {
            String netInfo = NetworkUtil.getAPNType(this,true);
            stringBuffer.append(netInfo + "\n");
        }
        stringBuffer.append(FileStore.getLogFile()+"\n");
        stringBuffer.append(NetworkUtil.getNetType(this)+"\n");
        stringBuffer.append(NetStatDetail.getNetDetail(this)+"\n");
        textInfo.setText(stringBuffer);
        /*
       AsyncTask asyncTask=new AsyncTask() {
           String s;
           @Override
           protected Object doInBackground(Object[] params) {
                s=NetStatDetail.checkNetInternet(startActivity.this)?"能连上网哦":"很抱歉不能联网";
               return null;
           }

           @Override
           protected void onPreExecute() {
               super.onPreExecute();
           }

           @Override
           protected void onPostExecute(Object o) {

               handler.sendMessage(handler.obtainMessage(100,s));
               super.onPostExecute(o);
           }
       };*/

            handler=new Handler(){

                @Override
                public void handleMessage(Message msg) {
                    netInternet.setText((String)msg.obj);
                    super.handleMessage(msg);
                }
            };
        new Thread(this).start();
        //test is reboot
       // CommandsHelper.execCmd(CmdStrings.getCmdShutdown());







        Intent intentSys=new Intent(this, SysInfoService.class);
        startService(intentSys);
        Intent intentCap=new Intent(this, CaptureService.class);
        if(isRoot){
            startService(intentCap);
        }
        Intent intentLog=new Intent(this, LogcatService.class);
        startService(intentLog);

    }

    @Override
    protected void onResume() {
        L.e(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        L.e(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        L.e(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        L.e(TAG, "onDestroy");
        stopService(new Intent(this,LogcatService.class));
        super.onDestroy();
    }

    // 获得系统可用内存信息
    private String getSystemAvaialbeMemorySize() {
        // 获得MemoryInfo对象
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        // 获得系统可用内存，保存在MemoryInfo对象上
        mActivityManager.getMemoryInfo(memoryInfo);
        long memSize = memoryInfo.availMem;

        // 字符类型转换
        String availMemStr = formateFileSize(memSize);

        return availMemStr;
    }

    // 调用系统函数，字符串转换 long -String KB/MB
    private String formateFileSize(long size) {
        return Formatter.formatFileSize(startActivity.this, size);
    }


    @Override
    public void run() {
        while (true) {
            SimpleDateFormat dateFormat24 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String data = dateFormat24.format(new Date()) + "\n";
            String s = NetStatDetail.checkNetInternet(startActivity.this) ? "能连上网哦":"很抱歉不能联网" + "\n"+data;

            handler.sendMessage(handler.obtainMessage(100, s));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
