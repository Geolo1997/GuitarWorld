package pers.geolo.guitarworld.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import butterknife.BindView;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.adapter.WorksViewAdapter;
import pers.geolo.guitarworld.base.BaseActivity;
import pers.geolo.guitarworld.model.Works;
import pers.geolo.guitarworld.network.BaseCallback;
import pers.geolo.guitarworld.network.HttpUtils;
import pers.geolo.guitarworld.util.LoginManager;
import pers.geolo.guitarworld.util.SingletonHolder;

import java.util.ArrayList;
import java.util.List;

public class MyWorksActivity extends BaseActivity {

    private List<Works> worksList;
    private WorksViewAdapter worksViewAdapter;

    @BindView(R.id.rv_myWorks)
    RecyclerView rvMyWorks;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_works);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateWorksList();
            }
        });

        // 设置RecyclerView管理器
        rvMyWorks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // 初始化适配器
        worksList = new ArrayList<>();
        worksViewAdapter = new WorksViewAdapter(worksList);
        worksViewAdapter.setOnItemListener(new WorksViewAdapter.OnItemListener() {
            @Override
            public void onItemClickListener(View view, int postion) {
                //TODO 跳转详情页
                toast("你点击了第" + postion + "条信息");
            }
        });
        // 设置添加或删除item时的动画，这里使用默认动画
        rvMyWorks.setItemAnimator(new DefaultItemAnimator());
        // 设置适配器
        rvMyWorks.setAdapter(worksViewAdapter);

        updateWorksList();
    }

    public void updateWorksList() {
        String anthor = SingletonHolder.getInstance(LoginManager.class).getLoginUser().getUsername();
        HttpUtils.getWorksList(anthor, new BaseCallback<List<Works>>() {
            @Override
            public void onSuccess(List<Works> data) {
                worksList.clear();
                worksList.addAll(data);
                worksViewAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(String message) {
                Log.d(TAG, message);
                toast(message);
            }

            @Override
            public void onFailure() {
                Log.d(TAG, "网络错误！");
            }
        });
    }

}
