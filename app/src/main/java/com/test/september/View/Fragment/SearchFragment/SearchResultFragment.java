package com.test.september.View.Fragment.SearchFragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.september.R;
import com.test.september.View.Activity.Main2Activity;
import com.test.september.View.Fragment.BaseFragment;
import com.test.september.listener.search_contact;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class SearchResultFragment extends BaseFragment {
    private ListView mainLView;
    private String getSearch_url="https://api.cnsepte.com:448/user/research";
    private  List<Data1.data> userBeanList;
    private EditText editText;
    private String responseData;
    private int p;
    private search_contact sendMessage;
    private LocalBroadcastManager broadcastManager;
    private ResultAdapter adapter;
//    LoadingView loadingView;

//    @SuppressLint("HandlerLeak")
//    private Handler handler = new Handler(){
//        public void handleMessage(android.os.Message msg){
//            super.handleMessage(msg);
//            switch (msg.what){
//                case 0:
//
//                    break;
//                default:
//                    break;
//            }
//        }
//    };


    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_search_result, null);

//        loadingView=view.findViewById(R.id.loadView);
//        loadingView.setVisibility(View.VISIBLE);
//        search();
        editText=getActivity().findViewById(R.id.et_input);
        mainLView=view.findViewById(R.id.lv_search);
        registerReceiver();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//            mActivity = getActivity();
            //注册广播
        registerReceiver();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void initData() {
        super.initData();
//        search();
    }

    public void search(){
        String ss=editText.getText().toString();
        send(ss);

    }

    /**
     * 注册广播接收器
     */
    private void registerReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(mContext);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("jerry");
        broadcastManager.registerReceiver(mAdDownLoadReceiver, intentFilter);
    }




    //原创作品，未经允许禁止转载，转载请注明来自：http://www.cnblogs.com/jiangbeixiaoqiao/
    private BroadcastReceiver mAdDownLoadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, final Intent intent) {
            String change = intent.getStringExtra("change");
            String ss=intent.getStringExtra("search");
            if ("yes".equals(change)) {
                send(ss);
                adapter = new ResultAdapter(mContext,userBeanList);
                mainLView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                // 这地方只能在主线程中刷新UI,子线程中无效，因此用Handler来实现
//                new Thread(){
//                    public void run(){
//                        handler.sendEmptyMessage(0);
//                    }
//                }.start();
//                new Handler().post(new Runnable() {
//                    public void run() {
//                        //在这里来写你需要刷新的地方
//                        //例如：testView.setText("恭喜你成功了");
////                        search();
//
//                    }
//
//                });
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    /**
     * 注销广播
     */
    @Override
    public void onDetach() {
        super.onDetach();
        broadcastManager.unregisterReceiver(mAdDownLoadReceiver);
    }


    public void send(final String Username) {
//        final int Time,final String Token,
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();   //定义一个OKHttpClient实例
                    RequestBody requestBody = new FormBody.Builder()
                            .add("type","0")
                            .add("username", Username)
//                            .add("password",Password)
                            .build();
                    Log.d("username", Username);
//                    Log.d("Password", Password);
                    //实例化一个Respon对象，用于发送HTTP请求
                    Request request = new Request.Builder()
                            .url(getSearch_url)             //设置目标网址
//                    http://www.domain/user/login
//                    https://api.cnsepte.com:88/user/login
                            .post(requestBody)
                            .build();
                    Response response = okHttpClient.newCall(request).execute();  //获取服务器返回的数据
                    if (response.body() != null) {
                        responseData = response.body().string();//存储服务器返回的数据
                        Log.d("data", responseData);
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//                                    textView.setText(responseData);
//
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }).start();
                        parseJSONWithGSON(responseData);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @SuppressLint("HandlerLeak")
    private void parseJSONWithGSON(String json) {
        Gson gson = new Gson();
//        java.lang.reflect.Type type = new TypeToken<Main2Activity.Data1>() {}.getType();
//        Main2Activity.Data1 data = gson.fromJson(json,type);

        // 将Json数组解析成相应的映射对象列表
//            public static <T> List<T> parseJsonArrayWithGson(String jsonData,Class<T> type) {
//       Gson gson = new Gson();
//       List<T> result = gson.fromJson(jsonData, new TypeToken<List<T>>() {

//        String strByJson = JsonToStringUtil.getStringByJson();
//        String strByJson = JsonToStringUtil.getStringByJson(this, R.raw.juser_3);
        //GSON直接解析成对象
        //GSON直接解析成对象
        //对象中拿到集合
        //展示到UI中

        java.lang.reflect.Type type = new TypeToken<Data1>() {}.getType();
        Data1 resultBean = gson.fromJson(json,type);
        p=resultBean.getCode();
        Log.d("p",String.valueOf(p));
        //对象中拿到集合
        userBeanList = resultBean.getMuser();
//        if (p==200){
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        adapter = new ResultAdapter(mContext,userBeanList);
//                        mainLView.setAdapter(adapter);

//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//        }
        //展示到UI中
        //消息接收处理的handler
        Log.d("your", String.valueOf(userBeanList));
//        Context context,
    };//listdata和str均可

//        List<Data1.data> yourList=data.muser;
//        for(int i = 0 ; i <2 ; i++) {

//        }



//        value = (float) data.getResultObj().getValue();
//        uuid=data.getData().getUuid();
//        code=data.getCode();
//        String S_code=String.valueOf(code);
//        if(code==400)
//        {
//
//        }else {
//            Log.d("code", S_code);
//            Log.d("uuid", uuid);
//        }

    public class ResultAdapter extends BaseAdapter {
        private Context context;
        private List<Data1.data> list;

        public ResultAdapter(Context context,List<Data1.data> list) {
            this.list = list;
           this.context = context;
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.list_item, null);
                textView=convertView.findViewById(R.id.tv_item);
                textView.setText(list.get(position).getUid());
            }
            return convertView;
        }
    }



    public class Data1 {

        private int code;
        private String msg;
        //        private Main2Activity.Data1.data data;
        private List<Data1.data> data;

        public class data {
            //            private int uid;
            private String uuid;

            //            private String phone;
//            private String email;
//            private String rtime;
//            private String uuid;
//            private String sessionid;
            public String getUid() {
                return uuid;
            }

            public void setUid(String uuid) {
                this.uuid = uuid;
            }

        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public List<Data1.data> getMuser() {
            return data;
        }

        public void setMuser(List<Data1.data> muser) {
            this.data = muser;
        }
    }
    @Override
    public void setViewListener() {

    }

}
