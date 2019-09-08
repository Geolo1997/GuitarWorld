package pers.geolo.guitarworld.controller.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import butterknife.BindView;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.base.BaseController;
import pers.geolo.guitarworld.controller.music.MusicDiscoverController;

public class DiscoverController extends BaseController {

//    @BindView(R.id.refresh_layout)
//    SwipeRefreshLayout refreshLayout;

    private BannerController bannerController;
    private MusicDiscoverController musicDiscoverController;

    @Override
    public Object getLayoutView() {
        return R.layout.controller_discover;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initRefreshLayout();
        bannerController = new BannerController();
        loadRootFragment(R.id.banner_layout, bannerController);
        musicDiscoverController = new MusicDiscoverController();
        loadRootFragment(R.id.music_discover_layout, musicDiscoverController);
    }

    private void initRefreshLayout() {
//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//            }
//        });
    }
}
