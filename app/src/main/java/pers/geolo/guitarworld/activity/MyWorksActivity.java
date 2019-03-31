package pers.geolo.guitarworld.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.adapter.WorksListAdapter;
import pers.geolo.guitarworld.base.BaseActivity;
import pers.geolo.guitarworld.dao.DAOService;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.WorksApi;
import pers.geolo.guitarworld.network.callback.BaseCallback;


import java.util.List;

public class MyWorksActivity extends BaseActivity {

    private WorksListAdapter adapter;

    @BindView(R.id.rv_my_works)
    RecyclerView rvMyWorks;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置刷新事件监听
        srlRefresh.setOnRefreshListener(() -> updateWorksList());

        // 设置RecyclerView管理器
        rvMyWorks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // 设置添加或删除item时的动画，这里使用默认动画
        rvMyWorks.setItemAnimator(new DefaultItemAnimator());
        adapter = new WorksListAdapter(this);
        rvMyWorks.setAdapter(adapter);

        updateWorksList();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_my_works;
    }

    public void updateWorksList() {
        String author = DAOService.getInstance().getCurrentLogInfo().getUsername();
        HttpService.getInstance().getAPI(WorksApi.class)
                .getWorksList(author).enqueue(new BaseCallback<List<Works>>() {
            @Override
            public void onSuccess(List<Works> responseData) {
//                adapter.setDataList(responseData);
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


}
