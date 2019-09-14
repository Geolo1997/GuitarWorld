package pers.geolo.guitarworld.controller.index;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import butterknife.BindView;
import org.microview.core.ControllerManager;
import org.microview.core.ViewParams;
import org.microview.support.MicroviewRVAdapter;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.BaseController;
import pers.geolo.guitarworld.controller.base.BeanFactory;
import pers.geolo.guitarworld.controller.works.PublishWorksMenuController;
import pers.geolo.guitarworld.controller.works.WorksItemController;
import pers.geolo.guitarworld.entity.DataCallback;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.model.WorksModel;
import pers.geolo.guitarworld.network.param.WorksParams;
import pers.geolo.guitarworld.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DynamicController extends BaseController {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.works_list_view)
    RecyclerView worksListView;
    @BindView(R.id.publish_works_menu_container)
    FrameLayout publishWorksMenuContainer;

    WorksModel worksModel = BeanFactory.getBean(WorksModel.class);

    private MicroviewRVAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.dynamic;
    }

    @Override
    public void initView(ViewParams viewParams) {
        ControllerManager.load(publishWorksMenuContainer, new PublishWorksMenuController());
        worksListView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new MicroviewRVAdapter(WorksItemController.class, new ArrayList<>());
        worksListView.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadWorksList();
            }
        });
        loadWorksList();
        refreshLayout.setRefreshing(true);
    }

    private void loadWorksList() {
        WorksParams params = new WorksParams();
        worksModel.getWorks(params, new DataCallback<List<Works>>() {
            @Override
            public void onReturn(List<Works> works) {
                List<ViewParams> viewParams = works.stream()
                        .map(new Function<Works, ViewParams>() {
                            @Override
                            public ViewParams apply(Works works) {
                                return new ViewParams("works", works);
                            }
                        })
                        .collect(Collectors.toList());
                adapter.updateDataList(viewParams);
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(String message) {
                ToastUtils.showErrorToast(getActivity(), message);
            }
        });
    }
}
