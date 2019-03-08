package pers.geolo.guitarworld.activity;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.adapter.CommentListAdapter;
import pers.geolo.guitarworld.base.BaseActivity;
import pers.geolo.guitarworld.dao.DAOService;
import pers.geolo.guitarworld.entity.Comment;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.CommentAPI;
import pers.geolo.guitarworld.network.api.WorksAPI;
import pers.geolo.guitarworld.network.callback.BaseCallback;
import pers.geolo.guitarworld.util.DateUtils;
import pers.geolo.guitarworld.util.KeyBoardUtils;

public class WorksDetailActivity extends BaseActivity {


    private CommentListAdapter adapter;
    private int worksId;

    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.rv_comments)
    RecyclerView rvComments;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;

    @BindView(R.id.ll_add_comment)
    LinearLayout llAddComment;
    @BindView(R.id.et_comment)
    EditText etComment;

    @Override
    protected int getContentView() {
        return R.layout.activity_works_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 接收活动传来的作品id
        worksId = getIntent().getIntExtra("id", 0);

        // 设置RecyclerView管理器
        rvComments.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // 初始化适配器
        adapter = new CommentListAdapter(this);
        // 设置添加或删除item时的动画，这里使用默认动画
        rvComments.setItemAnimator(new DefaultItemAnimator());
        // 设置适配器
        rvComments.setAdapter(adapter);

        srlRefresh.setOnRefreshListener(() -> loading());

        loading();
    }


    private int loadingFlag;

    private void loading() {

        loadingFlag = 2;

        HttpService.getInstance().getAPI(WorksAPI.class)
                .getWorks(worksId).enqueue(new BaseCallback<Works>() {
            @Override
            public void onSuccess(Works responseData) {
                tvAuthor.setText(responseData.getAuthor());
                tvCreateTime.setText(DateUtils.toString(responseData.getCreateTime()));
                tvTitle.setText(responseData.getTitle());
                tvContent.setText(responseData.getContent().toString());

                loadingFlag--;
                if (loadingFlag == 0) {
                    srlRefresh.setRefreshing(false);
                }
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                showToast("网络错误！");
                srlRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure() {
                showToast("网络错误！");
                srlRefresh.setRefreshing(false);
            }
        });

        HttpService.getInstance().getAPI(CommentAPI.class)
                .listCommentOfWorks(worksId).enqueue(new BaseCallback<List<Comment>>() {
            @Override
            public void onSuccess(List<Comment> responseData) {
                adapter.setDataList(responseData);

                loadingFlag--;
                if (loadingFlag == 0) {
                    srlRefresh.setRefreshing(false);
                }
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                showToast("网络错误！");
                srlRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure() {
                showToast("网络错误！");
                srlRefresh.setRefreshing(false);
            }
        });
    }

    @OnClick(R.id.bt_add_comment)
    public void showAddCommentView() {
        llAddComment.setVisibility(View.VISIBLE);
        KeyBoardUtils.showKeyBoard(WorksDetailActivity.this, etComment);
    }

    @OnClick(R.id.bt_comment)
    public void addComment() {
        String author = DAOService.getInstance().getCurrentLogInfo().getUsername();
        String content = etComment.getText().toString();
        Comment comment = new Comment(worksId, author, new Date(), content);

        HttpService.getInstance().getAPI(CommentAPI.class)
                .addComment(comment).enqueue(new BaseCallback<Void>() {
            @Override
            public void onSuccess(Void responseData) {
                showToast("评论成功！");
                etComment.setText("");
                KeyBoardUtils.hideKeyBoard(WorksDetailActivity.this, etComment);
                llAddComment.setVisibility(View.GONE);
                loading();
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                showToast("评论失败");
            }

            @Override
            public void onFailure() {
                showToast("评论失败");
            }
        });
    }
}
