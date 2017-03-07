package org.crazyit;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import org.crazyit.auction.client.R;

import cn.bmob.v3.Bmob;

/**
 * Created by cqjix on 2017/2/19.
 */

public class BaseActivity extends AppCompatActivity {

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

    /**
     * 点击左上角图标，以便关闭当前activity
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(0, R.anim.slide_right_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * toolbar的初始化
     *
     * @param mToolBar
     * @param activityName
     */
    public void initToolbar(Toolbar mToolBar, String activityName) {
        mToolBar.setTitle("");
        TextView toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        if (toolbar_title != null && !TextUtils.isEmpty(activityName)) {
            toolbar_title.setText(activityName);
        }
        initToolbar(mToolBar);
    }

    public void initToolbar(Toolbar mToolBar, int activityName) {
        mToolBar.setTitle("");
        TextView toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        if (toolbar_title != null) {
            toolbar_title.setText(activityName);
        }
//        mToolBar.setTitle(activityName);
        initToolbar(mToolBar);
    }

    private void initToolbar(Toolbar mToolBar) {
        mToolBar.setTitleTextColor(getResources().getColor(R.color.white));
//        mToolBar.setNavigationIcon(R.drawable.icon_left);
        setSupportActionBar(mToolBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
    }

    protected void setToolbarName(String name) {
        TextView toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText(name);
    }

}
