package pers.geolo.guitarworld.controller.music;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.BindView;
import net.lucode.hackware.magicindicator.MagicIndicator;
import org.microview.core.ViewParams;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.BaseController;

/**
 * 乐曲资源页面
 *
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/23
 */
public class MusicResourceController extends BaseController {

    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.place_holder)
    View placeHolder;

    @Override
    protected int getLayout() {
        return R.layout.music_resource;
    }

    @Override
    public void initView(ViewParams viewParams) {

    //        new MagicNavigator(getContext(), getChildFragmentManager(), viewPager, magicIndicator)
//                .setItem("乐谱", MusicScoreListController.newInstance(musicId))
//                .setItem("教学", WorksListController.newInstance(new HashMap<>()))
//                .setItem("演奏", WorksListController.newInstance(new HashMap<>()))
//                .init();
    }
}
