package com.test.september.View.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.test.september.R;
import com.test.september.util.SharedPreferences.SharedPrefUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private String getCode_url = "https://api.cnsepte.com:448/code";
    private String getSearch_url="https://api.cnsepte.com:448/user/research";
    private String responseData;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private EditText editText;
    private TextView textView;
    private ListView mainLView;

    private  List<Data1.data> userBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        button1=findViewById(R.id.cccccc);
        button2=findViewById(R.id.sign_out);
        button3=findViewById(R.id.denglu);
        button4=findViewById(R.id.search_btn);
        editText=findViewById(R.id.search_edit_text);
        textView=findViewById(R.id.search_history_text);
        mainLView=findViewById(R.id.min);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        search();
    }

    /*****
     * 请求验证码
     * 方式：Get
     * *****/
    public void sendRequestWithOkHttp() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();   //定义一个OKHttpClient实例
                    RequestBody requestBody = new FormBody.Builder()
                            .build();

                    //实例化一个Respon对象，用于发送HTTP请求
                    Request request = new Request.Builder()
                            .url("https://api.cnsepte.com:448/user/test")             //设置目标网址
                            .post(requestBody)
                            .build();
                    Response response = okHttpClient.newCall(request).execute();  //获取服务器返回的数据
                    if (response.body() != null) {
                        responseData = response.body().string();//存储服务器返回的数据
                        Log.d("register_data", responseData);
//                        register_parseJSONWithGSON(responseData);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cccccc:
            {
                sendRequestWithOkHttp();
            }break;
            case R.id.sign_out:
            {
                //保存登录状态
                SharedPrefUtil.setParam(this, SharedPrefUtil.IS_LOGIN, false);
//                Intent intent=new Intent(this, Login.class);
//                startActivity(intent);
            }break;
            case R.id.denglu:
            {
                //保存登录状态
                SharedPrefUtil.setParam(this, SharedPrefUtil.IS_LOGIN, true);
            }break;
            case R.id.search_btn:
            {
                //保存登录状态
                search();
            }break;
            default:break;
        }
    }

    public void search(){
        String ss=editText.getText().toString();
        send(ss);
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
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    textView.setText(responseData);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
//                        parseJSONWithGSON(responseData);

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
        int p=resultBean.getCode();
        Log.d("p",String.valueOf(p));
        //对象中拿到集合
        userBeanList = resultBean.getMuser();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mainLView.setAdapter(new ResultAdapter(userBeanList));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
//       private Context context;
       private List<Data1.data> list;

       public ResultAdapter(List<Data1.data> list) {
           this.list = list;
//           this.context = context;
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
               convertView = View.inflate(Main2Activity.this, R.layout.list_item, null);
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
        private List<data> data;

        public class data{
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
        public List<data> getMuser() {
            return data;
        }

        public void setMuser(List<data> muser) {
            this.data = muser;
        }
//        public Main2Activity.Data1.data getData() {
//            return data;
//        }
//
//        public void setData(Main2Activity.Data1.data data) {
//            this.data = data;
//        }


    }






//    public void onViewClicked() {
//        sendRequestWithOkHttp();
//    }
//    public void onViewClicked2() {
//        Intent intent=new Intent(this, Login.class);
//        startActivity(intent);
//    }
}
