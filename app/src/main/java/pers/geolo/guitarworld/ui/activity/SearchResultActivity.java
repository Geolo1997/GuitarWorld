package pers.geolo.guitarworld.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.ui.base.BaseActivity;
import pers.geolo.guitarworld.entity.UserListType;
import pers.geolo.guitarworld.presenter.user.UserListPresenter;
import pers.geolo.guitarworld.ui.adapter.UserListAdapter;
import pers.geolo.guitarworld.util.RecyclerViewUtils;

public class SearchResultActivity extends BaseActivity {

    @BindView(R.id.rv)
    RecyclerView rv;

    private UserListPresenter userListPresenter;
    private UserListAdapter userListAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_search_result;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userListAdapter = new UserListAdapter(this);
        // 设置RecyclerView
        RecyclerViewUtils.setDefaultConfig(this, rv);
        rv.setAdapter(userListAdapter);
        // 获取Presenter
        userListPresenter = userListAdapter.getPresenter();
        userListPresenter.setFilter(UserListType.class.getSimpleName(), UserListType.ALL);
        userListPresenter.loadUserList();
    }
}
