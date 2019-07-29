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
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.delegate.base.BaseDelegate;
import pers.geolo.guitarworld.delegate.base.BeanFactory;
import pers.geolo.guitarworld.delegate.base.SwipeBackDelegate;
import pers.geolo.guitarworld.entity.Comment;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.model.AuthModel;
import pers.geolo.guitarworld.model.CommentModel;
import pers.geolo.guitarworld.model.WorksModel;
import pers.geolo.guitarworld.util.KeyBoardUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorksDetailDelegate extends SwipeBackDelegate {

    private static final String WORKS_ID = "WORKS_ID";

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    //    @BindView(R.id.srl_refresh)
//    SwipeRefreshLayout srlRefresh;
    @BindView(R.id.ll_add_comment)
    LinearLayout llAddComment;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.works_list_layout)
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
        return R.layout.works_detail;
    }

    @Override
    protected View getStatueBarTopView() {
        return titleBar;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initTitleBar();
        if (getArguments() != null) {
            worksId = getArguments().getInt(WORKS_ID);
            HashMap<String, Object> filter = new HashMap<>();
            filter.put("worksId", worksId);
            // 加载WorksContentDelegate
            loadRootFragment(R.id.works_content_layout, WorksContentDelegate.newInstance(worksId));
            // 加载CommentListDelegate
            loadRootFragment(R.id.works_list_layout, CommentListDelegate.newInstance(filter));
        }
    }

    private void initTitleBar() {
        titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                pop();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {

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
                llAddComment.setVisibility(View.GONE);
                KeyBoardUtils.hideKeyBoard(getContext(), etComment);
            }

            @Override
            public void onError(String message) {

            }
        });
    }
}
