package org.crazyit.auction.client.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.crazyit.auction.client.R;
import org.crazyit.auction.client.bean.KindBean;

import java.util.List;

/**
 *
 *新增商品商品种类adapter
 * Created by cqjix on 2017/2/26.
 */

public class KindItemAdapter extends BaseAdapter {

    private Context context;

    List<KindBean> kindBeanList;

    public KindItemAdapter(Context context, List<KindBean> kindBeanList){
        this.context=context;
        this.kindBeanList=kindBeanList;
    }
    @Override
    public int getCount() {
        return kindBeanList==null?0:kindBeanList.size();
    }

    @Override
    public KindBean getItem(int i) {
        return getCount()>i?kindBeanList.get(i):null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // 定义一个线性布局管理器
        LinearLayout linear = new LinearLayout(context);
        // 设置为水平的线性布局管理器
        linear.setOrientation(LinearLayout.HORIZONTAL);
        // 创建一个ImageView
        ImageView iv = new ImageView(context);
        iv.setPadding(10, 0, 20, 0);
        iv.setImageResource(R.drawable.item);
        // 将图片添加到LinearLayout中
        linear.addView(iv);
        // 创建一个TextView
        TextView tv = new TextView(context);
        // 获取JSONArray数组元素的property属性
//			String itemName = ((JSONObject)getItem(position))
//					.getString(property);
        String itemName=getItem(i).getKindName();


        // 设置TextView所显示的内容
        tv.setText(itemName);

        tv.setTextSize(20);
       return tv;
    }

}
