package org.crazyit.auction.client;

import org.crazyit.BaseFragment;
import org.crazyit.auction.client.adapter.KindAdapter;
import org.crazyit.auction.client.bean.KindBean;
import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.HttpUtil;
import org.crazyit.auction.client.util.LogUtils;
import org.json.JSONArray;

import android.app.Activity;
import android.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * 管理物品种类
 */
public class ManageKindFragment extends BaseFragment {
    public static final int ADD_KIND = 0x1007;
    Button bnHome, bnAdd;
    ListView kindList;
    Callbacks mCallbacks;
    List<KindBean> kindBeanList = new ArrayList<>();
    KindAdapter kindAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater
            , ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View rootView = inflater.inflate(R.layout.manage_kind
                , container, false);
        // 获取界面布局上的两个按钮
        bnHome = (Button) rootView.findViewById(R.id.bn_home);
        bnAdd = (Button) rootView.findViewById(R.id.bnAdd);
        kindList = (ListView) rootView.findViewById(R.id.kindList);
        // 为返回按钮的单击事件绑定事件监听器
        bnHome.setOnClickListener(new HomeListener(getActivity()));
        // 为添加按钮的单击事件绑定事件监听器
        bnAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View source) {
                // 当添加按钮被单击时，
                // 调用该Fragment所在Activity的onItemSelected方法
                mCallbacks.onItemSelected(ADD_KIND, null);
            }
        });
        kindAdapter=new KindAdapter(kindBeanList
                , getActivity());
        kindList.setAdapter(kindAdapter);

        initData();
        return rootView;
    }

    private void initData() {
//        KindBean kindBean=new KindBean();
//        kindBean.setId(12323123);
//        kindBean.setKindName("图书馆");
//        kindBean.setKindName("拥有数量巨大的图书");
//        kindBean.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if(e!=null){
//                    LogUtils.loge(e.getMessage());
//                    activity.toast("插入数据失败："+e.getMessage());
//                }else{
//                    activity.toast(s);
//                }
//            }
//        });
        BmobQuery<KindBean> query=new BmobQuery<KindBean>();
        query.doSQLQuery("select * from KindBean", new SQLQueryListener<KindBean>() {
            @Override
            public void done(BmobQueryResult<KindBean> bmobQueryResult, BmobException e) {
                if(e==null){
                    if(bmobQueryResult.getResults()!=null&&bmobQueryResult.getResults().size()>0){
                        kindBeanList.clear();
                        kindBeanList.addAll(bmobQueryResult.getResults());
                        LogUtils.logd("获取数据成功");
                    }else{
                        kindBeanList=new ArrayList<KindBean>();
                    }
                    kindAdapter.notifyDataSetChanged();
                }else{
                    LogUtils.loge("获取数据失败："+e.getMessage());
                    activity.toast(e.getMessage());
                }
            }
        });
    }

    // 当该Fragment被添加、显示到Activity时，回调该方法
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // 如果Activity没有实现Callbacks接口，抛出异常
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException(
                    "ManageKindFragment所在的Activity必须实现Callbacks接口!");
        }
        // 把该Activity当成Callbacks对象
        mCallbacks = (Callbacks) activity;
    }

    // 当该Fragment从它所属的Activity中被删除时回调该方法
    @Override
    public void onDetach() {
        super.onDetach();
        // 将mCallbacks赋为null。
        mCallbacks = null;
    }
}