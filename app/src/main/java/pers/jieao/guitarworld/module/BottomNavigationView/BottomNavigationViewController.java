package pers.jieao.guitarworld.module.BottomNavigationView;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 底部导航栏控制器
 *
 * Created by @桀骜
 * 2018/10/5
 */
public class BottomNavigationViewController {

    private static class Holder {

        private static final BottomNavigationViewController INSTANCE = new BottomNavigationViewController();
    }

    private BottomNavigationView bottomNavigationView;
    private MenuItem menuItem;
    private List<Integer> menuItemIdList;

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private List<Fragment> fragmentList;

    private AppCompatActivity activity;

    private BottomNavigationViewController() {}

    private void init() {
        //设置bottomNavigationView的事件监听
        bottomNavigationView.setOnNavigationItemSelectedListener(
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    menuItem = item;
                    int itemId = item.getItemId();
                    for (int i = 0; i < menuItemIdList.size(); i++) {
                        if (itemId == menuItemIdList.get(i)) {
                            viewPager.setCurrentItem(i);
                            return true;
                        }
                    }
                    return false;
                }
            }
        );

        //设置viewPager的事件监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPagerAdapter.setList(fragmentList);
        viewPager.setAdapter(viewPagerAdapter);

        //
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
    }

    public static BottomNavigationViewController getInstance() {
        return Holder.INSTANCE;
    }

    public static class Builder {

        private final BottomNavigationViewController INSTANCE;

        public Builder() {
            INSTANCE = Holder.INSTANCE;
            INSTANCE.menuItemIdList = new ArrayList<>();
            INSTANCE.fragmentList = new ArrayList<>();
        }

        public Builder setActivity(AppCompatActivity activity) {
            INSTANCE.activity = activity;
            INSTANCE.viewPagerAdapter = new ViewPagerAdapter(activity.getSupportFragmentManager());
            return this;
        }

        public Builder setBottomNavigationView(int bottomNavigationViewId) {
            Log.d("BNVC", INSTANCE.activity.toString());
            INSTANCE.bottomNavigationView = INSTANCE.activity.findViewById(bottomNavigationViewId);
            Log.d("BNVC", INSTANCE.bottomNavigationView.toString());
            return this;
        }

        public Builder setViewPager(int viewPagerId) {
            INSTANCE.viewPager = INSTANCE.activity.findViewById(viewPagerId);
            return this;
        }

        public Builder addItem(Fragment fragment, Integer menuItemId) {
            INSTANCE.fragmentList.add(fragment);
            INSTANCE.menuItemIdList.add(menuItemId);
            return this;
        }

        public BottomNavigationViewController build() {
            INSTANCE.init();
            return INSTANCE;
        }
    }
}