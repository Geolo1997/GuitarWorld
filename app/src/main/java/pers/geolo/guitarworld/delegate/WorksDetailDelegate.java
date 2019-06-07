package pers.geolo.guitarworld.delegate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.entity.Comment;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.fragmentationtest.delegate.BaseDelegate;
import pers.geolo.guitarworld.model.AuthModel;
import pers.geolo.guitarworld.network.HttpUtils;
import pers.geolo.guitarworld.network.callback.Response;
import pers.geolo.guitarworld.util.KeyBoardUtils;

public class WorksDetailDelegate extends BaseDelegate {

    private static final String WORKS_ID = "WORKS_ID"
            ;
    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
//    @BindView(R.id.srl_refresh)
//    SwipeRefreshLayout srlRefresh;
    @BindView(R.id.ll_add_comment)
    LinearLayout llAddComment;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.ll_fragment)
    LinearLayout llFragment;

    public static WorksDetailDelegate newInstance(int worksId) {
        Bundle args = new Bundle();
        args.putInt(WORKS_ID, worksId);
        WorksDetailDelegate fragment = new WorksDetailDelegate();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public Object getLayout() {
        return R.layout.activity_works_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        loadRootFragment(R.id.ll_fragment, CommentListDelegate);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 接收活动传来的作品id
        worksId = getStartParameters().getInt("worksId");
//        srlRefresh.setOnRefreshListener(() -> {
//            loadWorks();
//            srlRefresh.setRefreshing(false);
//        });
        loadWorks();

        Bundle bundle = new Bundle();
        bundle.putInt("worksId", worksId);
        setFragment(R.id.ll_fragment, new CommentListDelegate(), bundle);
    }

    private void loadWorks() {
        Map<String, Object> filter = new HashMap<>();
        filter.put("id", worksId);
        HttpUtils.getWorks(filter, new Response<List<Works>>(this) {
            @Override
            public void onResponse(List<Works> responseData) {
                Works works = responseData.get(0);
                tvAuthor.setText(works.getAuthor());
                tvCreateTime.setText(works.getCreateTime().toString());
                tvTitle.setText(works.getTitle());
                tvContent.setText(works.getContent());
            }
        });
    }

    @OnClick(R.id.bt_add_comment)
    public void showAddCommentView() {
        llAddComment.setVisibility(View.VISIBLE);
        KeyBoardUtils.showKeyBoard(WorksDetailDelegate.this, etComment);
    }

    @OnClick(R.id.bt_comment)
    public void addComment() {
        String author = AuthModel.getCurrentLoginUser().getUsername();
        Comment comment = new Comment(worksId, author, new Date(), etComment.getText().toString());
        HttpUtils.addComment(comment, new Response<Void>(this) {
            @Override
            public void onResponse(Void responseData) {
                showToast("评论成功!");
                tvContent.setText("");
                llAddComment.setVisibility(View.GONE);
                KeyBoardUtils.hideKeyBoard(WorksDetailDelegate.this, etComment);
            }
        });
    }
}
