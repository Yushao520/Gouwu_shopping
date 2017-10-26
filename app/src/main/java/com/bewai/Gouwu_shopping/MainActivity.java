package com.bewai.Gouwu_shopping;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bewai.Gouwu_shopping.Aapter.MyAapter;
import com.bewai.Gouwu_shopping.bean.Bean;
import com.bewai.Gouwu_shopping.utils.HttpUtils;
import com.bewai.Gouwu_shopping.utils.TwoImageUtils;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Bean.DataBean> data1;
    private ImageView img_01;
    private RecyclerView recycler_01;
    private MyAapter apter;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                apter = new MyAapter(MainActivity.this,data1);
                recycler_01.setAdapter(apter);
                apter.setOnItemCleckListener(new MyAapter.OnItemCleck() {
                    @Override
                    public void setItemCleck(View v, int position) {
                        Toast.makeText(MainActivity.this,"成功",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    };
    private SwipeRefreshLayout sw_layout;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img_01 = (ImageView) findViewById(R.id.img_01);
        final String url = "http://img.taopic.com/uploads/allimg/120727/201995-120HG1030762.jpg";
        findViewById(R.id.img_01).setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View view) {
               TwoImageUtils.loadImage(url,img_01);
           }});
        sw_layout = (SwipeRefreshLayout) findViewById(R.id.sw_layout);
        recycler_01 = (RecyclerView) findViewById(R.id.recycler_01);

     
        LinearLayoutManager  layoutManager = new LinearLayoutManager(this);
        recycler_01.setLayoutManager(layoutManager);


        sw_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               handler.postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       List<Bean.DataBean> newDatas = new ArrayList<Bean.DataBean>();
                       for (int i = 0;i<5;i++){
                           Bean.DataBean bb = new Bean.DataBean();
                           bb.setName("这是我刷新的"+i);
                           newDatas.add(bb);
                       }
                       apter.additem(newDatas);
                       sw_layout.setRefreshing(false);
                       Toast.makeText(MainActivity.this, "更新了五条数据...", Toast.LENGTH_SHORT).show();

                   }
               },5000);
            }
        });


        HttpUtils instance = HttpUtils.getInstance();
        instance.getJson("http://120.27.23.105/product/getCarts?uid=149", new HttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {
                Gson gson = new Gson();
                Bean bean = gson.fromJson(data, Bean.class);
                data1 = bean.getData();
                handler.sendEmptyMessage(1);

            }
            
        });



    }

    private void getData(){
        try {
            getContent("http://120.27.23.105/product/getCatagory");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //获取网络数据
    private void getContent(String path){
        new AsyncTask<String, Void, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s == null){
                    return;
                }
                Gson gson = new Gson();
                Bean bean = gson.fromJson(s, Bean.class);
                List<Bean.DataBean> data = bean.getData();
                DisplayImageOptions  options = new DisplayImageOptions.Builder().build();
                ImageLoader.getInstance().displayImage(data.get(2).getIcon(),img_01,options);


            }

            @Override
            protected String doInBackground(String... strings) {
                try {
                    String s = strings[0];
                    URL url = new URL(s);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(5000);
                    connection.setConnectTimeout(5000);

                    int code = connection.getResponseCode();
                    if(code == 200){

                        InputStream is = connection.getInputStream();
                        String json = getJson(is);
                        return json;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        }.execute(path);
    }

    //将数据转换为字节流
    private String getJson(InputStream is){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while((len = is.read(buffer))!=-1){
                baos.write(buffer,0,len);
            }
            is.close();
            baos.close();
            return baos.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
