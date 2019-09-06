package pers.geolo.guitarworld.delegate.index;

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
import pers.geolo.guitarworld.delegate.base.BaseDelegate;
import pers.geolo.guitarworld.delegate.base.BeanFactory;
import pers.geolo.guitarworld.delegate.works.PublishImageTextWorksDelegate;
import pers.geolo.guitarworld.delegate.works.PublishVideoWorksDelegate;
import pers.geolo.guitarworld.delegate.works.WorksListDelegate;
import pers.geolo.guitarworld.entity.GetWorksListSuccessEvent;
import pers.geolo.guitarworld.model.AuthModel;

import java.util.HashMap;

public class DynamicDelegate extends BaseDelegate {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.works_list_layout)
    LinearLayout worksListLayout;
    @BindView(R.id.floating_menu)
    FloatingActionMenu floatingMenu;

    AuthModel authModel = BeanFactory.getBean(AuthModel.class);
    WorksListDelegate worksListDelegate;

    @Override
    public Object getLayoutView() {
        return R.layout.dynamic;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
//        loadRootFragment(R.id.add_layout, new PublishWorksMenuDelegate());
        // 初始化刷新控件
        initRefreshLayout();
        initWorksListDelegate();
        refreshLayout.setRefreshing(true);
    }

    private void initRefreshLayout() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                worksListDelegate.loadWorksList();
            }
        });
        // 修复嵌套滑动冲突
        refreshLayout.setOnChildScrollUpCallback(new SwipeRefreshLayout.OnChildScrollUpCallback() {
            @Override
            public boolean canChildScrollUp(@NonNull SwipeRefreshLayout swipeRefreshLayout, @Nullable View view) {
                return worksListDelegate.getScollYDistance() > 0;
            }
        });

    }

    private void initWorksListDelegate() {
        HashMap<String, Object> filter = new HashMap<>();
        filter.put("username", authModel.getCurrentLoginUser().getUsername());
        worksListDelegate = WorksListDelegate.newInstance(filter);
        loadRootFragment(R.id.works_list_layout, worksListDelegate);
    }

    @OnClick(R.id.publish_image_text_works)
    public void onPublishImageTextWorksClicked() {
        getContainerActivity().start(new PublishImageTextWorksDelegate());
    }

    @OnClick(R.id.publish_video_works)
    public void onPublishVideoWorksClicked() {
        getContainerActivity().start(new PublishVideoWorksDelegate());
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
