package pers.geolo.guitarworld.delegate.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import butterknife.BindView;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.delegate.base.BaseDelegate;
import pers.geolo.guitarworld.delegate.music.MusicDiscoverDelegate;

public class DiscoverDelegate extends BaseDelegate {

//    @BindView(R.id.refresh_layout)
//    SwipeRefreshLayout refreshLayout;

    private BannerDelegate bannerDelegate;
    private MusicDiscoverDelegate musicDiscoverDelegate;

    @Override
    public Object getLayoutView() {
        return R.layout.delegate_discover;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initRefreshLayout();
        bannerDelegate = new BannerDelegate();
        loadRootFragment(R.id.banner_layout, bannerDelegate);
        musicDiscoverDelegate = new MusicDiscoverDelegate();
        loadRootFragment(R.id.music_discover_layout, musicDiscoverDelegate);
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
