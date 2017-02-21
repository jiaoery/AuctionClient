package org.crazyit.auction.client;

import org.crazyit.BaseFragment;
import org.crazyit.auction.client.adapter.GoodsAdapter;
import org.crazyit.auction.client.bean.Goods;
import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.LogUtils;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ViewItemFragment extends BaseFragment {
    Button bnHome;
    ListView succList;
    TextView viewTitle;
    List<Goods> goodses = new ArrayList<>();
    GoodsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater
            , ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_item
                , container, false);
        // 获取界面上的返回按钮
        bnHome = (Button) rootView.findViewById(R.id.bn_home);
        succList = (ListView) rootView.findViewById(R.id.succList);
        viewTitle = (TextView) rootView.findViewById(R.id.view_titile);
        initView();
        initData();
        return rootView;
    }

    private void initView() {

        // 为返回按钮的单击事件绑定事件监听器
        bnHome.setOnClickListener(new HomeListener(getActivity()));
        String action = getArguments().getString("action");
        viewTitle.setText(R.string.view_fail);

        adapter = new GoodsAdapter(getActivity()
                , goodses, "name", true);  // ②
        succList.setAdapter(adapter);

        succList.setOnItemClickListener(new

                                                OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(AdapterView<?> parent, View view,
                                                                            int position, long id) {
                                                        // 查看指定物品的详细情况。
                                                        viewItemDetail(position);  // ③
                                                    }
                                                }

        );
    }

    //初始化数据
    public void initData() {
        BmobQuery<Goods> goodsQuery = new BmobQuery<>();
        goodsQuery.addWhereEqualTo("goodsName", "遥控飞机");
        goodsQuery.findObjects(new FindListener<Goods>() {
            @Override
            public void done(List<Goods> list, BmobException e) {
                if (e != null) {
                    goodses.clear();
                    goodses.addAll(list);
                } else {
                    LogUtils.loge(e.getMessage());
                    activity.toast("获取竞得物品失败：" + e.getMessage());

                }
            }
        });
    }

    private void viewItemDetail(int position) {
        // 加载detail.xml界面布局代表的视图
        View detailView = getActivity().getLayoutInflater()
                .inflate(R.layout.detail, null);
        // 获取detail.xml界面布局中的文本框
        TextView itemName = (TextView) detailView
                .findViewById(R.id.itemName);
        TextView itemKind = (TextView) detailView
                .findViewById(R.id.itemKind);
        TextView maxPrice = (TextView) detailView
                .findViewById(R.id.maxPrice);
        TextView itemRemark = (TextView) detailView
                .findViewById(R.id.itemRemark);
        // 获取被单击的列表项
//        JSONObject jsonObj = (JSONObject) succList.getAdapter().getItem(
//                position);
        Goods goods = goodses.get(position);
        // 通过文本框显示物品详情
        itemName.setText(goods.getGoodsName());
        itemKind.setText(goods.getKindName());
        maxPrice.setText(goods.getMaxPrice());
        itemRemark.setText(goods.getDesc());
        DialogUtil.showDialog(getActivity(), detailView);
    }
}