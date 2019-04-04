package pers.geolo.guitarworld.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import java.util.Date;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.ui.base.BaseActivity;
import pers.geolo.guitarworld.presenter.comment.AddCommentPresenter;
import pers.geolo.guitarworld.presenter.comment.CommentListPresenter;
import pers.geolo.guitarworld.presenter.works.WorksDetailPresenter;
import pers.geolo.guitarworld.ui.adapter.CommentListAdapter;
import pers.geolo.guitarworld.util.DateUtils;
import pers.geolo.guitarworld.util.KeyBoardUtils;
import pers.geolo.guitarworld.util.ModuleMessage;
import pers.geolo.guitarworld.util.RecyclerViewUtils;
import pers.geolo.guitarworld.view.AddCommentView;
import pers.geolo.guitarworld.view.WorksDetailView;

public class WorksDetailActivity extends BaseActivity implements WorksDetailView, AddCommentView {

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

    private CommentListAdapter commentListAdapter;
    private WorksDetailPresenter worksDetailPresenter = new WorksDetailPresenter();
    private AddCommentPresenter addCommentPresenter = new AddCommentPresenter();
    private CommentListPresenter commentListPresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_works_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 接收活动传来的作品id
        int worksId = getIntent().getIntExtra(ModuleMessage.WORKS_ID, 0);
        // 绑定Presenter
        worksDetailPresenter.bind(this);
        worksDetailPresenter.setWorksId(worksId);
        addCommentPresenter.bind(this);
        addCommentPresenter.setWorksId(worksId);
        // 设置适配器
        commentListAdapter = new CommentListAdapter(this);
        commentListPresenter = commentListAdapter.getCommentListPresenter();
        commentListPresenter.setFilter("worksId", worksId);
        //设置RecyclerView
        RecyclerViewUtils.setDefaultConfig(this, rvComments);
        rvComments.setAdapter(commentListAdapter);
        // 设置刷新事件
        srlRefresh.setOnRefreshListener(() -> worksDetailPresenter.loadWorksDetail());
        // 初始化加载
        worksDetailPresenter.loadWorksDetail();
        commentListPresenter.loadCommentList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        worksDetailPresenter.unBind();
        addCommentPresenter.unBind();
    }

    @OnClick(R.id.bt_add_comment)
    public void showAddCommentView() {
        llAddComment.setVisibility(View.VISIBLE);
        KeyBoardUtils.showKeyBoard(WorksDetailActivity.this, etComment);
    }

    @OnClick(R.id.bt_comment)
    public void addComment() {
        addCommentPresenter.addComment();
    }

    @Override
    public void setAuthor(String author) {
        tvAuthor.setText(author);
    }

    @Override
    public void setCreateTime(Date createTime) {
        tvCreateTime.setText(DateUtils.toString(createTime));
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
    public String getComment() {
        return etComment.getText().toString().trim();
    }

    @Override
    public void setCommentText(String s) {
        etComment.setText(s);
    }

    @Override
    public void toCommentListView() {
        KeyBoardUtils.hideKeyBoard(this, etComment);
        llAddComment.setVisibility(View.GONE);
    }
}
