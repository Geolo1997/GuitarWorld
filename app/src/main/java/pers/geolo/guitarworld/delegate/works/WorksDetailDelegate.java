package pers.geolo.guitarworld.delegate.works;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.delegate.base.BeanFactory;
import pers.geolo.guitarworld.delegate.base.SwipeBackDelegate;
import pers.geolo.guitarworld.entity.Comment;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.event.Event;
import pers.geolo.guitarworld.model.AuthModel;
import pers.geolo.guitarworld.model.CommentModel;
import pers.geolo.guitarworld.model.WorksModel;
import pers.geolo.guitarworld.util.ToastUtils;

import java.util.Date;
import java.util.HashMap;

public class WorksDetailDelegate extends SwipeBackDelegate {

    private static final String WORKS_ID = "WORKS_ID";

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.comment_count_text)
    TextView commentCountText;
    @BindView(R.id.scroll_view)
    NestedScrollView scrollView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    AuthModel authModel = BeanFactory.getBean(AuthModel.class);
    WorksModel worksModel = BeanFactory.getBean(WorksModel.class);

    WorksContentDelegate worksContentDelegate;
    CommentListDelegate commentListDelegate;
    WorksOptionDelegate worksOptionDelegate;

    private int worksId;

    private int currentScrollY;
    private int lastScrollY;

    public static WorksDetailDelegate newInstance(int worksId) {
        Bundle args = new Bundle();
        args.putInt(WORKS_ID, worksId);
        WorksDetailDelegate fragment = new WorksDetailDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object getLayoutView() {
        return R.layout.works_detail;
    }

    @Override
    protected View getStatueBarTopView() {
        return titleBar;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initTitleBar();
//        initDelegateCreateStatus();
        initScrollListener();
        if (getArguments() != null) {
            worksId = getArguments().getInt(WORKS_ID);
            HashMap<String, Object> filter = new HashMap<>();
            filter.put("worksId", worksId);
            // 加载WorksContentDelegate
            worksContentDelegate = WorksContentDelegate.newInstance(worksId);
            loadRootFragment(R.id.works_content_layout, worksContentDelegate);
            // 加载CommentListDelegate
            commentListDelegate = CommentListDelegate.newInstance(filter);
            loadRootFragment(R.id.comment_list_layout, commentListDelegate);
            //
            worksOptionDelegate = WorksOptionDelegate.newInstance(worksId);
            loadRootFragment(R.id.works_option_layout, worksOptionDelegate);

            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    initLayout();
                    refreshLayout.setRefreshing(false);
                }
            });
        }
    }

    private void initScrollListener() {
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                WorksDetailDelegate.this.currentScrollY = scrollY;
//                if (scrollY == 0) {
//                    scrollView.setScrollY(lastScrollY);
//                }
            }
        });
    }

    public void initLayout() {
        worksContentDelegate.onBindView(null, worksContentDelegate.getRootView());
        commentListDelegate.onBindView(null, commentListDelegate.getRootView());
        worksOptionDelegate.onBindView(null, worksOptionDelegate.getRootView());
    }

//    private boolean worksContentDelegateCreated;
//    private boolean commentListDelegateCreated;
//    private boolean worksOptionDelegateCreated;
//
//    public void initDelegateCreateStatus() {
//        worksContentDelegateCreated = false;
//        commentListDelegateCreated = false;
//        worksOptionDelegateCreated = false;
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onSubDelegateCreateFinish(Event event) {
//        String delegateName = (String) event.get(Event.Const.CREATE_DELEGATE_FINISH.name());
//        if (worksContentDelegate.getClass().getName().equals(delegateName)) {
//            worksContentDelegateCreated = true;
//        }
//        if (commentListDelegate.getClass().getName().equals(delegateName)) {
//            commentListDelegateCreated = true;
//        }
//        if (worksContentDelegate.getClass().getName().equals(delegateName)) {
//            worksContentDelegateCreated = true;
//        }
//        if (worksContentDelegateCreated && commentListDelegateCreated && worksOptionDelegateCreated) {
//            scrollView.setScrollY(lastScrollY);
//            refreshLayout.setRefreshing(false);
//        }
//    }

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addComment(Event event) {
        String commentText = (String) event.get("comment");
        if (commentText == null || commentText.length() == 0) {
            return;
        }
        EventBus.getDefault().post(new Event(Event.Const.GET_COMMENT_SUCCESS.name(), new Object()));
        String author = authModel.getCurrentLoginUser().getUsername();
        Comment comment = new Comment(worksId, author, new Date(), commentText);
        CommentModel.addComment(comment, new DataListener<Void>() {
            @Override
            public void onReturn(Void aVoid) {
                ToastUtils.showSuccessToast(getContext(), "评论成功");
                lastScrollY = currentScrollY;
                refreshLayout.setRefreshing(true);
//                initDelegateCreateStatus();
                initLayout();
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(String message) {

            }
        });
    }
}
