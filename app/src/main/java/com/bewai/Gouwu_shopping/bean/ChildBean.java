package com.bewai.Gouwu_shopping.bean;

/**
 * Created by &那么& on 2017/10/26.
 */

public class ChildBean {
    public String content;      //二级列表的内容
    public boolean isCheck;     //二级列表的CheckBox
    public boolean viewChange;  //二级列表是否显示编辑界面
    public int saleNum;         //二级列表的数量
    public int price;           //二级列表的价格
    public ChildBean(String content,int saleNum,boolean isCheck,int price,boolean viewChange)
    {
        this.saleNum=saleNum;
        this.viewChange=viewChange;
        this.content=content;
        this.isCheck=isCheck;
        this.price=price;
    }
}
