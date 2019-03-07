package pers.geolo.guitarworld.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.activity.PublishActivity;
import pers.geolo.guitarworld.adapter.WorksListAdapter;
import pers.geolo.guitarworld.base.BaseFragment;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.WorksAPI;
import pers.geolo.guitarworld.network.callback.BaseCallback;

public class DynamicFragment extends BaseFragment {

    WorksListAdapter adapter;

    @BindView(R.id.rv_works_list)
    RecyclerView rvWorksList;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;

    @Override
    protected int getContentView() {
        return R.layout.fragment_dynamic;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        srlRefresh.setOnRefreshListener(() -> {
            updateWorksList();
        });
        // 设置RecyclerView管理器
        rvWorksList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        // 设置添加或删除item时的动画，这里使用默认动画
        rvWorksList.setItemAnimator(new DefaultItemAnimator());
        adapter = new WorksListAdapter(getBaseActivity());
        rvWorksList.setAdapter(adapter);

        updateWorksList();

        return rootView;
    }

    private void updateWorksList() {
        HttpService.getInstance().getAPI(WorksAPI.class)
                .getAllWorks().enqueue(new BaseCallback<List<Works>>() {
            @Override
            public void onSuccess(List<Works> responseData) {
                adapter.setDataList(responseData);
                srlRefresh.setRefreshing(false);
            }

            @Override
            public void onError(int errorCode, String errorMessage) {

            }

            @Override
            public void onFailure() {

            }
        });
    }

    @OnClick(R.id.publish_works)
    public void onViewClicked() {
        getBaseActivity().startActivity(PublishActivity.class);
    }
}
