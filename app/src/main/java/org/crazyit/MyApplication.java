package org.crazyit;

import android.app.Application;

import org.crazyit.auction.client.util.SharedPrefsUtil;

/**
 * Created by cqjix on 2017/3/9.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPrefsUtil.init(getApplicationContext());
    }
}
