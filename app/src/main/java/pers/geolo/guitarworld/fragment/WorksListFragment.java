package pers.geolo.guitarworld.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.adapter.WorksListAdapter;
import pers.geolo.guitarworld.base.BaseFragment;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.WorksApi;
import pers.geolo.guitarworld.network.callback.BaseCallback;
import pers.geolo.util.SingletonHolder;

/**
 * 作品列表碎片，接收参数String author...
 *
 * @author Geolo
 */
public class WorksListFragment extends BaseFragment {

    // RecyclerView适配器
    private WorksListAdapter adapter;
    // 保存传入参数
    private Bundle bundle;

    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;
    @BindView(R.id.rv_works_list)
    RecyclerView rvWorksList;

    @Override
    protected int getContentView() {
        return R.layout.fragment_works_list;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        // 获取数据更新视图
        bundle = getArguments();
        // 设置RecyclerView管理器
        rvWorksList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        // 设置添加或删除item时的动画，这里使用默认动画
        rvWorksList.setItemAnimator(new DefaultItemAnimator());
        // 初始化适配器
        adapter = new WorksListAdapter(getBaseActivity());
        // 设置适配器
        rvWorksList.setAdapter(adapter);

        // 设置刷新事件监听
        srlRefresh.setOnRefreshListener(() -> updateWorksList(bundle.getString("author")));

        updateWorksList(bundle.getString("author"));

        return view;
    }


    public void updateWorksList(String author) {
        SingletonHolder.getInstance(HttpService.class).getAPI(WorksApi.class)
                .getWorksList(author)
                .enqueue(new BaseCallback<List<Works>>() {
                    @Override
                    public void onSuccess(List<Works> responseData) {
                        adapter.setDataList(responseData);
                        srlRefresh.setRefreshing(false);
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        getBaseActivity().showToast(errorMessage);
                    }

                    @Override
                    public void onFailure() {

                    }
                });
    }
}
