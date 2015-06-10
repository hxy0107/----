package com.alipay.common.service.runningService;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.alipay.common.util.tools.L;
import com.alipay.info.AppInfo;
import com.alipay.info.PacInfo;
import com.alipay.info.RunningAppInfo;
import com.alipay.security.Base64;
import com.alipay.store.FileStore;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xianyu.hxy on 2015/6/8.
 */
public class SysInfoService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                /**
                 * ���а�װ�İ���Ϣ
                 */
                PacInfo pacInfo=new PacInfo();
                ArrayList<AppInfo> appInfos=pacInfo.Get(SysInfoService.this);
                StringBuffer stringBuffer=new StringBuffer();
                stringBuffer.append("\n\n"+"�ռ�ʱ�䣺"+FileStore.date());
                stringBuffer.append("\n"+"*************��װ�İ���Ϣ*************"+"\n");
                int i=0;
                for(AppInfo appInfo:appInfos)
                {
                    String s=++i+". Ӧ������"+appInfo.getAppname()+" ������"+appInfo.getPackagename()+" ����װ·����"+appInfo.getInstalPath()+" ���װ���ڣ�"+new SimpleDateFormat("yy/MM/dd HH:mm").format(appInfo.getLastInstal())+" Ӧ�ð汾�ţ�"+appInfo.getVersionCode()+"\n";
                    stringBuffer.append(s);
                }
                stringBuffer.append("*************************************"+"\n");
                /**
                 *��װ����������Ϣ
                 */
                ArrayList<AppInfo> appInfos3rd=pacInfo.GetOnly3rd(SysInfoService.this);
                StringBuffer stringBuffer3rd=new StringBuffer();
                stringBuffer3rd.append("\n\n"+"�ռ�ʱ�䣺"+FileStore.date());
                stringBuffer3rd.append("\n"+"*************��װ����������Ϣ*************"+"\n");
                int i3rd=0;
                for(AppInfo appInfo:appInfos3rd)
                {
                    String s=++i3rd+". Ӧ������"+appInfo.getAppname()+" ������"+appInfo.getPackagename()+" ����װ·����"+appInfo.getInstalPath()+" ���װ���ڣ�"+new SimpleDateFormat("yy/MM/dd HH:mm").format(appInfo.getLastInstal())+" Ӧ�ð汾�ţ�"+appInfo.getVersionCode()+"\n";
                    stringBuffer3rd.append(s);
                }
                stringBuffer3rd.append("**********************************************"+"\n");
                /**
                 * �������е�Ӧ����Ϣ
                 */
                List<RunningAppInfo> appInfosRunning=pacInfo.queryAllRunningAppInfo(SysInfoService.this);
                StringBuffer stringBufferRun=new StringBuffer();
                stringBufferRun.append("\n\n"+"�ռ�ʱ�䣺"+FileStore.date());
                stringBufferRun.append("\n"+"*************�������е�Ӧ����Ϣ*************"+"\n");
                int iRun=0;
                for(RunningAppInfo appInfo:appInfosRunning)
                {
                    String s=++iRun+" Ӧ��:"+appInfo.getAppLabel()+" ����:"+appInfo.getPkgName()+" Ӧ��PID:"+appInfo.getPid()+" Ӧ�ý�������"+appInfo.getProcessName()+"\n";
                    stringBufferRun.append(s);
                }
                stringBufferRun.append("*************************************"+"\n");

               // FileStore.write(stringBuffer.toString(),FileStore.getLogFile());
                FileWriter writer = null;
                try {
                    if(L.isDebugModel){
                    writer = new FileWriter(FileStore.getLogFile(), true);
                    writer.write(stringBuffer.toString()+"\n");
                    writer.write(stringBuffer3rd.toString()+"\n");
                    writer.write(stringBufferRun.toString()+"\n");
                    writer.close();
                    }else {
                        writer = new FileWriter(FileStore.getLogFile(), true);
                        writer.write(Base64.toBase64(stringBuffer.toString()) + "\n");
                        writer.write(Base64.toBase64(stringBuffer3rd.toString())+"\n");
                        writer.write(Base64.toBase64(stringBufferRun.toString())+"\n");
                        writer.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stringBuffer=null;





            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
