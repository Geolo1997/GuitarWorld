package pers.geolo.guitarworld.delegate.works;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseDelegate;
import pers.geolo.guitarworld.base.BeanFactory;
import pers.geolo.guitarworld.entity.Comment;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.model.AuthModel;
import pers.geolo.guitarworld.model.CommentModel;
import pers.geolo.guitarworld.model.WorksModel;
import pers.geolo.guitarworld.util.KeyBoardUtils;

public class WorksDetailDelegate extends BaseDelegate {

    private static final String WORKS_ID = "WORKS_ID";
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

    AuthModel authModel = BeanFactory.getBean(AuthModel.class);
    WorksModel worksModel = BeanFactory.getBean(WorksModel.class);

    private int worksId;

    public static WorksDetailDelegate newInstance(int worksId) {
        Bundle args = new Bundle();
        args.putInt(WORKS_ID, worksId);
        WorksDetailDelegate fragment = new WorksDetailDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object getLayout() {
        return R.layout.delegate_works_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        if (getArguments() != null) {
            worksId = getArguments().getInt(WORKS_ID);
            HashMap<String, Object> filter = new HashMap<>();
            filter.put("worksId", worksId);
            initWorks();
            // 加载CommentListDelegate
            loadRootFragment(R.id.ll_fragment, CommentListDelegate.newInstance(filter));
        }
    }

    private void initWorks() {
        Map<String, Object> filter = new HashMap<>();
        filter.put("worksId", worksId);
        worksModel.getWorks(filter, new DataListener<List<Works>>() {
            @Override
            public void onReturn(List<Works> worksList) {
                Works works = worksList.get(0);
                tvAuthor.setText(works.getAuthor());
                tvCreateTime.setText(works.getCreateTime().toString());
                tvTitle.setText(works.getTitle());
                tvContent.setText(works.getContent());
            }

            @Override
            public void onError(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.bt_add_comment)
    public void showAddCommentView() {
        llAddComment.setVisibility(View.VISIBLE);
        KeyBoardUtils.showKeyBoard(getContext(), etComment);
    }

    @OnClick(R.id.bt_comment)
    public void addComment() {
        String author = authModel.getCurrentLoginUser().getUsername();
        Comment comment = new Comment(worksId, author, new Date(), etComment.getText().toString());
        CommentModel.addComment(comment, new DataListener<Void>() {
            @Override
            public void onReturn(Void aVoid) {
                Toast.makeText(getContext(), "评论成功", Toast.LENGTH_SHORT).show();
                tvContent.setText("");
                llAddComment.setVisibility(View.GONE);
                KeyBoardUtils.hideKeyBoard(getContext(), etComment);
            }

            @Override
            public void onError(String message) {

            }
        });
    }
}
