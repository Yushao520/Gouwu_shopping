package com.bewai.Gouwu_shopping.Aapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bewai.Gouwu_shopping.R;
import com.bewai.Gouwu_shopping.bean.Bean;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by &那么& on 2017/10/26.
 */

public class MyAapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Bean.DataBean> data;

    //定义两种类型
    private final int TYPE_0=0;
    private final int TYPE_1=1;

    public MyAapter(Context context, List<Bean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    //在create里判断item的类型
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //判断类型
        if (viewType == TYPE_0){
            //初始化布局
            View view = LayoutInflater.from(context).inflate(R.layout.item_01, null);
            final MyViewHolder holder = new MyViewHolder(view);
            //设置点击事件
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    ItemCleck.setItemCleck(v,position);
                }
            });
            return holder;
            //判断类型
        }else if(viewType == TYPE_1){
            //初始化布局
            View view1= LayoutInflater.from(context).inflate(R.layout.item_02,null);
            final MyViewHolder1 holder1 = new MyViewHolder1(view1);
            //设置点击事件
            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder1.getLayoutPosition();
                    ItemCleck.setItemCleck(v,position);
                }
            });
            return holder1;
        }
        return null;
    }
    //赋值
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //要判断属于哪个ViewHolder才会点到属性
        if (holder instanceof MyViewHolder){
            Glide.with(context).load(data.get(position).getIcon()).into(((MyViewHolder) holder).item_img01);
            ((MyViewHolder) holder).item_name01.setText(data.get(position).getName());
        } else if (holder instanceof MyViewHolder1) {
            Glide.with(context).load(data.get(position).getIcon()).into(((MyViewHolder1) holder).item_img02);
        }
    }
    //判断item是哪种类型的条件
    @Override
    public int getItemViewType(int position) {
        if (position%2==0){
            return TYPE_0;
        }else{
            return TYPE_1;
        }
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    //接口回掉:recyclerView的点击事件
    private OnItemCleck ItemCleck;
    public interface OnItemCleck{
        void setItemCleck(View v,int position);
    }
    public void setOnItemCleckListener(OnItemCleck itemCleck) {
        ItemCleck = itemCleck;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        private  ImageView item_img01;
        private  TextView item_name01;

        public MyViewHolder(View itemView) {
            super(itemView);
            item_img01 = itemView.findViewById(R.id.item_img01);
            item_name01 = itemView.findViewById(R.id.item_name01);

        }
        public  void additem (List<Bean.DataBean> newdatda){
            newdatda.addAll(data);
            data.removeAll(data);
            data.addAll(newdatda);
            notifyDataSetChanged();

        }
        public void addMoreItem(List<Bean.DataBean> newDatas) {
            data.addAll(newDatas);
            notifyDataSetChanged();
        }
    }

    public class MyViewHolder1 extends RecyclerView.ViewHolder{
        private  ImageView item_img02;
        public MyViewHolder1(View itemView) {
            super(itemView);
            item_img02 = itemView.findViewById(R.id.item_img02);
        }

    }
    public  void additem (List<Bean.DataBean> newdatda){
        newdatda.addAll(data);
        data.removeAll(data);
        data.addAll(newdatda);
        notifyDataSetChanged();

    }
    public void addMoreItem(List<Bean.DataBean> newDatas) {
        data.addAll(newDatas);
        notifyDataSetChanged();
    }
}