package pers.geolo.guitarworld.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.ui.adapter.UsernameListAdapter;
import pers.geolo.guitarworld.base.BaseFragment;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.UserRelationApi;
import pers.geolo.guitarworld.network.callback.BaseCallback;

import java.util.List;

public class FollowerFragment extends BaseFragment {

    Bundle bundle;

    UsernameListAdapter adapter;

    @BindView(R.id.rv_follower)
    RecyclerView rvFollower;

    @Override
    protected int getContentView() {
        return R.layout.fragment_follower;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        // 设置RecyclerView管理器
        rvFollower.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        // 设置添加或删除item时的动画，这里使用默认动画
        rvFollower.setItemAnimator(new DefaultItemAnimator());
        adapter = new UsernameListAdapter(getBaseActivity());
        rvFollower.setAdapter(adapter);

        bundle = getArguments();

        updateUsernameList(bundle.getString("username"));

        return rootView;
    }

    private void updateUsernameList(String username) {
        HttpService.getInstance().getAPI(UserRelationApi.class)
                .getFollower(username).enqueue(new BaseCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> responseData) {
//                adapter.setDataList(responseData);
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
