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
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.alipay.activity.BrowseProcessInfoActivity;
import com.alipay.common.service.runningService.SysInfoService;
import com.alipay.common.util.tools.L;
import com.alipay.common.util.tools.NetworkUtil;
import com.alipay.store.FileStore;
import com.alipay.test.IntentService1;
import com.alipay.test.IntentService2;
import com.alipay.test.startService1;
import com.alipay.IsRoot;


public class startActivity extends Activity {
    private static String TAG = "AM_MEMORYIPROCESS";

    private ActivityManager mActivityManager = null;

    private TextView tvAvailMem;
    private Button btProcessInfo;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvAvailMem = (TextView) findViewById(R.id.tvAvailMemory);
        TextView textInfo=(TextView)findViewById(R.id.textInfo);
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
            stringBuffer.append(FileStore.getLogFile());
            textInfo.setText(stringBuffer);









        Intent intent1=new Intent(this, SysInfoService.class);
        startService(intent1);
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


}
