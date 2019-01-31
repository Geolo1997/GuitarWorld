package pers.geolo.guitarworld.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.adapter.CreationAdapter;
import pers.geolo.guitarworld.base.BaseActivity;
import pers.geolo.guitarworld.model.Creation;

public class MyCreationActivity extends BaseActivity {

    List<Creation> mCreationList;
    CreationAdapter mCreationAdapter;

    @BindView(R.id.rv_mycreation)
    RecyclerView mRecyclerView;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCreationList = new LinkedList<>();
        setCreationList();
        setRecyclerAdapter();
        updateCreationList();
        setSwipeResfreshLayout();
    }

    private void setSwipeResfreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateCreationList();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void setRecyclerAdapter() {
        mCreationAdapter = new CreationAdapter(mCreationList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mCreationAdapter);
    }

    private void updateCreationList() {

    }

    static int times;
    static {
        times = 0;
    }
    private void setCreationList() {
        times++;
        mCreationList.clear();
        for (int i = 0; i < 50; i++) {
            Creation creation = new Creation();
            creation.setTitle("title");
            creation.setContent("context.............................\n......\n....\n.....");
            creation.setAuthorId("" + times +"author");
            creation.setCreateTime(new Date());
            mCreationList.add(creation);
        }
    }
}
