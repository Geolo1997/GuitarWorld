package pers.geolo.guitarworld.controller.works;

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
import pers.geolo.guitarworld.controller.base.BeanFactory;
import pers.geolo.guitarworld.controller.base.SwipeBackController;
import pers.geolo.guitarworld.entity.Comment;
import pers.geolo.guitarworld.entity.DataCallback;
import pers.geolo.guitarworld.entity.event.Event;
import pers.geolo.guitarworld.model.AuthModel;
import pers.geolo.guitarworld.model.CommentModel;
import pers.geolo.guitarworld.model.WorksModel;
import pers.geolo.guitarworld.util.ToastUtils;

import java.util.Date;
import java.util.HashMap;

public class WorksDetailController extends SwipeBackController {

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

    WorksItemController worksItemController;
    CommentListController commentListController;
    WorksOptionController worksOptionController;

    private int worksId;

    private int currentScrollY;
    private int lastScrollY;

    public static WorksDetailController newInstance(int worksId) {
        Bundle args = new Bundle();
        args.putInt(WORKS_ID, worksId);
        WorksDetailController fragment = new WorksDetailController();
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
//        initControllerCreateStatus();
        initScrollListener();
        if (getArguments() != null) {
            worksId = getArguments().getInt(WORKS_ID);
            HashMap<String, Object> filter = new HashMap<>();
            filter.put("worksId", worksId);
            // 加载WorksContentController
//            worksItemController = WorksItemController.newInstance(worksId);
//            loadRootFragment(R.id.works_content_layout, worksItemController);
            // 加载CommentListController
            commentListController = CommentListController.newInstance(filter);
            loadRootFragment(R.id.comment_list_layout, commentListController);
            //
            worksOptionController = WorksOptionController.newInstance(worksId);
            loadRootFragment(R.id.works_option_layout, worksOptionController);

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
//                WorksDetailController.this.currentScrollY = scrollY;
//                if (scrollY == 0) {
//                    scrollView.setScrollY(lastScrollY);
//                }
            }
        });
    }

    public void initLayout() {
//        worksItemController.onBindView(null, worksItemController.getRootView());
        commentListController.onBindView(null, commentListController.getRootView());
        worksOptionController.onBindView(null, worksOptionController.getRootView());
    }

//    private boolean worksContentControllerCreated;
//    private boolean commentListControllerCreated;
//    private boolean worksOptionControllerCreated;
//
//    public void initControllerCreateStatus() {
//        worksContentControllerCreated = false;
//        commentListControllerCreated = false;
//        worksOptionControllerCreated = false;
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onSubControllerCreateFinish(Event event) {
//        String controllerName = (String) event.get(Event.Const.CREATE_DELEGATE_FINISH.name());
//        if (worksItemController.getClass().getName().equals(controllerName)) {
//            worksContentControllerCreated = true;
//        }
//        if (commentListController.getClass().getName().equals(controllerName)) {
//            commentListControllerCreated = true;
//        }
//        if (worksItemController.getClass().getName().equals(controllerName)) {
//            worksContentControllerCreated = true;
//        }
//        if (worksContentControllerCreated && commentListControllerCreated && worksOptionControllerCreated) {
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
        String author = authModel.getLoginUser().getUsername();
        Comment comment = new Comment(worksId, author, new Date(), commentText);
        CommentModel.addComment(comment, new DataCallback<Void>() {
            @Override
            public void onReturn(Void aVoid) {
                ToastUtils.showSuccessToast(getContext(), "评论成功");
                lastScrollY = currentScrollY;
                refreshLayout.setRefreshing(true);
//                initControllerCreateStatus();
                initLayout();
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(String message) {

            }
        });
    }
}
