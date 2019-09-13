package pers.geolo.guitarworld.controller.index;

import android.widget.FrameLayout;
import butterknife.BindView;
import org.microview.core.ControllerManager;
import org.microview.core.ViewParams;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.BaseController;
import pers.geolo.guitarworld.controller.music.MusicDiscoverController;

public class DiscoverController extends BaseController {

    @BindView(R.id.banner_layout)
    FrameLayout bannerLayout;
    @BindView(R.id.music_discover_layout)
    FrameLayout musicDiscoverLayout;

//    @BindView(R.id.refresh_layout)
//    SwipeRefreshLayout refreshLayout;

    @Override
    protected int getLayout() {
        return R.layout.discover;
    }

    @Override
    public void initView(ViewParams viewParams) {
//        ControllerManager.load(bannerLayout, new BannerController());
//        ControllerManager.load(musicDiscoverLayout, new MusicDiscoverController());
    }
}
