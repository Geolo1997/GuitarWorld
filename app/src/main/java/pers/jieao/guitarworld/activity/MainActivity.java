package pers.jieao.guitarworld.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import pers.jieao.guitarworld.R;
import pers.jieao.guitarworld.base.BaseActivity;
import pers.jieao.guitarworld.fragment.DynamicFragment;
import pers.jieao.guitarworld.fragment.ProfileFragment;
import pers.jieao.guitarworld.fragment.ShopFragment;
import pers.jieao.guitarworld.fragment.ToolsFragment;
import pers.jieao.guitarworld.module.BottomNavigationView.BottomNavigationViewController;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBottomNavigationView();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    /**
     * 设置底部导航栏和左右滑动视图关联
     */
    private void setBottomNavigationView() {
        new BottomNavigationViewController.Builder()
                .setActivity(this)
                .setBottomNavigationView(R.id.bottomNavigationView)
                .setViewPager(R.id.view_pager)
                .addItem(new DynamicFragment(), R.id.it_dynamic)
                .addItem(new ToolsFragment(), R.id.it_tools)
                .addItem(new ShopFragment(), R.id.it_shop)
                .addItem(new ProfileFragment(), R.id.it_profile)
                .build();
    }

}