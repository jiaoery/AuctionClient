package org.crazyit.auction.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.crazyit.BaseActivity;
import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.HttpUtil;
import org.crazyit.auction.client.util.LogUtils;
import org.crazyit.auction.client.util.SharedPrefsUtil;
import org.crazyit.constant.CONSTANT;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class Login extends BaseActivity {
    Toolbar toolbar;
    // 定义界面中两个文本框
    EditText etName, etPass;
    // 定义界面中两个按钮
    Button bnLogin;
    //注册
    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolbar(toolbar, getString(R.string.login));
        // 获取界面中两个编辑框
        etName = (EditText) findViewById(R.id.userEditText);
        etPass = (EditText) findViewById(R.id.pwdEditText);
        //从SharePrefence取值
        etName.setText(SharedPrefsUtil.getString(CONSTANT.USER_NAME, ""));
        etPass.setText(SharedPrefsUtil.getString(CONSTANT.PASSWORD, ""));
        // 获取界面中的两个按钮
        bnLogin = (Button) findViewById(R.id.btn_login);
        tvRegister = (TextView) findViewById(R.id.tv_register);
        tvRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
        bnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 执行输入校验
                if (validate())  // ①
                {
                    loginPro();
                }
            }
        });
    }

    private void loginPro() {
        // 获取用户输入的用户名、密码
        String username = etName.getText().toString();
        final String pwd = etPass.getText().toString();
        BmobUser bu2 = new BmobUser();
        bu2.setUsername(username);
        bu2.setPassword(pwd);
        DialogUtil.showProgress(this, true);
        bu2.login(new SaveListener<BmobUser>() {

            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                DialogUtil.showProgress(Login.this, false);
                if (e == null) {
                    toast("登录成功:");
                    // 启动Main Activity
                    SharedPrefsUtil.putString(CONSTANT.USER_NAME, bmobUser.getUsername());
                    SharedPrefsUtil.putString(CONSTANT.PASSWORD, pwd);
                    Intent intent = new Intent(Login.this
                            , AuctionClientActivity.class);
                    startActivity(intent);
//						// 结束该Activity
                    finish();
                    //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                    //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
                } else {
                    toast("登陆失败,请检查账号和密码" + e.getMessage());
                    LogUtils.loge(e.getMessage());
                }
            }
        });


    }

    // 对用户输入的用户名、密码进行校验
    private boolean validate() {
        String username = etName.getText().toString().trim();
        if (username.equals("")) {
            DialogUtil.showDialog(this, "用户账户是必填项！", false);
            return false;
        }
        String pwd = etPass.getText().toString().trim();
        if (pwd.equals("")) {
            DialogUtil.showDialog(this, "用户口令是必填项！", false);
            return false;
        }
        return true;
    }

}