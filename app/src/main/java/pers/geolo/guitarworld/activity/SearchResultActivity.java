package pers.geolo.guitarworld.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.adapter.UsernameListAdapter;
import pers.geolo.guitarworld.base.BaseActivity;
import pers.geolo.guitarworld.entity.User;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.UserAPI;
import pers.geolo.guitarworld.network.callback.BaseCallback;

import java.util.List;

public class SearchResultActivity extends BaseActivity {

    private UsernameListAdapter adapter;

    @BindView(R.id.rv)
    RecyclerView rv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 设置RecyclerView管理器
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,
                false));
        // 设置添加或删除item时的动画，这里使用默认动画
        rv.setItemAnimator(new DefaultItemAnimator());
        adapter = new UsernameListAdapter(this);
        rv.setAdapter(adapter);

        update();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_search_result;
    }

    private void update() {

        HttpService.getInstance().getAPI(UserAPI.class)
                .getAllUsers().enqueue(new BaseCallback<List<User>>() {
            @Override
            public void onSuccess(List<User> responseData) {
                adapter.getDataList().clear();
                responseData.forEach(user -> adapter.getDataList().add(user.getUsername()));
                adapter.notifyDataSetChanged();
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
