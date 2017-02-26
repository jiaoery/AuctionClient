package org.crazyit.auction.client.bean;

import cn.bmob.v3.BmobObject;

/**
 * 商品种类
 * Created by cqjix on 2017/2/26.
 */

public class KindBean extends BmobObject {

    private String kindName;//种类名称

    private String kindDesc;//种类介绍


    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public String getKindDesc() {
        return kindDesc;
    }

    public void setKindDesc(String kindDesc) {
        this.kindDesc = kindDesc;
    }
}
