package org.crazyit.auction.client;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.crazyit.BaseActivity;
import org.crazyit.BaseFragment;
import org.crazyit.auction.client.adapter.KindItemAdapter;
import org.crazyit.auction.client.bean.Goods;
import org.crazyit.auction.client.bean.KindBean;
import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class AddItemFragment extends BaseFragment implements OnClickListener
{
	// 定义界面中文本框
	EditText itemName, itemDesc,itemRemark,initPrice;
	Spinner itemKind , availTime;
	// 定义界面中两个按钮
	Button bnAdd, bnCancel;
	ImageView ivAddImage;//添加图片
	List<KindBean> kindBeanList=new ArrayList<>();
	KindItemAdapter itemAdapter;
	long endtime;
	Goods good=new Goods();
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
		ivAddImage= (ImageView) rootView.findViewById(R.id.iv_add_item);
		//为添加图片按钮增加点击事件
		ivAddImage.setOnClickListener(this);
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
		if(good.getGoodsIcon()==null){
			DialogUtil.showDialog(getActivity() , "请选择图片！" , false);
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

	@Override
	public void onClick(View v) {
		//根据点击的对象id判断逻辑处理
		switch (v.getId()){
			case R.id.iv_add_item:
				AddItemFragmentPermissionsDispatcher.openGalleryWithCheck(this);
				break;
		}

	}


	@NeedsPermission(Manifest.permission.CAMERA)
	void openGallery() {
		/***启动GalleryFinal多选***/
		GalleryFinal.openGallerySingle(1001, new GalleryFinal.OnHanlderResultCallback() {
			@Override
			public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
				if (resultList != null) {
					final File file=new File(resultList.get(0).getPhotoPath());
					final BmobFile bmobFile=new BmobFile(file);
					DialogUtil.showProgress(getActivity(),true);//显示加载dialog
					bmobFile.uploadblock(new UploadFileListener() {
						@Override
						public void done(BmobException e) {
							DialogUtil.showProgress(getActivity(),false);
							if(e==null){
								//bmobFile.getFileUrl()--返回的上传文件的完整地址
//								toast("上传文件成功:" + bmobFile.getFileUrl());
								LogUtils.logd("上传文件成功:" + bmobFile.getFileUrl());
								good.setGoodsIcon(bmobFile.getFileUrl());
								Glide.with(activity).load(file).placeholder(R.drawable.icon_photo_add_2).centerCrop().into(ivAddImage);
							}else{
								((BaseActivity)getActivity()).toast("上传文件失败:"+e.getMessage() );
							}

						}
					});
				}
			}

			@Override
			public void onHanlderFailure(int requestCode, String errorMsg) {
				Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
			}
		});
	}

	// ———————————————打开权限模块：照相———————————————

	@OnShowRationale(Manifest.permission.CAMERA)
	void show(PermissionRequest request) {
		DialogUtil.showDialog(getActivity(), getString(R.string.permission_camera_grant),false);
	}

	@OnPermissionDenied(Manifest.permission.CAMERA)
	void deny() {
		activity.toast(R.string.permission_camera_no);
//		BaseUtils.ToastLong(R.string.permission_camera_no);
	}

	@OnNeverAskAgain(Manifest.permission.CAMERA)
	void never() {
		DialogUtil.showDialog(activity,activity.getString(R.string.permission_camera_go_to_setting),false);
//		DialogUtil.buildNoPermissionAndGoSetting(getActivity(), getString(R.string.permission_camera_go_to_setting));
	}

	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		AddItemFragmentPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
	}




}