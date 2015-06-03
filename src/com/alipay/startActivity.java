package com.alipay;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.alipay.activity.BrowseProcessInfoActivity;
import com.alipay.common.util.tools.MyLog;
import com.alipay.common.util.tools.NetworkUtil;

import java.io.IOException;

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
        tvAvailMem.setText(NetworkUtil.getAPNType(this) + "");


        IsRoot isRoot = new IsRoot();
        Boolean b = isRoot.isDeviceRooted();

        tvAvailMem.setText(b + "");
        isRoot.checkRootMethod4();

        // MyLog.MLog.Log();
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
