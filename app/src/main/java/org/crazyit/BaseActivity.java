package org.crazyit;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import cn.bmob.v3.Bmob;

/**
 * Created by cqjix on 2017/2/19.
 */

public class BaseActivity extends FragmentActivity {

    private String LOG_TAG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化bomb云
        Bmob.initialize(this, "04e87f33b464ac0e2171075a7fb805ef");
        LOG_TAG=this.toString();
        Logger.init(LOG_TAG);
    }


    //标准toat
    public void toast(String string){
        Toast.makeText(this,string,Toast.LENGTH_SHORT).show();
    }

    //标准toat
    public void toast(int string){
        Toast.makeText(this,string,Toast.LENGTH_SHORT).show();
    }

    //标准toat
    public void toastLong(String string){
        Toast.makeText(this,string,Toast.LENGTH_LONG).show();
    }

    public void toastLong(int string){
        Toast.makeText(this,string,Toast.LENGTH_LONG).show();
    }

    //通用log
    public void log(String string){
        Logger.d(string);
    }

}
