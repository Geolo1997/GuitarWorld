package pers.geolo.guitarworld.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.OnClick;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseFragment;
import pers.geolo.guitarworld.presenter.WorksListPresenter;
import pers.geolo.guitarworld.ui.activity.PublishActivity;
import pers.geolo.guitarworld.ui.adapter.WorksListAdapter;

public class DynamicFragment extends BaseFragment {

    WorksListAdapter adapter;

    @BindView(R.id.rv_works_list)
    RecyclerView rvWorksList;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;

    private WorksListPresenter worksListPresenter = new WorksListPresenter();

    @Override
    protected int getContentView() {
        return R.layout.fragment_dynamic;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        srlRefresh.setOnRefreshListener(() -> {
            worksListPresenter.loadingWorksList();
        });
        // 设置RecyclerView管理器
        rvWorksList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        // 设置添加或删除item时的动画，这里使用默认动画
        rvWorksList.setItemAnimator(new DefaultItemAnimator());
        adapter = new WorksListAdapter(getBaseActivity());
        rvWorksList.setAdapter(adapter);

        worksListPresenter.bind(adapter);

        worksListPresenter.loadingWorksList();

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
