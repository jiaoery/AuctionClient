package org.crazyit.auction.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.crazyit.BaseActivity;
import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.LogUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Register extends BaseActivity {

    @Bind(R.id.userEditText)
    EditText userEditText;
    @Bind(R.id.pwdEditText)
    EditText pwdEditText;
    @Bind(R.id.bnRegister)
    Button bnRegister;
    String username;//用户名
    String pwd;//密码
    @Bind(R.id.my_toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initToolbar(toolbar, getString(R.string.register));
    }

    //注册
    @OnClick(R.id.bnRegister)
    public void register() {
        //验证输入是否有效
        if (validate()) {
            //注册用户
            BmobUser user = new BmobUser();
            user.setUsername(username);
            user.setPassword(pwd);
            user.signUp(new SaveListener<BmobUser>() {
                @Override
                public void done(BmobUser bmobUser, BmobException e) {
                    if (e == null) {
                        toast("注册成功:" + bmobUser.toString());
                        //启动Main Activity
                        Intent intent = new Intent(Register.this
                                , AuctionClientActivity.class);
                        startActivity(intent);
//						// 结束该Activity
                        finish();
                    } else {
                        LogUtils.loge(e.getMessage());
                        toast("注册失败：" + e.getMessage());
                    }
                }
            });
        }

    }

//    //返回登陆界面
//    @OnClick(R.id.bnLogin)
//    public void login(){
//        startActivity(new Intent(this,Login.class));
//        finish();
//    }

    // 对用户输入的用户名、密码进行校验
    private boolean validate() {
        username = userEditText.getText().toString().trim();
        if (username.equals("")) {
            DialogUtil.showDialog(this, "用户账户是必填项！", false);
            return false;
        }
        pwd = pwdEditText.getText().toString().trim();
        if (pwd.equals("")) {
            DialogUtil.showDialog(this, "用户口令是必填项！", false);
            return false;
        }
        return true;
    }

}
