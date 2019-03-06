package pers.geolo.guitarworld.fragment;

import android.content.Intent;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.activity.WorksDetailActivity;
import pers.geolo.guitarworld.adapter.WorksViewAdapter;
import pers.geolo.guitarworld.base.BaseFragment;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.WorksAPI;
import pers.geolo.guitarworld.network.callback.BaseCallback;
import pers.geolo.util.SingletonHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * 作品列表碎片，接收参数String author...
 *
 * @author Geolo
 */
public class WorksListFragment extends BaseFragment {

    Unbinder unbinder;
    Unbinder unbinder1;
    // 作品列表
    private List<Works> worksList;
    // RecyclerView适配器
    private WorksViewAdapter adapter;
    // 保存传入参数
    private Bundle bundle;

    @BindView(R.id.liner_list_srl)
    SwipeRefreshLayout srlUpdate;
    @BindView(R.id.liner_list_rv)
    RecyclerView rvMyWorks;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_group_liner_list_view, container, false);
        unbinder1 = ButterKnife.bind(this, view);
        // 设置RecyclerView管理器
        rvMyWorks.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        // 初始化适配器
        worksList = new ArrayList<>();
        adapter = new WorksViewAdapter(worksList);
        adapter.setOnItemListener((view1, position) -> {
            // 跳转到作品详情页
            Intent intent = new Intent(getContext(), WorksDetailActivity.class);
            intent.putExtra("id", worksList.get(position).getId());
            startActivity(intent);
        });
        // 设置添加或删除item时的动画，这里使用默认动画
        rvMyWorks.setItemAnimator(new DefaultItemAnimator());
        // 设置适配器
        rvMyWorks.setAdapter(adapter);

        // 设置刷新事件监听
        srlUpdate.setOnRefreshListener(() -> updateWorksList(bundle.getString("author")));

        // 获取数据更新视图
        bundle = getArguments();
        if (bundle != null) {
            updateWorksList(bundle.getString("author"));
        }
        return view;
    }

    public void updateWorksList(String author) {
        SingletonHolder.getInstance(HttpService.class)
                .getAPI(WorksAPI.class)
                .getWorksList(author)
                .enqueue(new BaseCallback<List<Works>>() {
                    @Override
                    public void onSuccess(List<Works> responseData) {
                        worksList.clear();
                        worksList.addAll(responseData);
                        adapter.notifyDataSetChanged();
                        srlUpdate.setRefreshing(false);
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        showToast(errorMessage);
                    }

                    @Override
                    public void onFailure() {

                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
