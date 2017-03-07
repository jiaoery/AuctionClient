package org.crazyit.auction.client;

import java.util.HashMap;
import java.util.Map;

import org.crazyit.BaseFragment;
import org.crazyit.auction.client.bean.KindBean;
import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.HttpUtil;
import org.crazyit.auction.client.util.LogUtils;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class AddKindFragment extends BaseFragment {
    // 定义界面中两个文本框
    EditText kindName, kindDesc;
    // 定义界面中两个按钮
    Button bnAdd, bnCancel;

    @Override
    public View onCreateView(LayoutInflater inflater
            , ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View rootView = inflater.inflate(R.layout.add_kind
                , container, false);
        // 获取界面中两个编辑框
        kindName = (EditText) rootView.findViewById(R.id.kindName);
        kindDesc = (EditText) rootView.findViewById(R.id.kindDesc);
        // 获取界面中的两个按钮
        bnAdd = (Button) rootView.findViewById(R.id.bnAdd);
        bnCancel = (Button) rootView.findViewById(R.id.bnCancel);
        // 为取消按钮的单击事件绑定事件监听器
        bnCancel.setOnClickListener(new HomeListener(getActivity()));
        bnAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 输入校验
                if (validate()) {
                    // 获取用户输入的种类名、种类描述
                    String name = kindName.getText().toString();
                    String desc = kindDesc.getText().toString();
                    // 添加物品种类
                    addKind(name, desc);
                }
            }
        });
        return rootView;
    }

    // 对用户输入的种类名称进行校验
    private boolean validate() {
        String name = kindName.getText().toString().trim();
        if (name.equals("")) {
            DialogUtil.showDialog(getActivity(), "种类名称是必填项！", false);
            return false;
        }
        return true;
    }

    private void addKind(String name, String desc) {
        KindBean bean = new KindBean();
        bean.setKindName(name);
        bean.setKindDesc(desc);
        bean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    LogUtils.logd("商品种类添加成功：" + s);
                    activity.toast("商品种类添加成功：" + s);
                    activity.finish();
                } else {
                    LogUtils.logd("商品种类添加失败：" + e.getMessage());
                    activity.toast("商品种类添加失败：" + e.getMessage());
                }
            }
        });
    }

}