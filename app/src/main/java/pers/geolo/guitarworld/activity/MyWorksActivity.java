package pers.geolo.guitarworld.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.adapter.WorksViewAdapter;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.network.BaseCallback;
import pers.geolo.guitarworld.network.HttpUtils;
import pers.geolo.guitarworld.service.UserService;
import pers.geolo.guitarworld.util.SingletonHolder;

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
        String author = SingletonHolder.getInstance(UserService.class).getUsername();
        HttpUtils.getWorksList(author, new BaseCallback<List<Works>>() {
            @Override
            public void onSuccess(List<Works> data) {
                myWorksList.clear();
                myWorksList.addAll(data);
                worksViewAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(String message) {

            }

            @Override
            public void onFailure() {
            }
        });
    }

}
