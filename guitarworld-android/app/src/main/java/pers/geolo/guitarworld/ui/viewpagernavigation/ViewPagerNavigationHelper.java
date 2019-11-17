package pers.geolo.guitarworld.ui.viewpagernavigation;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/10
 */
public class ViewPagerNavigationHelper {

    private BottomNavigationView bottomNavigationView;
    private MenuItem menuItem;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    private List<Integer> menuItemIdList = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();

    private ViewPagerNavigationHelper() {
    }

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

//        //
//        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
    }

    public static class Builder {
        private ViewPagerNavigationHelper helper;

        public Builder(FragmentManager fragmentManager) {
            helper = new ViewPagerNavigationHelper();
            helper.viewPagerAdapter = new ViewPagerAdapter(fragmentManager);
        }

        public Builder bottomNavigationView(BottomNavigationView bottomNavigationView) {
            helper.bottomNavigationView = bottomNavigationView;
            return this;
        }

        public Builder viewPager(ViewPager viewPager) {
            helper.viewPager = viewPager;
            return this;
        }

        public Builder addItem(Fragment fragment, Integer menuItemId) {
            helper.fragmentList.add(fragment);
            helper.menuItemIdList.add(menuItemId);
            return this;
        }

        public ViewPagerNavigationHelper build() {
            helper.init();
            return helper;
        }
    }

}
