package pers.geolo.guitarworld.controller.music;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.BindView;
import net.lucode.hackware.magicindicator.MagicIndicator;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.base.BaseController;
import pers.geolo.guitarworld.controller.works.WorksListController;
import pers.geolo.guitarworld.ui.MagicNavigator;

import java.util.HashMap;

/**
 * 乐曲资源页面
 *
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/23
 */
public class MusicResourceController extends BaseController {

    private static final String MUSIC_ID = "MUSIC_ID";
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.place_holder)
    View placeHolder;

    public static MusicResourceController newInstance(int musicId) {

        Bundle args = new Bundle();
        args.putInt(MUSIC_ID, musicId);
        MusicResourceController fragment = new MusicResourceController();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object getLayoutView() {
        return R.layout.music_resource;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        int musicId = getArguments().getInt(MUSIC_ID);
//        new MagicNavigator(getContext(), getChildFragmentManager(), viewPager, magicIndicator)
//                .setItem("乐谱", MusicScoreListController.newInstance(musicId))
//                .setItem("教学", WorksListController.newInstance(new HashMap<>()))
//                .setItem("演奏", WorksListController.newInstance(new HashMap<>()))
//                .init();
    }

    public void setPlaceHolderVisible(int visible) {
        placeHolder.setVisibility(visible);
    }
}
