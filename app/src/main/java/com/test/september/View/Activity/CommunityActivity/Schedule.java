package com.test.september.View.Activity.CommunityActivity;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.test.september.R;
import com.test.september.View.MyView.SelectView;
import java.util.ArrayList;
import java.util.List;

public class Schedule extends AppCompatActivity {

        SelectView sv_year,sv_month,sv_relation;
        TextView tv_year,tv_month,tv_relation;
        @SuppressLint("CutPasteId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_schedule);
            sv_year = (SelectView) findViewById(R.id.sv_year);
            sv_month = (SelectView) findViewById(R.id.sv_month);
            sv_relation = (SelectView) findViewById(R.id.sv_relation);

            tv_year = (TextView) findViewById(R.id.tv_year);
            tv_month = (TextView) findViewById(R.id.tv_month);
            tv_relation = (TextView) findViewById(R.id.tv_relation);

            List<String> list_year=new ArrayList<>();
            for(int i=2019;i<=2100;i++)
            {
                list_year.add(i+"");
            }

            sv_year.showValue(list_year, new SelectView.onSelect() {
                        @Override
                        public void onSelectItem(String value) {
                            tv_year.setText(value+"年");
                        }
                    }
            );
            List<String> list_month=new ArrayList<>();
            for(int i=1;i<=12;i++)
            {
                list_month.add(i+"");
            }

            sv_month.showValue(list_month, new SelectView.onSelect() {
                @Override
                public void onSelectItem(String value) {
                    tv_month.setText(value+"月");
                }
            });

            List<String> list_relation=new ArrayList<>();
//        for(int i=1;i<=6;i++)
//        {
//            list_month.add(i+"");
//        }

            for (int i=0;i<=24;i++)
            {
                if(i<=12) {
                    String ii = "上午"+String.valueOf(i);
                    list_relation.add(ii);
                }
                if (i>12)
                {
                    String ii="下午"+String.valueOf(i);
                    list_relation.add(ii);
                }
            }
//        list_relation.add("爸爸");
//        list_relation.add("妈妈");
//        list_relation.add("老公");
//        list_relation.add("老婆");
//
//        list_relation.add("儿子");
//        list_relation.add("女儿");
//        list_relation.add("爷爷");
//        list_relation.add("奶奶");




            sv_relation.showValue(list_relation, new SelectView.onSelect() {
                @Override
                public void onSelectItem(String value) {
                    tv_relation.setText("时间："+value+"点");
                }
            });
        }
}