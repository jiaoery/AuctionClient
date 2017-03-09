package org.crazyit.auction.client;

import org.crazyit.BaseFragment;
import org.crazyit.auction.client.adapter.GoodsAdapter;
import org.crazyit.auction.client.bean.Goods;
import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.HttpUtil;
import org.crazyit.auction.client.util.LogUtils;
import org.crazyit.auction.client.util.Tools;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
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
import java.util.Queue;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 浏览自己竞拍的物品（拍下和未拍下的）
 */
public class ViewBidFragment extends BaseFragment {
    Button bnHome;
    ListView bidList;
    List<Goods> goodsList = new ArrayList<>();//查询数据集
    GoodsAdapter adapter;//列表适配器

    @Override
    public View onCreateView(LayoutInflater inflater
            , ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.view_bid
                , container, false);
        // 获取界面上的返回按钮
        bnHome = (Button) rootView.findViewById(R.id.bn_home);
        bidList = (ListView) rootView.findViewById(R.id.bidList);
        // 为返回按钮的单击事件绑定事件监听器
        bnHome.setOnClickListener(new HomeListener(getActivity()));
        adapter = new GoodsAdapter(activity, goodsList, true);
        bidList.setAdapter(adapter);
        bidList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 查看竞价详情
                viewBidDetail(position);
            }
        });
        initData();
        return rootView;
    }

    private void initData() {
        BmobQuery<Goods> query = new BmobQuery<>();
        query.addWhereEqualTo("userId", BmobUser.getCurrentUser().getObjectId());
        query.findObjects(new FindListener<Goods>() {
            @Override
            public void done(List<Goods> list, BmobException e) {
                if (e == null) {
                    if (list != null && list.size() > 0) {
                        goodsList.clear();
                        goodsList.addAll(list);//添加数据
                        adapter.notifyDataSetChanged();//通知listview刷新
                    }
                } else {
                    LogUtils.loge("获取数据失败：" + e.getMessage());
                    activity.toast("获取数据失败：" + e.getMessage());
                }
            }
        });
    }

    //显示商品的详情
    private void viewBidDetail(int position) {
        // 加载bid_detail.xml界面布局代表的视图
        View detailView = getActivity().getLayoutInflater()
                .inflate(R.layout.bid_detail, null);
        // 获取bid_detail界面中的文本框
        TextView itemName = (TextView) detailView
                .findViewById(R.id.itemName);
        TextView bidPrice = (TextView) detailView
                .findViewById(R.id.bidPrice);
        TextView bidTime = (TextView) detailView
                .findViewById(R.id.bidTime);
        TextView bidUser = (TextView) detailView
                .findViewById(R.id.bidUser);
//        // 获取被单击项目所包装的JSONObject
//        JSONObject jsonObj = (JSONObject) bidList.getAdapter()
//                .getItem(position);
        Goods good = goodsList.get(position);
        // 使用文本框来显示竞价详情。
        itemName.setText(good.getGoodsName());
        bidPrice.setText(String.valueOf(good.getMaxPrice()));//必须将int类型强转为string类型
        bidTime.setText(Tools.formatTime(good.getEndTime()));
        bidUser.setText(BmobUser.getCurrentUser().getUsername());
        DialogUtil.showDialog(getActivity(), detailView);
    }
}