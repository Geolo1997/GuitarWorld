package pers.geolo.guitarworld.helper;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.adapter.BaseRecyclerViewAdapter;
import pers.geolo.guitarworld.network.callback.BaseCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <T> 列表视图适配器中的元素类型
 * @author Geolo
 */
public abstract class LinerListViewHelper<T> {

    // 被include进具体布局的view_group_liner_list_view.xml的视图
    private View linerListView;
    // 刷新控件
    private SwipeRefreshLayout swipeRefreshLayout;
    // recyclerView控件
    private RecyclerView recyclerView;
    // recycler的适配器
    private BaseRecyclerViewAdapter recyclerViewAdapter;
    // 数据列表
    private List<T> list;
    // 回调接口
//    private BaseCallback<List<T>> callback = new BaseCallback<List<T>>() {
//        @Override
//        public void onSuccess(List<T> data) {
//            list.clear();
//            list.addAll(data);
//            recyclerViewAdapter.notifyDataSetChanged();
//            swipeRefreshLayout.setRefreshing(false);
//        }
//
//        @Override
//        public void onError(String message) {
//
//        }
//
//        @Override
//        public void onFailure() {
//        }
//    };

    public LinerListViewHelper(View linerListView) {
        this.linerListView = linerListView;

        // 获取swipeRefreshLayout
        swipeRefreshLayout = linerListView.findViewById(R.id.liner_list_srl);
        // 设置刷新事件监听
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                onUpdate(callback);
            }
        });

        // 获取recyclerView
        recyclerView = linerListView.findViewById(R.id.liner_list_rv);
        // 设置RecyclerView管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(linerListView.getContext(),
                LinearLayoutManager.VERTICAL, false));
        // 设置添加或删除item时的动画，这里使用默认动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    public void setAdapter(BaseRecyclerViewAdapter adapter) {
        recyclerViewAdapter = adapter;
        // 设置适配器
        recyclerView.setAdapter(recyclerViewAdapter);
        list = new ArrayList<>();
//        recyclerViewAdapter.setOnItemListener(new BaseRecyclerViewAdapter.OnItemListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                LinerListViewHelper.this.onItemClick(view, position);
//            }
//        });
    }

    public <A extends BaseRecyclerViewAdapter> void setAdapter(Class<A> adapterClass) {
        list = new ArrayList<>();
//        try {
//            setAdapter(adapterClass.newInstance(list));
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        }
    }

    public void update() {
//        onUpdate(callback);
    }

    public List<T> getList() {
        return list;
    }

    protected abstract void onItemClick(View view, int position);

    protected abstract void onUpdate(BaseCallback<List<T>> callback);

}
