package org.crazyit.auction.client.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 常用工具类
 * Created by cqjix on 2017/2/26.
 */

public class Tools {

    /**
     * 将系统时间转换为 年月日 时分秒的格式
     * @param time
     * @return
     */
    public static String formatTime(long time){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return sdf.format(calendar.getTime());
    }
}
