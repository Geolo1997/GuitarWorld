package pers.geolo.guitarworld.ui.magicnavigator;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;
import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.ui.viewpagernavigation.ViewPagerAdapter;
import pers.geolo.guitarworld.util.WidgetUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/23
 */
public class MagicNavigator {

    private Context context;
    private FragmentManager fragmentManager;
    private ViewPager viewPager;
    private MagicIndicator magicIndicator;
    private List<String> titles;
    private List<Fragment> fragments;

    public MagicNavigator(Context context, FragmentManager fragmentManager, ViewPager viewPager, MagicIndicator magicIndicator) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.viewPager = viewPager;
        this.magicIndicator = magicIndicator;
        titles = new ArrayList<>();
        fragments = new ArrayList<>();
    }

    public MagicNavigator setItem(String title, Fragment fragment) {
        titles.add(title);
        fragments.add(fragment);
        return this;
    }

    public void init() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(fragmentManager);
        viewPagerAdapter.setList(fragments);
        viewPager.setAdapter(viewPagerAdapter);
        // init MagicNavigator
        FragmentContainerHelper fragmentContainerHelper = new FragmentContainerHelper(magicIndicator);
        CommonNavigator commonNavigator = new CommonNavigator(context);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                ColorTransitionPagerTitleView titleView = new ColorTransitionPagerTitleView(context);
                titleView.setNormalColor(Color.GRAY);
                titleView.setSelectedColor(context.getResources().getColor(R.color.colorPrimary));
                //TODO 设置黑线为主题色
                titleView.setText(titles.get(index));
                WidgetUtils.measure(magicIndicator, new ViewTreeObserver.OnPreDrawListener() {

                    private boolean isMeasured = false;

                    @Override
                    public boolean onPreDraw() {
                        if (!isMeasured) {
                            int width = magicIndicator.getMeasuredWidth();
                            titleView.setWidth(width / 3);
                            if (width != 0) {
                                isMeasured = true;
                            }
                        }
                        return true;
                    }
                });
                titleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return titleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }
}
