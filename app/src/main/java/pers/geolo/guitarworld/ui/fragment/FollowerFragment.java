package pers.geolo.guitarworld.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.ui.base.BaseFragment;
import pers.geolo.guitarworld.entity.UserListType;
import pers.geolo.guitarworld.presenter.user.UserListPresenter;
import pers.geolo.guitarworld.ui.adapter.UserListAdapter;
import pers.geolo.guitarworld.util.ModuleMessage;
import pers.geolo.guitarworld.util.RecyclerViewUtils;

public class FollowerFragment extends BaseFragment {

    @BindView(R.id.rv_follower)
    RecyclerView rvFollower;

    @Override
    protected int getContentView() {
        return R.layout.fragment_follower;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        String currentUsername = null;
        Bundle bundle = getArguments();
        if (bundle != null) {
            currentUsername = bundle.getString(ModuleMessage.CURRENT_USERNAME);
        }
        // 设置适配器
        UserListAdapter userListAdapter = new UserListAdapter(getBaseActivity());
        userListAdapter.setRefreshView(this);
        // 设置RecyclerView
        RecyclerViewUtils.setDefaultConfig(getContext(), rvFollower);
        rvFollower.setAdapter(userListAdapter);
        // 获取presenter
        UserListPresenter userListPresenter = userListAdapter.getPresenter();
        // 设置过滤器
        userListPresenter.setFilter(UserListType.class.getSimpleName(), UserListType.FOLLOWER);
        userListPresenter.setFilter("currentUsername", currentUsername);
        // 加载用户列表
        userListPresenter.loadUserList();
        return rootView;
    }
}
