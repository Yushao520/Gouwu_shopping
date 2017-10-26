package com.bewai.Gouwu_shopping.bean;

/**
 * Created by &那么& on 2017/10/26.
 */

public class ParentBean {
    public String title;        //一级列表的标题
    public boolean isCheck;     //一级列表的CheckBox
    public boolean ziCheck;     //一级列表的编辑开关
    public ParentBean(String title,boolean isCheck,boolean ziCheck)
    {
        this.ziCheck=ziCheck;
        this.title=title;
        this.isCheck=isCheck;
    }
}
