package org.crazyit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by cqjix on 2017/2/26.
 */

public abstract class BaseFragmentActivity extends BaseActivity {
    private static final int ROOT_CONTAINER_ID = 0x90001;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        FrameLayout layout = new FrameLayout(this);
        setContentView(layout);
        layout.setId(ROOT_CONTAINER_ID);
//        getFragmentManager().beginTransaction()
//                .replace(ROOT_CONTAINER_ID , getFragment())
//                .commit();
        getSupportFragmentManager().beginTransaction()
                .replace(ROOT_CONTAINER_ID,getFragment())
                .commit();
    }
    protected abstract Fragment getFragment();
}
