package org.crazyit.auction.client.adapter;

import org.crazyit.auction.client.R;
import org.crazyit.auction.client.bean.Goods;
import org.crazyit.auction.client.util.Tools;
import org.json.JSONObject;
import org.json.JSONException;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class GoodsAdapter extends BaseAdapter
{
	private Context context;
	//商品列表
	private List<Goods> goodses;
	private boolean hasIcon;
	private LayoutInflater inflater;
	public GoodsAdapter(Context context
			, List<Goods> goodses, boolean hasIcon)
	{
		this.context = context;
		this.goodses = goodses;
		this.hasIcon = hasIcon;
		this.inflater=LayoutInflater.from(context);
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
		ViewHolder holder;
		if(convertView==null){//如果该界面缓存为空
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.item_goods,parent,false);
			holder.imageView= (ImageView) convertView.findViewById(R.id.iv_good);
			holder.goodName= (TextView) convertView.findViewById(R.id.tv_good_name);
			holder.kindName= (TextView) convertView.findViewById(R.id.tv_kind_name);
			holder.goodInitPrice= (TextView) convertView.findViewById(R.id.tv_initprice);
			holder.goodMaxPrice= (TextView) convertView.findViewById(R.id.tv_max_price);
			holder.goodEndTime= (TextView) convertView.findViewById(R.id.tv_end_time);
			convertView.setTag(holder);
		}else{
			holder= (ViewHolder) convertView.getTag();
		}
		if(position<getCount()){
			if(hasIcon){
				//图片加载框架
				Glide.with(context).load(goodses.get(position).getGoodsIcon()).centerCrop().placeholder(R.drawable.ic_launcher).into(holder.imageView);
			}
			holder.goodName.setText(goodses.get(position).getGoodsName());
			holder.kindName.setText(goodses.get(position).getKindName());
			holder.goodInitPrice.setText(String.valueOf(goodses.get(position).getInitPrice()));
			holder.goodMaxPrice.setText(String.valueOf(goodses.get(position).getMaxPrice()));
			holder.goodEndTime.setText(Tools.formatTime(goodses.get(position).getEndTime()));

		}
		return convertView;
	}

	class ViewHolder{
		public ImageView imageView;//商品图像
		public TextView goodName;//商品名称
		public TextView kindName;//商品种类
		public TextView goodDesc;//商品简介
		public TextView goodInitPrice;//商品的初始价格
		public TextView goodMaxPrice;//商品的最高价格
		public TextView goodEndTime;//商品的截至时间
	}
}