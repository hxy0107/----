package com.alipay.info;

import android.content.pm.ProviderInfo;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Created by xianyu.hxy on 2015/6/2.
 */
public class AppInfo {
    private int versionCode = 0;  //�汾��
    private String appname = "";// ��������
    private String packagename = "";    //������
    private Drawable appicon = null;//ͼ��
    private long lastInstal;//���һ�ΰ�װʱ��
    private ProviderInfo[] provider;//��Ӧ��
    private String InstalPath;//��װ·��

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }



    public Drawable getAppicon() {
        return appicon;
    }

    public void setAppicon(Drawable appicon) {
        this.appicon = appicon;
    }

    /**
     * @return the lastInstal
     */
    public long getLastInstal() {
        return lastInstal;
    }

    /**
     * @param firstInstallTime the lastInstal to set
     */
    public void setLastInstal(long firstInstallTime) {
        this.lastInstal = firstInstallTime;
    }

    /**
     * @return the provider
     */
    public ProviderInfo[] getProvider() {
        return provider;
    }

    /**
     * @param providers the provider to set
     */
    public void setProvider(ProviderInfo[] providers) {
        this.provider = providers;
    }

    /**
     * @return the instalPath
     */
    public String getInstalPath() {
        return InstalPath;
    }

    /**
     * @param instalPath the instalPath to set
     */
    public void setInstalPath(String instalPath) {
        InstalPath = instalPath;
    }
}
