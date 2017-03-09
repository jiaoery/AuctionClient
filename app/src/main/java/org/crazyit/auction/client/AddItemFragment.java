package org.crazyit.auction.client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.crazyit.BaseFragment;
import org.crazyit.auction.client.adapter.KindItemAdapter;
import org.crazyit.auction.client.bean.Goods;
import org.crazyit.auction.client.bean.KindBean;
import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

public class AddItemFragment extends BaseFragment
{
	// 定义界面中文本框
	EditText itemName, itemDesc,itemRemark,initPrice;
	Spinner itemKind , availTime;
	// 定义界面中两个按钮
	Button bnAdd, bnCancel;
	List<KindBean> kindBeanList=new ArrayList<>();
	KindItemAdapter itemAdapter;
	long endtime;
	@Override
	public View onCreateView(LayoutInflater inflater
			, ViewGroup container, Bundle savedInstanceState)
	{
		super.onCreateView(inflater,container,savedInstanceState);
		View rootView = inflater.inflate(R.layout.add_item
				, container , false);
		// 获取界面中的文本框
		itemName = (EditText) rootView.findViewById(R.id.itemName);
		itemDesc = (EditText) rootView.findViewById(R.id.itemDesc);
		itemRemark = (EditText) rootView.findViewById(R.id.itemRemark);
		initPrice = (EditText) rootView.findViewById(R.id.initPrice);
		itemKind = (Spinner) rootView.findViewById(R.id.itemKind);
		availTime = (Spinner) rootView.findViewById(R.id.availTime);
		itemAdapter=new KindItemAdapter(getActivity(),kindBeanList);
		itemKind.setAdapter(itemAdapter);

//		// 将JSONArray包装成Adapter
//		JSONArrayAdapter adapter = new JSONArrayAdapter(
//				getActivity() , jsonArray , "kindName" , false);
//		// 显示物品种类列表
//		itemKind.setAdapter(adapter);
		// 获取界面中的两个按钮
		bnAdd = (Button) rootView.findViewById(R.id.bnAdd);
		bnCancel = (Button) rootView.findViewById(R.id.bnCancel);
		// 为取消按钮的单击事件绑定事件监听器
		bnCancel.setOnClickListener(new HomeListener(getActivity()));
		bnAdd.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// 执行输入校验
				if (validate())
				{
					// 获取用户输入的物品名、物品描述等信息
					String name = itemName.getText().toString();
					String desc = itemDesc.getText().toString();
					String remark = itemRemark.getText().toString();
					String price = initPrice.getText().toString();
					KindBean kind = (KindBean) itemKind.getSelectedItem();
					int avail = availTime.getSelectedItemPosition();
					//根据用户选择有效时间选项，指定实际的有效时间
					switch(avail)
					{
						case 0://一天
							endtime=System.currentTimeMillis()+24*60*60*1000;
							break;
						case 1://两天
							endtime=System.currentTimeMillis()+2*24*60*60*1000;
							break;
						case 2://三天
							endtime=System.currentTimeMillis()+3*24*60*60*1000;
							break;
						case 3://四天
							endtime=System.currentTimeMillis()+4*24*60*60*1000;
							break;
						case 4://五天
							endtime=System.currentTimeMillis()+5*24*60*60*1000;
							break;
						case 5 ://一周
							avail = 7;
							endtime=System.currentTimeMillis()+7*24*60*60*1000;
							break;
						case 6 ://一个月
							endtime=System.currentTimeMillis()+30*24*60*60*1000;
							break;
						default :
							endtime=System.currentTimeMillis();
							break;
					}
						// 添加物品
						addItem(name, desc
								, remark , price , kind.getKindName() , endtime);
				}
			}
		});

	initData();
	return rootView;
}

	private void initData() {
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
					itemAdapter.notifyDataSetChanged();
				}else{
					LogUtils.loge("获取数据失败："+e.getMessage());
					activity.toast(e.getMessage());
				}
			}
		});
	}

	// 对用户输入的物品名、起拍价格进行校验
	private boolean validate()
	{
		String name = itemName.getText().toString().trim();
		if (name.equals(""))
		{
			DialogUtil.showDialog(getActivity() , "物品名称是必填项！" , false);
			return false;
		}
		String price = initPrice.getText().toString().trim();
		if (price.equals(""))
		{
			DialogUtil.showDialog(getActivity() , "起拍价格是必填项！" , false);
			return false;
		}
		try
		{
			// 尝试把起拍价格转换为浮点数
			Double.parseDouble(price);
		}
		catch(NumberFormatException e)
		{
			DialogUtil.showDialog(getActivity() , "起拍价格必须是数值！" , false);
			return false;
		}
		return true;
	}

	private void addItem(String name, String desc
			, String remark , String initPrice , String kindName , long availTime)
	{
//		// 使用Map封装请求参数
//		Map<String , String> map = new HashMap<>();
//		map.put("itemName" , name);
//		map.put("itemDesc" , desc);
//		map.put("itemRemark" , remark);
//		map.put("initPrice" , initPrice);
//		map.put("kindId" , kindId);
//		map.put("availTime" , availTime + "");
//		// 定义发送请求的URL
//		String url = HttpUtil.BASE_URL + "addItem.jsp";
//		// 发送请求
//		return HttpUtil.postRequest(url , map);

		Goods good=new Goods();
		good.setGoodsName(name);
		good.setDesc(desc);
		good.setInitPrice(Integer.valueOf(initPrice));
		good.setKindName(kindName);
		good.setGoodsRemark(remark);
		good.setEndTime(availTime);
//		good.setUserId(BmobUser.getCurrentUser().getObjectId());
		good.save(new SaveListener<String>() {
			@Override
			public void done(String s, BmobException e) {
				if(e==null){
					LogUtils.logd("插入数据成功："+s);
					activity.toast("插入新商品成:"+s);
					activity.finish();
				}else{
					LogUtils.loge("插入新商品失败"+e.getMessage());
					activity.toast("插入新商品失败"+e.getMessage());
				}
			}
		});
	}
}