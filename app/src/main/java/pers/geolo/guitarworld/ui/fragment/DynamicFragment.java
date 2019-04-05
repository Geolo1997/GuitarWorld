package pers.geolo.guitarworld.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.OnClick;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.presenter.works.WorksListPresenter;
import pers.geolo.guitarworld.ui.base.BaseFragment;
import pers.geolo.guitarworld.ui.activity.PublishActivity;
import pers.geolo.guitarworld.ui.adapter.WorksListAdapter;
import pers.geolo.guitarworld.ui.base.CustomContext;
import pers.geolo.guitarworld.util.RecyclerViewUtils;

public class DynamicFragment extends BaseFragment {

    @BindView(R.id.rv_works_list)
    RecyclerView rvWorksList;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;

    WorksListAdapter worksListAdapter;

    @Override
    protected int getContentView() {
        return R.layout.fragment_dynamic;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        // 设置刷新控件监听
        srlRefresh.setOnRefreshListener(() -> worksListAdapter.getWorksListPresenter().loadWorksList());
        // 设置适配器
        worksListAdapter = new WorksListAdapter(getBaseActivity());
        worksListAdapter.setRefreshView(this);
        // 设置RecyclerView默认配置
        RecyclerViewUtils.setDefaultConfig(getContext(), rvWorksList);
        rvWorksList.setAdapter(worksListAdapter);
        // 设置presenter
        WorksListPresenter worksListPresenter = worksListAdapter.getWorksListPresenter();
        worksListPresenter.setFilter("follower", CustomContext.getInstance().getLogInfo().getUsername());
        worksListPresenter.loadWorksList();
        return rootView;
    }

    @OnClick(R.id.publish_works)
    public void onViewClicked() {
        getBaseActivity().startActivity(PublishActivity.class);
    }

    @Override
    public void showRefreshing() {
        srlRefresh.setRefreshing(true);
    }

    @Override
    public void hideRefreshing() {
        srlRefresh.setRefreshing(false);
    }
}
