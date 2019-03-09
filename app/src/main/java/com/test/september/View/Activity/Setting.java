package com.test.september.View.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.test.september.Adapter.ItemAdapter;
import com.test.september.R;
import com.test.september.widget.MessageA;
import com.test.september.widget.MessageB;

import java.util.ArrayList;
import java.util.List;

public class Setting extends AppCompatActivity {

    ListView mListView;
    ItemAdapter mAdapter;
    // List<String> mList;
    List<MessageA> mList1; //固定集合
    List<MessageB>mList2;  //聊天的集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
//        initView();
//        initData();
//        initAdapter();
    }
    private void initView() {
        mListView= (ListView) findViewById(R.id.message_lv);
    }

    private void initData() {
      /*  mList=new ArrayList<>();
        for (int i = 0; i <20 ; i++) {
            mList.add(Integer.toString(i));
        }*/
        mList1=new ArrayList<>();
        mList2=new ArrayList<>();
        MessageA messageA1=new MessageA(R.mipmap.ic_launcher_round,"@我的",R.drawable.ic_right_arrow);
        MessageA messageA2=new MessageA(R.mipmap.ic_launcher_round,"评论",R.drawable.ic_right_arrow);
        MessageA messageA3=new MessageA(R.mipmap.ic_launcher_round,"赞",R.drawable.ic_right_arrow);
        mList1.add(messageA1);
        mList1.add(messageA2);
        mList1.add(messageA3);

        for (int i = 0; i <10 ; i++) {
            MessageB messageB=new MessageB(R.mipmap.ic_launcher,"小凡","你好","09:38");
            mList2.add(messageB);
        }
    }

    private void initAdapter() {
        //mAdapter=new MyMessAdapter(this);
        mAdapter=new ItemAdapter(this,mList1,mList2);
        mListView.setAdapter(mAdapter);
    }
}
