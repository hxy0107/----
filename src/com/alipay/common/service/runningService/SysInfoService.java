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
                 * 所有安装的包信息
                 */
                PacInfo pacInfo=new PacInfo();
                ArrayList<AppInfo> appInfos=pacInfo.Get(SysInfoService.this);
                StringBuffer stringBuffer=new StringBuffer();
                stringBuffer.append("\n\n"+"日记时间："+FileStore.date());
                stringBuffer.append("\n"+"*************安装的包信息*************"+"\n");
                int i=0;
                for(AppInfo appInfo:appInfos)
                {
                    String s=++i+". 应用名："+appInfo.getAppname()+" 包名："+appInfo.getPackagename()+" 包安装路径："+appInfo.getInstalPath()+" 最后安装日期："+new SimpleDateFormat("yy/MM/dd HH:mm").format(appInfo.getLastInstal())+" 应用版本号："+appInfo.getVersionCode()+"\n";
                    stringBuffer.append(s);
                }
                stringBuffer.append("*************************************"+"\n");
                /**
                 *安装的三方包信息
                 */
                ArrayList<AppInfo> appInfos3rd=pacInfo.GetOnly3rd(SysInfoService.this);
                StringBuffer stringBuffer3rd=new StringBuffer();
                stringBuffer3rd.append("\n\n"+"日记时间："+FileStore.date());
                stringBuffer3rd.append("\n"+"*************安装的三方包信息*************"+"\n");
                int i3rd=0;
                for(AppInfo appInfo:appInfos3rd)
                {
                    String s=++i3rd+". 应用名："+appInfo.getAppname()+" 包名："+appInfo.getPackagename()+" 包安装路径："+appInfo.getInstalPath()+" 最后安装日期："+new SimpleDateFormat("yy/MM/dd HH:mm").format(appInfo.getLastInstal())+" 应用版本号："+appInfo.getVersionCode()+"\n";
                    stringBuffer3rd.append(s);
                }
                stringBuffer3rd.append("**********************************************"+"\n");
                /**
                 * 正在运行的应用信息
                 */
                List<RunningAppInfo> appInfosRunning=pacInfo.queryAllRunningAppInfo(SysInfoService.this);
                StringBuffer stringBufferRun=new StringBuffer();
                stringBufferRun.append("\n\n"+"日记时间："+FileStore.date());
                stringBufferRun.append("\n"+"*************正在运行的应用信息*************"+"\n");
                int iRun=0;
                for(RunningAppInfo appInfo:appInfosRunning)
                {
                    String s=++iRun+" 应用:"+appInfo.getAppLabel()+" 包名:"+appInfo.getPkgName()+" 应用PID:"+appInfo.getPid()+" 应用进程名："+appInfo.getProcessName()+"\n";
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
