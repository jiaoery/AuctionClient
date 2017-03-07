package org.crazyit.auction.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.crazyit.BaseActivity;
import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.HttpUtil;
import org.crazyit.auction.client.util.LogUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class Login extends BaseActivity
{
	// 定义界面中两个文本框
	EditText etName, etPass;
	// 定义界面中两个按钮
	Button bnLogin, bnCancel,bnResgister;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		// 获取界面中两个编辑框
		etName = (EditText) findViewById(R.id.userEditText);
		etPass = (EditText) findViewById(R.id.pwdEditText);
		// 获取界面中的两个按钮
		bnLogin = (Button) findViewById(R.id.bnLogin);
		bnCancel = (Button) findViewById(R.id.bnCancel);
		bnResgister= (Button) findViewById(R.id.bnRegister);
		// 为bnCancal按钮的单击事件绑定事件监听器
		bnCancel.setOnClickListener(new HomeListener(this));
		bnResgister.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(Login.this,Register.class));
			}
		});
		bnLogin.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// 执行输入校验
				if (validate())  // ①
				{
					loginPro();
//					// 如果登录成功
//					if (loginPro())  // ②
//					{
//						// 启动Main Activity
//						Intent intent = new Intent(Login.this
//								, AuctionClientActivity.class);
//						startActivity(intent);
//						// 结束该Activity
//						finish();
//					}
//					else
//					{
//						DialogUtil.showDialog(Login.this
//								, "用户名称或者密码错误，请重新输入！", false);
//					}
				}
			}
		});
	}

	private void loginPro()
	{
		// 获取用户输入的用户名、密码
		String username = etName.getText().toString();
		String pwd = etPass.getText().toString();

		BmobUser bu2 = new BmobUser();
		bu2.setUsername(username);
		bu2.setPassword(pwd);
		bu2.login(new SaveListener<BmobUser>() {

			@Override
			public void done(BmobUser bmobUser, BmobException e) {
				if(e==null){
					toast("登录成功:");
					// 启动Main Activity
						Intent intent = new Intent(Login.this
								, AuctionClientActivity.class);
						startActivity(intent);
//						// 结束该Activity
						finish();
					//通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
					//如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
				}else{
					toast("登陆失败,请检查账号和密码");
					LogUtils.loge(e.getMessage());
				}
			}
		});
//		JSONObject jsonObj;
//		try
//		{
//			jsonObj = query(username, pwd);
//			// 如果userId 大于0
//			if (jsonObj.getInt("userId") > 0)
//			{
//				return true;
//			}
//		}
//		catch (Exception e)
//		{
//			DialogUtil.showDialog(this
//					, "服务器响应异常，请稍后再试！", false);
//			e.printStackTrace();
//		}


	}

	// 对用户输入的用户名、密码进行校验
	private boolean validate()
	{
		String username = etName.getText().toString().trim();
		if (username.equals(""))
		{
			DialogUtil.showDialog(this, "用户账户是必填项！", false);
			return false;
		}
		String pwd = etPass.getText().toString().trim();
		if (pwd.equals(""))
		{
			DialogUtil.showDialog(this, "用户口令是必填项！", false);
			return false;
		}
		return true;
	}

	// 定义发送请求的方法
	private JSONObject query(String username, String password)
			throws Exception
	{
		// 使用Map封装请求参数
		Map<String, String> map = new HashMap<>();
		map.put("user", username);
		map.put("pass", password);
		// 定义发送请求的URL
		String url = HttpUtil.BASE_URL + "login.jsp";
		// 发送请求
		return new JSONObject(HttpUtil.postRequest(url, map));
	}
}