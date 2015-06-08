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
        // ��ת����ʾ������Ϣ����
        btProcessInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(startActivity.this,
                        BrowseProcessInfoActivity.class);
                startActivity(intent);
            }
        });

        // ���ActivityManager����Ķ���
        mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        // ��ÿ����ڴ���Ϣ
        String availMemStr = getSystemAvaialbeMemorySize();
        Log.i(TAG, "The Availabel Memory Size is" + availMemStr);
        // ��ʾ
        tvAvailMem.setText("ϵͳ�����ڴ�Ϊ: " + availMemStr);
        //tvAvailMem.setText(NetworkUtil.getAPNType(this) + "");
        IsRoot isRooted=new IsRoot();
        Boolean isRoot = isRooted.isDeviceRooted();
        String s = isRoot ? "System is Root" : "System is Not Root";
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(s + "\n");

        if (!NetworkUtil.isNetWorkAvilable(this)) {
            Toast.makeText(this, "����������", Toast.LENGTH_LONG);
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

    // ���ϵͳ�����ڴ���Ϣ
    private String getSystemAvaialbeMemorySize() {
        // ���MemoryInfo����
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        // ���ϵͳ�����ڴ棬������MemoryInfo������
        mActivityManager.getMemoryInfo(memoryInfo);
        long memSize = memoryInfo.availMem;

        // �ַ�����ת��
        String availMemStr = formateFileSize(memSize);

        return availMemStr;
    }

    // ����ϵͳ�������ַ���ת�� long -String KB/MB
    private String formateFileSize(long size) {
        return Formatter.formatFileSize(startActivity.this, size);
    }


}
