package pers.jieao.guitarworld.module.BottomNavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.*;
/**
 * Created by 桀骜 on 2018/9/4.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setList(List<Fragment> list) {
        this.list = list;
        notifyDataSetChanged();
    }

     //重写getItem()和getCount()方法，用于内部调用list中的内容以显示
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }
}