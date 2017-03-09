package org.crazyit.auction.client;

import org.crazyit.BaseFragment;
import org.crazyit.auction.client.adapter.GoodsAdapter;
import org.crazyit.auction.client.adapter.KindAdapter;
import org.crazyit.auction.client.bean.Goods;
import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.HttpUtil;
import org.crazyit.auction.client.util.LogUtils;
import org.crazyit.auction.client.util.Tools;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ManageItemFragment extends BaseFragment {
    public static final int ADD_ITEM = 0x1006;
    ;
    Button bnHome, bnAdd;
    ListView itemList;
    Callbacks mCallbacks;

    List<Goods> goodses = new ArrayList<>();

    GoodsAdapter goodsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater
            , ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.manage_item
                , container, false);
        bnHome = (Button) rootView.findViewById(R.id.bn_home);
        bnAdd = (Button) rootView.findViewById(R.id.bnAdd);
        itemList = (ListView) rootView.findViewById(R.id.itemList);
        // 为返回按钮的单击事件绑定事件监听器
        bnHome.setOnClickListener(new HomeListener(getActivity()));
        bnAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View source) {

                mCallbacks.onItemSelected(ADD_ITEM, null);
            }
        });
        // 定义发送请求的URL
//		String url = HttpUtil.BASE_URL + "viewOwnerItem.jsp";
//		try
//		{
        goodsAdapter = new GoodsAdapter(getActivity(), goodses, true);
        itemList.setAdapter(goodsAdapter);
//			// 向指定URL发送请求
//			JSONArray jsonArray = new JSONArray(HttpUtil.getRequest(url));
//			// 将服务器响应包装成Adapter
//			JSONArrayAdapter adapter = new JSONArrayAdapter(getActivity()
//					, jsonArray, "name", true);
//			itemList.setAdapter(adapter);
//		}
//		catch (Exception e)
//		{
//			DialogUtil.showDialog(getActivity()
//					, "服务器响应异常，请稍后再试！", false);
//			e.printStackTrace();
//		}
        itemList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                viewItemInBid(position);  // ①
            }
        });
        initData();
        return rootView;
    }

    private void initData() {
        //查询所有的物品
        BmobQuery<Goods> goodsQuery = new BmobQuery<>();
        goodsQuery.addWhereGreaterThanOrEqualTo("goodsName", "");
        goodsQuery.findObjects(new FindListener<Goods>() {
            @Override
            public void done(List<Goods> list, BmobException e) {
                if (e == null) {
                    goodses.clear();
                    goodses.addAll(list);
                    goodsAdapter.notifyDataSetChanged();
                } else {
                    LogUtils.loge(e.getMessage());
                    activity.toast("获取竞得物品失败：" + e.getMessage());

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
                    "ManagerItemFragment所在的Activity必须实现Callbacks接口!");
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

    private void viewItemInBid(int position) {
        // 加载detail_in_bid.xml界面布局代表的视图
        View detailView = getActivity().getLayoutInflater()
                .inflate(R.layout.detail_in_bid, null);
        // 获取detail_in_bid.xml界面中的文本框
        TextView itemName = (TextView) detailView
                .findViewById(R.id.itemName);
        TextView itemKind = (TextView) detailView
                .findViewById(R.id.itemKind);
        TextView maxPrice = (TextView) detailView
                .findViewById(R.id.maxPrice);
        TextView initPrice = (TextView) detailView
                .findViewById(R.id.initPrice);
        TextView endTime = (TextView) detailView
                .findViewById(R.id.endTime);
        TextView itemRemark = (TextView) detailView
                .findViewById(R.id.itemRemark);
//		// 获取被单击列表项所包装的JSONObject
//		JSONObject jsonObj = (JSONObject) itemList.getAdapter().getItem(
//				position);
        Goods goods = goodses.get(position);

        // 通过文本框显示物品详情
        itemName.setText(goods.getGoodsName());
        itemKind.setText(goods.getKindName());
        maxPrice.setText(String.valueOf(goods.getMaxPrice()));
        itemRemark.setText(goods.getGoodsRemark());
        initPrice.setText(String.valueOf(goods.getInitPrice()));
        endTime.setText(Tools.formatTime(goods.getEndTime()));
        DialogUtil.showDialog(getActivity(),detailView);
    }
}