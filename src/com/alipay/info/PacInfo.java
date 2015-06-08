package com.alipay.info;


import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.*;

/**
 * Created by xianyu.hxy on 2015/6/8.
 */
public class PacInfo {
    private PackageManager pm;
    //����Ӧ��
    public ArrayList<AppInfo> Get(Context context){
        ArrayList<AppInfo> appList = new ArrayList<AppInfo>();
        List<PackageInfo> packageInfos=context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packageInfos.size(); i++) {
            PackageInfo pInfo=packageInfos.get(i);
            AppInfo allAppInfo=new AppInfo();
            allAppInfo.setAppname(pInfo.applicationInfo.loadLabel(context.getPackageManager()).toString());//Ӧ�ó��������
            allAppInfo.setPackagename(pInfo.packageName);//Ӧ�ó���İ�
            allAppInfo.setVersionCode(pInfo.versionCode);//�汾��
            allAppInfo.setLastInstal(pInfo.firstInstallTime);
            //allAppInfo.setProvider(pInfo.providers);
            allAppInfo.setInstalPath(pInfo.applicationInfo.sourceDir);
            allAppInfo.setAppicon(pInfo.applicationInfo.loadIcon(context.getPackageManager()));
            appList.add(allAppInfo);
        }
        return appList;
    }
    //����Ӧ��
    public ArrayList<AppInfo> GetOnly3rd(Context context){
        ArrayList<AppInfo> appList = new ArrayList<AppInfo>();
        List<PackageInfo> packageInfos=context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packageInfos.size(); i++) {
            PackageInfo pInfo=packageInfos.get(i);
            AppInfo allAppInfo=new AppInfo();
            allAppInfo.setAppname(pInfo.applicationInfo.loadLabel(context.getPackageManager()).toString());//Ӧ�ó��������
            allAppInfo.setPackagename(pInfo.packageName);//Ӧ�ó���İ�
            allAppInfo.setVersionCode(pInfo.versionCode);//�汾��
            allAppInfo.setLastInstal(pInfo.firstInstallTime);
            //allAppInfo.setProvider(pInfo.providers);
            allAppInfo.setInstalPath(pInfo.applicationInfo.sourceDir);
            allAppInfo.setAppicon(pInfo.applicationInfo.loadIcon(context.getPackageManager()));
            if((pInfo.applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM)==0){
                appList.add(allAppInfo);//�����ϵͳӦ�ã��������appList
            }

        }
        return appList;
    }
    //����ϵͳӦ��
    public ArrayList<AppInfo> GetOnlysys(Context context){
        ArrayList<AppInfo> appList = new ArrayList<AppInfo>();
        List<PackageInfo> packageInfos=context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packageInfos.size(); i++) {
            PackageInfo pInfo=packageInfos.get(i);
            AppInfo allAppInfo=new AppInfo();
            allAppInfo.setAppname(pInfo.applicationInfo.loadLabel(context.getPackageManager()).toString());//Ӧ�ó��������
            allAppInfo.setPackagename(pInfo.packageName);//Ӧ�ó���İ�
            allAppInfo.setVersionCode(pInfo.versionCode);//�汾��
            allAppInfo.setLastInstal(pInfo.firstInstallTime);
            //allAppInfo.setProvider(pInfo.providers);
            allAppInfo.setInstalPath(pInfo.applicationInfo.sourceDir);
            allAppInfo.setAppicon(pInfo.applicationInfo.loadIcon(context.getPackageManager()));
            if(!((pInfo.applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM)==0)){
                appList.add(allAppInfo);//�����ϵͳӦ�ã��������appList
            }

        }
        return appList;
    }
    public boolean filterApp(ApplicationInfo info) {
        if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
        // �������ϵͳ��Ӧ��,���Ǳ��û�������. �û�Ӧ��
            return true;
        } else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            // ������û���Ӧ��
            return true;
        }
        return false;
    }


    public List<RunningAppInfo> queryAllRunningAppInfo(Context context) {
        pm = context.getPackageManager();
        // ��ѯ�����Ѿ���װ��Ӧ�ó���
        List<ApplicationInfo> listAppcations = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        Collections.sort(listAppcations, new ApplicationInfo.DisplayNameComparator(pm));// ����

        // ���������������еİ��� �Լ������ڵĽ�����Ϣ
        Map<String, ActivityManager.RunningAppProcessInfo> pgkProcessAppMap = new HashMap<String, ActivityManager.RunningAppProcessInfo>();

        ActivityManager mActivityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        // ͨ������ActivityManager��getRunningAppProcesses()�������ϵͳ�������������еĽ���
        List<ActivityManager.RunningAppProcessInfo> appProcessList = mActivityManager
                .getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcessList) {
            int pid = appProcess.pid; // pid
            String processName = appProcess.processName; // ������

            String[] pkgNameList = appProcess.pkgList; // ��������ڸý����������Ӧ�ó����

            // �������Ӧ�ó���İ���
            for (int i = 0; i < pkgNameList.length; i++) {
                String pkgName = pkgNameList[i];
                // ������map������
                pgkProcessAppMap.put(pkgName, appProcess);
            }
        }
        // ���������������е�Ӧ�ó�����Ϣ
        List<RunningAppInfo> runningAppInfos = new ArrayList<RunningAppInfo>(); // ������˲鵽��AppInfo

        for (ApplicationInfo app : listAppcations) {
            // ����ð������� ����һ��RunningAppInfo����
            if (pgkProcessAppMap.containsKey(app.packageName)) {
                // ��ø�packageName�� pid �� processName
                int pid = pgkProcessAppMap.get(app.packageName).pid;
                String processName = pgkProcessAppMap.get(app.packageName).processName;
                runningAppInfos.add(getAppInfo(app, pid, processName));
            }
        }

        return runningAppInfos;

    }
    private RunningAppInfo getAppInfo(ApplicationInfo app, int pid, String processName) {
        RunningAppInfo appInfo = new RunningAppInfo();
        appInfo.setAppLabel((String) app.loadLabel(pm));
        appInfo.setAppIcon(app.loadIcon(pm));
        appInfo.setPkgName(app.packageName);

        appInfo.setPid(pid);
        appInfo.setProcessName(processName);

        return appInfo;
    }
}
