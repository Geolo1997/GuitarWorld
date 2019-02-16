package pers.geolo.guitarworld.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.adapter.CommentViewAdapter;
import pers.geolo.guitarworld.adapter.WorksViewAdapter;
import pers.geolo.guitarworld.model.Comment;
import pers.geolo.guitarworld.model.Works;
import pers.geolo.guitarworld.network.BaseCallback;
import pers.geolo.guitarworld.network.HttpUtils;

import java.util.ArrayList;
import java.util.List;

public class WorksDetailActivity extends BaseActivity {

    int worksId;

    List<Comment> comments;
    private CommentViewAdapter commentViewAdapter;

    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @BindView(R.id.tv_createTime)
    TextView tvCreateTime;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.rv_comments)
    RecyclerView rvComments;

//    @BindView(R.id.srl_refresh)
//    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_works_detail);
        // 接收活动传来的作品id
        worksId = getIntent().getIntExtra("id", 0);

        loadingWorks();

//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                loadingWorks();
//            }
//        });

        comments = new ArrayList<>();
        // 设置RecyclerView管理器
        rvComments.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // 初始化适配器
        commentViewAdapter = new CommentViewAdapter(comments);
        // 设置添加或删除item时的动画，这里使用默认动画
        rvComments.setItemAnimator(new DefaultItemAnimator());
        // 设置适配器
        rvComments.setAdapter(commentViewAdapter);
    }

    private void loadingWorks() {
        HttpUtils.getWorks(worksId, new BaseCallback<Works>() {
            @Override
            public void onSuccess(Works data) {
                tvAuthor.setText(data.getAnthor());
//                tvCreateTime.setText(data.getCreateTime().toString());
                tvTitle.setText(data.getTitle());
                tvContent.setText(data.getContent().toString());
                comments.clear();
                comments.addAll(data.getComments());
                commentViewAdapter.notifyDataSetChanged();
//                swipeRefreshLayout.setRefreshing(false);
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
