package pers.geolo.guitarworld.controller.index;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.OnClick;
import com.github.clans.fab.FloatingActionMenu;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.base.BaseController;
import pers.geolo.guitarworld.controller.base.BeanFactory;
import pers.geolo.guitarworld.controller.works.PublishImageTextWorksController;
import pers.geolo.guitarworld.controller.works.PublishVideoWorksController;
import pers.geolo.guitarworld.controller.works.WorksListController;
import pers.geolo.guitarworld.entity.GetWorksListSuccessEvent;
import pers.geolo.guitarworld.model.AuthModel;

import java.util.HashMap;

public class DynamicController extends BaseController {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.works_list_layout)
    LinearLayout worksListLayout;
    @BindView(R.id.floating_menu)
    FloatingActionMenu floatingMenu;

    AuthModel authModel = BeanFactory.getBean(AuthModel.class);
    WorksListController worksListController;

    @Override
    public Object getLayoutView() {
        return R.layout.dynamic;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
//        loadRootFragment(R.id.add_layout, new PublishWorksMenuController());
        // 初始化刷新控件
        initRefreshLayout();
        initWorksListController();
        refreshLayout.setRefreshing(true);
    }

    private void initRefreshLayout() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                worksListController.loadWorksList();
            }
        });
        // 修复嵌套滑动冲突
        refreshLayout.setOnChildScrollUpCallback(new SwipeRefreshLayout.OnChildScrollUpCallback() {
            @Override
            public boolean canChildScrollUp(@NonNull SwipeRefreshLayout swipeRefreshLayout, @Nullable View view) {
                return worksListController.getScollYDistance() > 0;
            }
        });

    }

    private void initWorksListController() {
        HashMap<String, Object> filter = new HashMap<>();
        filter.put("username", authModel.getCurrentLoginUser().getUsername());
        worksListController = WorksListController.newInstance(filter);
        loadRootFragment(R.id.works_list_layout, worksListController);
    }

    @OnClick(R.id.publish_image_text_works)
    public void onPublishImageTextWorksClicked() {
        getContainerActivity().start(new PublishImageTextWorksController());
    }

    @OnClick(R.id.publish_video_works)
    public void onPublishVideoWorksClicked() {
        getContainerActivity().start(new PublishVideoWorksController());
    }

//    @Override
    public void onSupportInvisible() {
//        super.onSupportInvisible();
        floatingMenu.close(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetWorksListSuccessEvent(GetWorksListSuccessEvent event) {
        refreshLayout.setRefreshing(false);
    }

}
