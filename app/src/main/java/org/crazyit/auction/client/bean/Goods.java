package org.crazyit.auction.client.bean;

import cn.bmob.v3.BmobObject;

/**
 * 已经成功竞争到的物品
 * Created by cqjix on 2017/2/20.
 */

public class Goods extends BmobObject{


    private String userId;//用户的id名

    private long goodsId;//商品id

    private String goodsName;//商品名称

    private String kindName;//种类名称

    private String item;//商品备注信息

    private int initPrice;//初始价格

    private int maxPrice;//最大价格

    private String desc;//商品简介

    private long endTime;//拍卖截至时间

    private long addTime;//添加日期


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getInitPrice() {
        return initPrice;
    }

    public void setInitPrice(int initPrice) {
        this.initPrice = initPrice;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
