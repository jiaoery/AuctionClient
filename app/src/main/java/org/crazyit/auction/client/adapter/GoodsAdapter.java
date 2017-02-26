package org.crazyit.auction.client.adapter;

import org.crazyit.auction.client.R;
import org.crazyit.auction.client.bean.Goods;
import org.json.JSONObject;
import org.json.JSONException;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class GoodsAdapter extends BaseAdapter
{
	private Context context;
	//商品列表
	private List<Goods> goodses;
	// 定义列表项显示JSONObject对象的哪个属性
	private String property;
	private boolean hasIcon;
	public GoodsAdapter(Context context
			, List<Goods> goodses, String property
			, boolean hasIcon)
	{
		this.context = context;
		this.goodses = goodses;
		this.property = property;
		this.hasIcon = hasIcon;
	}

	@Override
	public int getCount()
	{
		return goodses.size();
	}

	@Override
	public Goods getItem(int position)
	{
		return goodses.get(position);
	}

	@Override
	public long getItemId(int position)
	{
			// 返回物品的ID
			return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
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
		String itemName="";
		switch (property){
			case "name"://商品名称
				itemName=getItem(position).getGoodsName();
				break;
			case "kindName"://种类名称
				itemName=getItem(position).getKindName();
				break;
		}

			// 设置TextView所显示的内容
			tv.setText(itemName);

		tv.setTextSize(20);
		if (hasIcon)
		{
			// 将TextView添加到LinearLayout中
			linear.addView(tv);
			return linear;
		}
		else
		{
			return tv;
		}
	}
}