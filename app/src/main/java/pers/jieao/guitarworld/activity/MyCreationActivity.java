package pers.jieao.guitarworld.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.IOException;
import java.sql.Time;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;
import okhttp3.Call;
import okhttp3.Response;
import pers.jieao.guitarworld.LoginManager;
import pers.jieao.guitarworld.R;
import pers.jieao.guitarworld.adapter.CreationAdapter;
import pers.jieao.guitarworld.base.BaseActivity;
import pers.jieao.guitarworld.model.Creation;
import pers.jieao.guitarworld.network.Network;
import pers.jieao.guitarworld.utils.JsonUtil;

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
        Network.getCreationList(LoginManager.getInstance().getUserId(), new okhttp3.Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //String responseString = response.body().string();
                Log.d("Creation", "回调成功！");
                //mCreationList = JsonUtil.getCreationList(responseString);
                //test
                setCreationList();
                //end test
                //更新UI
                mCreationAdapter.notifyDataSetChanged();
                /*
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });*/

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
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

    @Override
    protected int getContentView() {
        return R.layout.activity_my_creation;
    }
}
