package pers.geolo.guitarworld.controller.works;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import de.hdodenhof.circleimageview.CircleImageView;
import org.greenrobot.eventbus.EventBus;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.base.BaseController;
import pers.geolo.guitarworld.controller.base.BeanFactory;
import pers.geolo.guitarworld.controller.user.ProfileController;
import pers.geolo.guitarworld.entity.*;
import pers.geolo.guitarworld.model.AuthModel;
import pers.geolo.guitarworld.model.UserModel;
import pers.geolo.guitarworld.model.WorksModel;
import pers.geolo.guitarworld.network.ParamBeanHandler;
import pers.geolo.guitarworld.network.param.WorksParam;
import pers.geolo.guitarworld.util.*;

import java.io.File;
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
    Adapter adapter = new Adapter();
    Map<String, Object> filter = new HashMap<>();

    AuthModel authModel = BeanFactory.getBean(AuthModel.class);
    UserModel userModel = BeanFactory.getBean(UserModel.class);
    WorksModel worksModel = BeanFactory.getBean(WorksModel.class);

    @Deprecated
    public static WorksListController newInstance(HashMap<String, Object> filter) {
        Bundle args = new Bundle();
        args.putSerializable(FILTER, filter);
        WorksListController worksListController = new WorksListController();
        worksListController.setArguments(args);
        return worksListController;
    }

    public static WorksListController newInstance(WorksParam param) {

        Bundle args = new Bundle();
        // TODO 临时转换
        args.putSerializable(FILTER, ParamBeanHandler.handle(param));
        WorksListController fragment = new WorksListController();
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public void onNewBundle(Bundle args) {
//        super.onNewBundle(args);
//    }

    @Override
    public Object getLayoutView() {
        return R.layout.works_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

        // 获取过滤器
        Bundle bundle = getArguments();
        if (bundle != null) {
            //noinspection unchecked
            filter = (Map<String, Object>) bundle.getSerializable(FILTER);
        }
        initRefreshLayout();
        initRecyclerView();
        loadWorksList();
    }

    private void initRecyclerView() {
        // 设置适配器
        RecyclerViewUtils.setDefaultConfig(getContext(), rvWorksList);
        rvWorksList.setAdapter(adapter);
    }

    private void initRefreshLayout() {
        // 设置刷新控件监听
//        srlRefresh.setOnRefreshListener(() -> {
//            loadWorksList();
//            srlRefresh.setRefreshing(false);
//        });
    }

    public void loadWorksList() {
        worksModel.getWorks(filter, new DataListener<List<Works>>() {
            @Override
            public void onReturn(List<Works> worksList) {
                WorksListController.this.worksList = worksList;
                adapter.notifyDataSetChanged();
                EventBus.getDefault().post(new GetWorksListSuccessEvent(worksList));
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    public int getScollYDistance() {
        // TODO 空指针异常
        LinearLayoutManager layoutManager = (LinearLayoutManager) rvWorksList.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisiableChildView = layoutManager.findViewByPosition(position);
        int itemHeight = firstVisiableChildView.getHeight();
        return (position) * itemHeight - firstVisiableChildView.getTop();
    }

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

    class Adapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.works_content, viewGroup, false);
            return new ViewHolder(view);
        }

        // TODO ViewHolder回收后残留，导致加载混乱
        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.init();
            Works works = worksList.get(i);
            viewHolder.authorText.setText(works.getAuthor());
            viewHolder.createTimeText.setText(DateUtils.toFriendlyString(works.getCreateTime()));
            viewHolder.titleText.setText(works.getTitle());

            // 加载头像
            userModel.getPublicProfile(works.getAuthor(), new DataListener<User>() {
                @Override
                public void onReturn(User user) {
                    GlideUtils.load(getContext(), user.getAvatarUrl(), viewHolder.avatarImage);
                }

                @Override
                public void onError(String message) {

                }
            });
            if (works.getType() == WorksType.IMAGE_TEXT) { // 图文创作
                viewHolder.contentText.setVisibility(View.VISIBLE);
                viewHolder.contentText.setText(works.getContent());
                int imageSize = works.getImageUrls().size();
                if (imageSize == 1) {
                    // 加载一张图
                    viewHolder.firstImage.setVisibility(View.VISIBLE);
                    GlideUtils.load(getContext(), works.getImageUrls().get(0), viewHolder.firstImage);
                } else if (imageSize > 1) {
                    viewHolder.setImageList(works.getImageUrls());
                }
            } else if (works.getType() == WorksType.VIDEO) { // 视频创作
                String url = works.getVideoUrl();
                viewHolder.video.setVisibility(View.VISIBLE);
                viewHolder.video.setUp(url, works.getTitle());
                String previewImageUrl = works.getVideoPreviewUrl();
                if (previewImageUrl != null && !"".equals(previewImageUrl)) {
                    GlideUtils.load(getContext(), previewImageUrl, viewHolder.video.thumbImageView);
                }
            }
        }

        @Override
        public int getItemCount() {
            return worksList.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.avatar_image)
        CircleImageView avatarImage;
        @BindView(R.id.author_text)
        TextView authorText;
        @BindView(R.id.create_time_text)
        TextView createTimeText;
        @BindView(R.id.title_text)
        TextView titleText;
        @BindView(R.id.content_text)
        TextView contentText;
        @BindView(R.id.first_image)
        ImageView firstImage;
        @BindView(R.id.image_list_layout)
        ViewGroup imageListLayout;
        @BindView(R.id.video)
        JzvdStd video;

        ImageListController controller;

        String[] options;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            controller = new ImageListController();
            controller.createView(imageListLayout);
//            controller.setActivity(getContainerActivity());
            imageListLayout.addView(controller.getRootView());
            init();

        }

        public void init() {
            titleText.setMaxLines(2);
            contentText.setMaxLines(4);
            avatarImage.setImageBitmap(null);
            authorText.setText("");
            createTimeText.setText("");
            titleText.setText("");
            contentText.setText("");
            firstImage.setImageBitmap(null);
            video.reset();
            video.thumbImageView.setImageBitmap(null);
            // 设置不可见
            contentText.setVisibility(View.GONE);
            firstImage.setVisibility(View.GONE);
            imageListLayout.setVisibility(View.GONE);
            video.setVisibility(View.GONE);
        }

        @OnClick({R.id.avatar_image, R.id.author_text})
        public void onViewClicked(View view) {
            String author = worksList.get(getAdapterPosition()).getAuthor();
            getContainerActivity().start(ProfileController.newInstance(author));
        }

        @OnClick(R.id.works_content_layout)
        public void onWorksItemClicked() {
            int worksId = worksList.get(getAdapterPosition()).getId();
            getContainerActivity().start(WorksDetailController.newInstance(worksId));
        }

        @OnLongClick(R.id.works_content_layout)
        public boolean onWorksItemLongClick() {
            Works works = worksList.get(getAdapterPosition());
            String username = works.getAuthor();
            if (username.equals(authModel.getCurrentLoginUser().getUsername())) {
                options = new String[]{"删除"};
            }
            //添加列表
            AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                    .setTitle("选项")
                    .setItems(options, (dialogInterface, i) -> {
                        String text = options[i];
                        switch (text) {
                            case "删除":
                                removeWorks(works);
                                break;
                            default:
                        }
                    })
                    .create();
            alertDialog.show();
            return true;
        }

        private void removeWorks(Works works) {
            WorksParam param = new WorksParam();
            param.setWorksId(works.getId());
            worksModel.deleteWorks(ParamBeanHandler.handle(param), new DataListener<Void>() {
                @Override
                public void onReturn(Void aVoid) {
                    worksList.remove(works);
                    adapter.notifyItemRemoved(getAdapterPosition());
                    ToastUtils.showSuccessToast(getContext(), "删除成功");
                }

                @Override
                public void onError(String message) {
                    ToastUtils.showErrorToast(getContext(), message);
                }
            });
        }

        public void setImageList(List<String> imageUrls) {
            imageListLayout.setVisibility(View.VISIBLE);
            controller.onNewList(imageUrls);
        }
    }
}
