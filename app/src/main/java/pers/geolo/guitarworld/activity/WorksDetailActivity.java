package pers.geolo.guitarworld.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import butterknife.BindView;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.adapter.CommentListAdapter;
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

    private CommentListAdapter adapter;

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
    protected int getContentView() {
        return R.layout.activity_works_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 接收活动传来的作品id
        worksId = getIntent().getIntExtra("id", 0);

        loadingWorks();

//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                loadingWorks();
//            }
//        });

        // 设置RecyclerView管理器
        rvComments.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // 初始化适配器
        adapter = new CommentListAdapter(this);
        // 设置添加或删除item时的动画，这里使用默认动画
        rvComments.setItemAnimator(new DefaultItemAnimator());
        // 设置适配器
        rvComments.setAdapter(adapter);
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
//                        adapter.getDataList().clear();
//                        List<Comment> commentList = responseData.getComments();
//                        adapter.setDataList(commentList);
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
