package org.crazyit.auction.client.util;

import com.orhanobut.logger.Logger;

/**
 * Logger日志打印类
 * Created by cqjix on 2017/2/20.
 */

public class LogUtils {

    //info 信息输入
    public static void logi(String str){
        Logger.i(str);
    }

    //debug 信息输入
    public static void logd(String str){
        Logger.d(str);
    }
    //verbose 信息输入
    public static void logv(String str){
        Logger.v(str);
    }

    //error 信息输入
    public static void loge(String str){
        Logger.e(str);
    }

    //waring 信息输入
    public static void logw(String str){
        Logger.w(str);
    }

    //wtf 信息输入
    public static void logwtf(String str){
        Logger.wtf(str);
    }

    //json 信息输入
    public static void logjson(String str){
        Logger.json(str);
    }

    //info 信息输入
    public static void logxml(String str){
        Logger.xml(str);
    }

}
