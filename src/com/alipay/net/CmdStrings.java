package com.alipay.net;

/**
 * Created by xianyu.hxy on 2015/6/10.
 */
public class CmdStrings {
    //实现关机命令
  public static String[] getCmdShutdown(){
       String[] commands = new String[7];
       commands[0] = "adb shell";
       commands[1] = "su";
       commands[2] = "cd /system/bin";
       commands[3] = "./reboot";
       return commands;
   }


}
