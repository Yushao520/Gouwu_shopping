package com.bewai.Gouwu_shopping;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.bewai.Gouwu_shopping.Aapter.CartAdapter;
import com.bewai.Gouwu_shopping.bean.ChildBean;
import com.bewai.Gouwu_shopping.bean.ParentBean;
import com.bewai.Gouwu_shopping.utils.ICartView;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener,ICartView{

    private ExpandableListView expandableListView;
    private CheckBox gouwuche_footer_check;
    private TextView gouwuche_footer_jiesuan;
    private TextView gouwuche_footer_price;
    private TextView gouwuche_footer_heji;
    private CartAdapter adapter;
    private List<ParentBean> parentList;
    private List<List<ChildBean>> childList;
    private SharedPreferences sp;
    private int sum=0;//总价


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        initData();
        createEvent();
    }
    private void initData() {
        /**
         * 先不进行判断登录状态,写死数据
         */
        if(/*sp.getBoolean("islogin",false)*/true)
        {

            expandableListView.setVisibility(View.VISIBLE);
            //登录成功
            //调用P层获取数据
            /*以下为假数据*/
            parentList = new ArrayList<>();
            parentList.add(new ParentBean("店铺1", false,true));
            parentList.add(new ParentBean("店铺2", false,true));
            parentList.add(new ParentBean("店铺3", false,true));
            childList = new ArrayList<>();
            List<ChildBean> child_1 = new ArrayList<>();
            child_1.add(new ChildBean("商品1",2, false,2,true));
            child_1.add(new ChildBean("商品2",1, false,4,true));
            List<ChildBean> child_2 = new ArrayList<>();
            child_2.add(new ChildBean("商品3",1, false,4,true));
            child_2.add(new ChildBean("商品4",1, false,5,true));
            child_2.add(new ChildBean("商品5",1, false,8,true));
            List<ChildBean> child_3 = new ArrayList<>();
            child_3.add(new ChildBean("商品3",1, false,4,true));
            child_3.add(new ChildBean("商品4",1, false,5,true));
            child_3.add(new ChildBean("商品5",1, false,8,true));
            childList.add(child_1);
            childList.add(child_2);
            childList.add(child_3);

            adapter = new CartAdapter(Main2Activity.this, parentList, childList,this);
            expandableListView.setAdapter(adapter);

            expandableListView.setGroupIndicator(null);
            for (int i = 0; i < adapter.getGroupCount(); i++) {
                expandableListView.expandGroup(i);
            }
        }

    }

    private void initView() {
        expandableListView = (ExpandableListView) findViewById(R.id.gouwuche_expanded);
        gouwuche_footer_check = (CheckBox) findViewById(R.id.gouwuche_footer_check);
        gouwuche_footer_jiesuan = (TextView) findViewById(R.id.gouwuche_footer_jiesuan);
        gouwuche_footer_jiesuan.setOnClickListener(this);
        gouwuche_footer_price = (TextView) findViewById(R.id.gouwuche_footer_price);
        gouwuche_footer_heji = (TextView) findViewById(R.id.gouwuche_footer_heji);
    }

    private void createEvent() {
        //设置选中监听去实现全选
        gouwuche_footer_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true)
                {
                    adapter.allCheck(true);
                }
            }
        });
        //设置点击监听去实现反选
        gouwuche_footer_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取购物车的CheckBox的选中状态
                boolean isCheck=gouwuche_footer_check.isChecked();
                if(!isCheck)
                {
                    adapter.allCheck(false);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gouwuche_footer_jiesuan:
                break;
        }
    }

    //修改全选按钮的状态
    @Override
    public void changeCheckBtn(boolean flag) {
        gouwuche_footer_check.setChecked(flag);
    }

    //计算总价的方法
    @Override
    public void addPrice() {
        //初始化总价
        sum=0;
        //遍历所有的子集合
        for(int i=0;i<adapter.getGroupCount();i++)
        {
            for (int j=0;j<adapter.getChildrenCount(i);j++)
            {
                ChildBean child=adapter.getChild(i,j);
                //如果该对象被选中,则加上这个对象中的价钱
                if(child.isCheck)
                {
                    sum+=child.price*child.saleNum;
                }
            }
        }
        //得到总价,更新UI控件
        gouwuche_footer_price.setText(sum+"");
    }

    @Override
    public void delete() {
        //删除的接口回调

        //通知P层去删除数据,通过回调请求
    }
}
