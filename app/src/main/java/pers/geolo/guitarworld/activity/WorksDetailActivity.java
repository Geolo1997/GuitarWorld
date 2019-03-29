package pers.geolo.guitarworld.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import java.util.Date;
import java.util.List;

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
import pers.geolo.guitarworld.presenter.CommentPresenter;
import pers.geolo.guitarworld.presenter.WorksPresenter;
import pers.geolo.guitarworld.util.DateUtils;
import pers.geolo.guitarworld.util.KeyBoardUtils;
import pers.geolo.guitarworld.view.CommentView;
import pers.geolo.guitarworld.view.WorsDetailView;

public class WorksDetailActivity extends BaseActivity implements WorsDetailView, CommentView {


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
    private CommentListAdapter adapter;
    private int worksId;
    private int loadingFlag;

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

    private void loading() {
        loadingFlag = 2;
        WorksPresenter.loadingWorksDetail(this);
        CommentPresenter.loadingComment(this);
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

    @Override
    public void setDataList(List<Comment> responseData) {
        adapter.setDataList(responseData);
    }

    @Override
    public void loadingCommentOnSuccess() {
        loadingFlag--;
        if (loadingFlag == 0) {
            srlRefresh.setRefreshing(false);
        }
    }

    @Override
    public Works getWorks() {
        return null;
    }

    @Override
    public void setAuthor(String author) {
        tvAuthor.setText(author);
    }

    @Override
    public void setCreateTime(Date createTime) {
        tvCreateTime.setText(createTime.toString());
    }

    @Override
    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public void setContent(Object content) {
        tvContent.setText(content.toString());
    }

    @Override
    public void loadingWorksDetailOnSuccess() {
        loadingFlag--;
        if (loadingFlag == 0) {
            srlRefresh.setRefreshing(false);
        }
    }

    @Override
    public int getWorksId() {
        return worksId;
    }
}
