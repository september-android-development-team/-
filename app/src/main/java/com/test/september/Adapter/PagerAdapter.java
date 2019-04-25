package com.test.september.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.test.september.View.Fragment.BaseFragment;

import java.util.List;

/**
 * Created by Administrator on 2016/10/7.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> list;

    public PagerAdapter(FragmentManager fm, List<BaseFragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }


}
