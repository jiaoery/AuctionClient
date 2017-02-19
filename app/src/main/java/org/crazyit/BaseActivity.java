package org.crazyit;

import android.app.Activity;
import android.os.Bundle;

import cn.bmob.v3.Bmob;

/**
 * Created by cqjix on 2017/2/19.
 */

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化bomb云
        Bmob.initialize(this, "04e87f33b464ac0e2171075a7fb805ef");
    }
}
