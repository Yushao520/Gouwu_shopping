package com.bewai.Gouwu_shopping.utils;

/**
 * Created by &那么& on 2017/10/26.
 */

public interface ICartView {
    //修改购物车中全选按钮的状态
    void changeCheckBtn(boolean flag);

    //计算总价的方法
    void addPrice();
    //删除条目的方法
    void delete(/*里面是你要用的参数*/);
}
