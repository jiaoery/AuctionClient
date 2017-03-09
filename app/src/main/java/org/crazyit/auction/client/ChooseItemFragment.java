package org.crazyit.auction.client;

import org.crazyit.BaseFragment;
import org.crazyit.auction.client.adapter.GoodsAdapter;
import org.crazyit.auction.client.bean.Goods;
import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.HttpUtil;
import org.crazyit.auction.client.util.LogUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
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

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ChooseItemFragment extends BaseFragment {
    public static final int ADD_BID = 0x1009;
    Button bnHome;
    ListView succList;
    TextView viewTitle;
    Callbacks mCallbacks;
    GoodsAdapter goodsAdapter;
    List<Goods> goodses = new ArrayList<>();

    // 重写该方法，该方法返回的View将作为Fragment显示的组件
    @Override
    public View onCreateView(LayoutInflater inflater
            , ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.view_item
                , container, false);
        // 获取界面中的返回按钮
        bnHome = (Button) rootView.findViewById(R.id.bn_home);
        succList = (ListView) rootView.findViewById(R.id.succList);
        viewTitle = (TextView) rootView.findViewById(R.id.view_titile);
        // 为返回按钮的单击事件绑定事件监听器
        bnHome.setOnClickListener(new HomeListener(getActivity()));
        Bundle bundle = getArguments();
        String kindName = bundle.getString("kindName");
        goodsAdapter = new GoodsAdapter(getActivity(), goodses, true);
        // 根据种类ID获取该种类对应的所有物品
//			JSONArray jsonArray = new JSONArray(HttpUtil.getRequest(url));
//			JSONArrayAdapter adapter = new JSONArrayAdapter(
//					getActivity() , jsonArray , "name" , true);
//			// 使用ListView显示当前种类的所有物品
        succList.setAdapter(goodsAdapter);
        // 修改标题
        viewTitle.setText(R.string.item_list);
        succList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Goods goods = goodses.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("itemId", goods.getObjectId());
                mCallbacks.onItemSelected(ADD_BID, bundle);
            }
        });
        intData(kindName);
        return rootView;
    }


    /**
     * @param kindName 种类名字
     */
    private void intData(String kindName) {
        BmobQuery<Goods> query = new BmobQuery<Goods>();
        query.addWhereEqualTo("kindName", kindName);
        query.findObjects(new FindListener<Goods>() {
            @Override
            public void done(List<Goods> list, BmobException e) {
                if (e == null) {
                    goodses.clear();
                    goodses.addAll(list);
                    goodsAdapter.notifyDataSetChanged();
                } else {
                    LogUtils.loge("获取数据失败:" + e.getMessage());
                    activity.toast("获取数据失败:" + e.getMessage());
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
}