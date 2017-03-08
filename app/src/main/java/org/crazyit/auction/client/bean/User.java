package org.crazyit.auction.client.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by cqjix on 2017/3/8.
 */

public class User extends BmobUser {
    private String user_icon;//用户头像

    public String getUser_icon() {
        return user_icon;
    }

    public void setUser_icon(String user_icon) {
        this.user_icon = user_icon;
    }
}
