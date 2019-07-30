package pers.geolo.guitarworld.delegate.music;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.BindView;
import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.delegate.base.BaseDelegate;
import pers.geolo.guitarworld.ui.magicnavigator.MagicNavigator;
import pers.geolo.guitarworld.ui.viewpagernavigation.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/11
 */
public class MusicDiscoverNavigationDelegate extends BaseDelegate {

    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    FragmentContainerHelper fragmentContainerHelper;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    public Object getLayout() {
        return R.layout.music_discover_navigation;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
//        // onBindView ViewPager
//        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
//        List<Fragment> fragments = getFragments();
//        viewPagerAdapter.setList(fragments);
//        viewPager.setAdapter(viewPagerAdapter);
//        // onBindView MagicNavigator
//        List<String> titles = getTitles();
//        fragmentContainerHelper = new FragmentContainerHelper(magicIndicator);
//        CommonNavigator commonNavigator = new CommonNavigator(getContext());
//        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
//            @Override
//            public int getCount() {
//                return fragments.size();
//            }
//
//            @Override
//            public IPagerTitleView getTitleView(Context context, int index) {
//                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
//                colorTransitionPagerTitleView.setNormalColor(Color.GRAY);
//                colorTransitionPagerTitleView.setSelectedColor(Color.BLACK);
//                colorTransitionPagerTitleView.setText(titles.get(index));
////                colorTransitionPagerTitleView.setMaxWidth(magicIndicator.getWidth() / 3);
//                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        viewPager.setCurrentItem(index);
//                    }
//                });
//                return colorTransitionPagerTitleView;
//            }
//
//            @Override
//            public IPagerIndicator getIndicator(Context context) {
//                LinePagerIndicator indicator = new LinePagerIndicator(context);
//                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
//                return indicator;
//            }
//        });
//        magicIndicator.setNavigator(commonNavigator);
//        ViewPagerHelper.bind(magicIndicator, viewPager);

        new MagicNavigator(getContext(), getChildFragmentManager(), viewPager, magicIndicator)
                .setItem("推荐", MusicListDelegate.newInstance(new HashMap<>()))
                .setItem("排行", MusicListDelegate.newInstance(new HashMap<>()))
                .setItem("飙升", MusicListDelegate.newInstance(new HashMap<>()))
                .init();
    }

    private List<String> getTitles() {
        List<String> titles = new ArrayList<>();
        titles.add("推荐");
        titles.add("排行");
        titles.add("飙升");
        return titles;
    }

    List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        HashMap<String, Object> filter1 = new HashMap<>();
        filter1.put("recommand", true);
        fragments.add(MusicListDelegate.newInstance(filter1));
        HashMap<String, Object> filter2 = new HashMap<>();
        filter1.put("sort", true);
        fragments.add(MusicListDelegate.newInstance(filter2));
        HashMap<String, Object> filter3 = new HashMap<>();
        filter1.put("trending", true);
        fragments.add(MusicListDelegate.newInstance(filter3));
        return fragments;
    }
}
