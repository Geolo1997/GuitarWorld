package pers.geolo.guitarworld.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.adapter.WorksViewAdapter;
import pers.geolo.guitarworld.base.BaseActivity;
import pers.geolo.guitarworld.dao.DAOService;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.WorksAPI;
import pers.geolo.guitarworld.network.callback.BaseCallback;

import java.util.ArrayList;
import java.util.List;

public class MyWorksActivity extends BaseActivity {

    private List<Works> myWorksList;
    private WorksViewAdapter worksViewAdapter;

    @BindView(R.id.rv_myWorks)
    RecyclerView rvMyWorks;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_works);
        ButterKnife.bind(this);

        // 设置刷新事件监听
        swipeRefreshLayout.setOnRefreshListener(() -> updateWorksList());

        // 设置RecyclerView管理器
        rvMyWorks.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,
                false));
        // 设置添加或删除item时的动画，这里使用默认动画
        rvMyWorks.setItemAnimator(new DefaultItemAnimator());

        myWorksList = new ArrayList<>();
        worksViewAdapter = new WorksViewAdapter(myWorksList);
        worksViewAdapter.setOnItemListener((view, position) -> {
            // 跳转到作品详情页
            Intent intent = new Intent(MyWorksActivity.this, WorksDetailActivity.class);
            intent.putExtra("id", myWorksList.get(position).getId());
            startActivity(intent);
        });
        // 设置适配器
        rvMyWorks.setAdapter(worksViewAdapter);

        updateWorksList();
    }

    public void updateWorksList() {
        String author = DAOService.getInstance().getCurrentLogInfo().getUsername();
        HttpService.getInstance().getAPI(WorksAPI.class)
                .getWorksList(author)
                .enqueue(new BaseCallback<List<Works>>() {
                    @Override
                    public void onSuccess(List<Works> responseData) {
                        myWorksList.clear();
                        myWorksList.addAll(responseData);
                        worksViewAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
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
