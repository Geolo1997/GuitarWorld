package pers.geolo.guitarworld.controller.works;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import cn.jzvd.JzvdStd;
import de.hdodenhof.circleimageview.CircleImageView;
import org.greenrobot.eventbus.EventBus;
import org.microview.core.ViewParams;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.BaseController;
import pers.geolo.guitarworld.controller.base.BeanFactory;
import pers.geolo.guitarworld.controller.user.ProfileController;
import pers.geolo.guitarworld.entity.*;
import pers.geolo.guitarworld.model.AuthModel;
import pers.geolo.guitarworld.model.UserModel;
import pers.geolo.guitarworld.model.WorksModel;
import pers.geolo.guitarworld.network.ParamBeanHandler;
import pers.geolo.guitarworld.network.param.WorksParams;
import pers.geolo.guitarworld.util.DateUtils;
import pers.geolo.guitarworld.util.GlideUtils;
import pers.geolo.guitarworld.util.RecyclerViewUtils;
import pers.geolo.guitarworld.util.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 桀骜(Geolo)
 * @date 2019-05-31
 */
public class WorksListController extends BaseController {

    private static final String FILTER = "FILTER";
    private static final String WORKS_PARAM = "WORKS_PARAM";

    @BindView(R.id.rv_works_list)
    RecyclerView rvWorksList;
    //    @BindView(R.id.srl_refresh)
//    SwipeRefreshLayout srlRefresh;
    @BindView(R.id.ll_works_list)
    LinearLayout llWorksList;

    List<Works> worksList = new ArrayList<>();
//    Adapter adapter = new Adapter();
    Map<String, Object> filter = new HashMap<>();

    AuthModel authModel = BeanFactory.getBean(AuthModel.class);
    UserModel userModel = BeanFactory.getBean(UserModel.class);
    WorksModel worksModel = BeanFactory.getBean(WorksModel.class);


    @Override
    protected int getLayout() {
        return R.layout.works_list;
    }

    @Override
    public void initView(ViewParams viewParams) {
//        List<Works>
//        // 获取过滤器
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            //noinspection unchecked
//            filter = (Map<String, Object>) bundle.getSerializable(FILTER);
//        }
//        initRefreshLayout();
//        initRecyclerView();
//        loadWorksList();
    }

//    private void initRecyclerView() {
//        // 设置适配器
//        RecyclerViewUtils.setDefaultConfig(getContext(), rvWorksList);
//        rvWorksList.setAdapter(adapter);
//    }
//
//    private void initRefreshLayout() {
//        // 设置刷新控件监听
////        srlRefresh.setOnRefreshListener(() -> {
////            loadWorksList();
////            srlRefresh.setRefreshing(false);
////        });
//    }
//
//    public void loadWorksList() {
//        worksModel.getWorks(filter, new DataCallback<List<Works>>() {
//            @Override
//            public void onReturn(List<Works> worksList) {
//                WorksListController.this.worksList = worksList;
//                adapter.notifyDataSetChanged();
//                EventBus.getDefault().post(new GetWorksListSuccessEvent(worksList));
//            }
//
//            @Override
//            public void onError(String message) {
//
//            }
//        });
//    }

//    public int getScollYDistance() {
//        // TODO 空指针异常
//        LinearLayoutManager layoutManager = (LinearLayoutManager) rvWorksList.getLayoutManager();
//        int position = layoutManager.findFirstVisibleItemPosition();
//        View firstVisiableChildView = layoutManager.findViewByPosition(position);
//        int itemHeight = firstVisiableChildView.getHeight();
//        return (position) * itemHeight - firstVisiableChildView.getTop();
//    }


//    @Override
//    public boolean onBackPressedSupport() {
//        if (Jzvd.backPress()) {
//            return true;
//        }
//        return super.onBackPressedSupport();
//    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        Jzvd.releaseAllVideos();
//    }

//
//        @OnClick(R.id.works_content_layout)
//        public void onWorksItemClicked() {
//            int worksId = worksList.get(getAdapterPosition()).getId();
//            getContainerActivity().start(WorksDetailController.newInstance(worksId));
//        }
//
//        @OnLongClick(R.id.works_content_layout)
//        public boolean onWorksItemLongClick() {
//            Works works = worksList.get(getAdapterPosition());
//            String username = works.getAuthor();
//            if (username.equals(authModel.getLoginUser().getUsername())) {
//                options = new String[]{"删除"};
//            }
//            //添加列表
//            AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
//                    .setTitle("选项")
//                    .setItems(options, (dialogInterface, i) -> {
//                        String text = options[i];
//                        switch (text) {
//                            case "删除":
//                                removeWorks(works);
//                                break;
//                            default:
//                        }
//                    })
//                    .create();
//            alertDialog.show();
//            return true;
//        }
//
//        private void removeWorks(Works works) {
//            WorksParams param = new WorksParams();
//            param.setWorksId(works.getId());
//            worksModel.deleteWorks(ParamBeanHandler.handle(param), new DataCallback<Void>() {
//                @Override
//                public void onReturn(Void aVoid) {
//                    worksList.remove(works);
//                    adapter.notifyItemRemoved(getAdapterPosition());
//                    ToastUtils.showSuccessToast(getContext(), "删除成功");
//                }
//
//                @Override
//                public void onError(String message) {
//                    ToastUtils.showErrorToast(getContext(), message);
//                }
//            });
//        }
//
//        public void setImageList(List<String> imageUrls) {
//            imageListLayout.setVisibility(View.VISIBLE);
//            controller.onNewList(imageUrls);
//        }
}
