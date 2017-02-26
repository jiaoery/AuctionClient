package org.crazyit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by cqjix on 2017/2/20.
 */

public class BaseFragment extends Fragment{

    protected String LOG_TAG;
    protected BaseActivity activity;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity= (BaseActivity) getActivity();

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
