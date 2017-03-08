package org.crazyit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import org.crazyit.auction.client.R;
import org.crazyit.constant.CONSTANT;

import rx.internal.schedulers.NewThreadScheduler;

/**
 * 通用模块
 * Created by cqjix on 2017/2/26.
 */

public abstract class BaseFragmentActivity extends BaseActivity {
    private static final int ROOT_CONTAINER_ID = 0x90001;

    private Toolbar mToolsBar;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //设置该activity的布局
//        FrameLayout layout = new FrameLayout(this);
        setContentView(R.layout.base_fragment_activity);
        mToolsBar= (Toolbar) findViewById(R.id.my_toolbar);
        initView();
//        layout.setId(ROOT_CONTAINER_ID);
//        getFragmentManager().beginTransaction()
//                .replace(ROOT_CONTAINER_ID , getFragment())
//                .commit();

        //替换framelyout为指定的fragemnt（碎片）
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_common_framlayout,getFragment())
                .commit();
    }

    //初始化界面
    protected void initView(){
        Intent intent=getIntent();
        if (intent!=null){
            initToolbar(mToolsBar,intent.getStringExtra(CONSTANT.COMMON_TITLE));
        }
    }

    //获取相应的fragment（碎片）
    protected abstract Fragment getFragment();
}
