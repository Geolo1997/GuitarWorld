package pers.geolo.guitarworld.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.ui.base.BaseActivity;
import pers.geolo.guitarworld.ui.base.CustomContext;
import pers.geolo.guitarworld.presenter.works.WorksListPresenter;
import pers.geolo.guitarworld.ui.adapter.WorksListAdapter;
import pers.geolo.guitarworld.util.RecyclerViewUtils;

public class MyWorksActivity extends BaseActivity {

    @BindView(R.id.rv_my_works)
    RecyclerView rvMyWorks;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;

    private WorksListAdapter worksListAdapter;
    private WorksListPresenter worksListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置刷新事件监听
        srlRefresh.setOnRefreshListener(() -> worksListPresenter.loadWorksList());
        // 设置RecyclerView
        RecyclerViewUtils.setDefaultConfig(this, rvMyWorks);
        worksListAdapter = new WorksListAdapter(this);
        rvMyWorks.setAdapter(worksListAdapter);
        // 设置worksListPresenter
        worksListPresenter = worksListAdapter.getWorksListPresenter();
        // 设置Filter
        String currentUsername = CustomContext.getInstance().getLogInfo().getUsername();
        worksListPresenter.setFilter("author", currentUsername);
        // 初始化加载WorksList
        worksListPresenter.loadWorksList();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_my_works;
    }
}
