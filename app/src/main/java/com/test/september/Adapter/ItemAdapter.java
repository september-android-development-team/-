package com.test.september.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.september.R;
import com.test.september.widget.MessageA;
import com.test.september.widget.MessageB;

import java.util.List;

/**
 * @author BULUSILI
 * @描述    仿新浪消息不同listView不同item布局适配器
 * @项目名称
 * @包名 com.example.message
 * @2016/8/4--14:04
 */
public class ItemAdapter extends BaseAdapter{
    public static final int TYPE_TITLE = 0; //两种类型
    public static final int TYPE_COMPANY = 1;
    Context mContext;
    List<MessageA>mList1;
    List<MessageB>mList2;

    public ItemAdapter(Context context, List<MessageA> list1, List<MessageB> list2) {
        mContext = context;
        mList1 = list1;
        mList2 = list2;
    }

    @Override
    public int getCount() {
        return (mList1.size()+mList2.size());

    }
    //这个不用管
    @Override
    public Object getItem(int i) {
        return null;
    }
    //这个不用管
    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position<=2){
            return TYPE_TITLE;
        }else {
            return TYPE_COMPANY;
        }

    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //声明一下viewHolder
        MessageAviewHolder holderA;
        MessageBviewHolder holderB;
        switch (getItemViewType(i)){
            case TYPE_TITLE:
                if (view==null){
                    view= LayoutInflater.from(mContext).inflate(R.layout.item_setting2,null);
                    holderA=new MessageAviewHolder();
                    holderA.messageIndex= (ImageView) view.findViewById(R.id.message_item2_img);
                    holderA.text= (TextView) view.findViewById(R.id.message_item2_text);
                    holderA.next= (ImageView) view.findViewById(R.id.message_item2_back);
                    view.setTag(holderA);
                }else{
                    holderA= (MessageAviewHolder) view.getTag();
                }
                //赋值  通过这一步说明还是要建实体类的
                holderA.messageIndex.setImageResource(mList1.get(i).getMessageIndex());
                holderA.text.setText(mList1.get(i).getText());
                holderA.next.setImageResource(mList1.get(i).getNext());
                break;
            case TYPE_COMPANY:
                if (view==null){
                    view= LayoutInflater.from(mContext).inflate(R.layout.item_setting1,null);
                    holderB=new MessageBviewHolder();
                    holderB.messageIndex= (ImageView) view.findViewById(R.id.message_item1_img);
                    holderB.name= (TextView) view.findViewById(R.id.message_item1_text1);
                    holderB.content= (TextView) view.findViewById(R.id.message_item1_text2);
                    holderB.time= (TextView) view.findViewById(R.id.message_item1_time);
                    view.setTag(holderB);
                }else{
                    holderB= (MessageBviewHolder) view.getTag();
                }
                //赋值  通过这一步说明还是要建实体类的
                //一定要减去上一个类型的数量，不然越界
                holderB.messageIndex.setImageResource(mList2.get(i-3).getMessageIndex());
                holderB.name.setText(mList2.get(i-3).getName());
                holderB.content.setText(mList2.get(i-3).getContent());
                holderB.time.setText(mList2.get(i-3).getTime());
                break;
        }
        return view;
    }
    class MessageAviewHolder{
        ImageView messageIndex;
        TextView text;
        ImageView next;
    }
    class MessageBviewHolder{
        ImageView messageIndex;
        TextView name;
        TextView content;
        TextView time;
    }
}
