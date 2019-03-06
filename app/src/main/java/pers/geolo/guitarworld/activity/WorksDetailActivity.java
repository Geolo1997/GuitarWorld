package pers.geolo.guitarworld.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.adapter.CommentViewAdapter;
import pers.geolo.guitarworld.base.BaseActivity;
import pers.geolo.guitarworld.entity.Comment;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.WorksAPI;
import pers.geolo.guitarworld.network.callback.BaseCallback;
import pers.geolo.guitarworld.util.DateUtils;

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
        ButterKnife.bind(this);
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

        HttpService.getInstance().getAPI(WorksAPI.class)
                .getWorks(worksId)
                .enqueue(new BaseCallback<Works>() {
                    @Override
                    public void onSuccess(Works responseData) {
                        tvAuthor.setText(responseData.getAuthor());
                        tvCreateTime.setText(DateUtils.toString(responseData.getCreateTime()));
                        tvTitle.setText(responseData.getTitle());
                        tvContent.setText(responseData.getContent().toString());
                        comments.clear();
//                comments.addAll(data.getComments());
                        commentViewAdapter.notifyDataSetChanged();
//                swipeRefreshLayout.setRefreshing(false);
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
}
