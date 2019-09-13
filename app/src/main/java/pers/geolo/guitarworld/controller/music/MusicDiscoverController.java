package pers.geolo.guitarworld.controller.music;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import butterknife.BindView;
import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import org.microview.core.ViewController;
import org.microview.core.ViewParams;
import org.microview.support.MicroviewPageAdapter;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.BaseController;
import pers.geolo.guitarworld.ui.MagicNavigator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/11
 */
public class MusicDiscoverController extends BaseController {

    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Override
    protected int getLayout() {
        return R.layout.music_discover;
    }

    @Override
    public void initView(ViewParams viewParams) {
        List<MusicListItem> musicListItems = new ArrayList<>();
        musicListItems.add(new MusicListItem(new MusicDiscoverController(), new ViewParams(), "推荐"));
        musicListItems.add(new MusicListItem(new MusicDiscoverController(), new ViewParams(), "排行"));
        musicListItems.add(new MusicListItem(new MusicDiscoverController(), new ViewParams(), "飙升"));
        MicroviewPageAdapter pageAdapter = new MicroviewPageAdapter(musicListItems.stream()
                .map(new Function<MusicListItem, ViewController>() {
                    @Override
                    public ViewController apply(MusicListItem musicListItem) {
                        return musicListItem.controller;
                    }
                })
                .collect(Collectors.toList()), musicListItems.stream()
                .map(new Function<MusicListItem, ViewParams>() {
                    @Override
                    public ViewParams apply(MusicListItem musicListItem) {
                        return musicListItem.params;
                    }
                })
                .collect(Collectors.toList()));
        viewPager.setAdapter(pageAdapter);
        // 初始化MagicIndicator
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return musicListItems.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.GRAY);
                colorTransitionPagerTitleView.setSelectedColor(Color.BLACK);
                colorTransitionPagerTitleView.setText(musicListItems.get(index).title);
//                colorTransitionPagerTitleView.setMaxWidth(magicIndicator.getWidth() / 3);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
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

    private static class MusicListItem {
        private ViewController controller;
        private ViewParams params;
        private String title;

        public MusicListItem(ViewController controller, ViewParams params, String title) {
            this.controller = controller;
            this.params = params;
            this.title = title;
        }
    }
}
