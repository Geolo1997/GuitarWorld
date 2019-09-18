package pers.geolo.guitarworld.controller.index;

import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.OnClick;
import org.microview.core.ControllerManager;
import org.microview.core.ViewController;
import org.microview.core.ViewParams;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.BaseController;
import pers.geolo.guitarworld.controller.base.BeanFactory;
import pers.geolo.guitarworld.entity.DataCallback;
import pers.geolo.guitarworld.entity.Information;
import pers.geolo.guitarworld.model.InformationModel;

import java.util.ArrayList;
import java.util.List;

public class DiscoverController extends BaseController {

    @BindView(R.id.banner_layout)
    FrameLayout bannerLayout;
    @BindView(R.id.music_discover_layout)
    FrameLayout musicDiscoverLayout;

//    @BindView(R.id.refresh_layout)
//    SwipeRefreshLayout refreshLayout;

    private BannerController bannerController;

    private InformationModel informationModel = BeanFactory.getBean(InformationModel.class);

    @Override
    protected int getLayout() {
        return R.layout.discover;
    }

    @Override
    public void initView(ViewParams viewParams) {
        bannerController = new BannerController();
        ControllerManager.load(bannerLayout, bannerController, new ViewParams("informationList", new ArrayList<>()));
//        ControllerManager.load(musicDiscoverLayout, new MusicDiscoverController());
        loadInformations();
    }

    private void loadInformations() {
        informationModel.getBannerInformation(new DataCallback<List<Information>>() {
            @Override
            public void onReturn(List<Information> information) {
                bannerController.initView(new ViewParams("informationList", information));
            }

            @Override
            public void onError(String message) {

            }
        });
    }
}
