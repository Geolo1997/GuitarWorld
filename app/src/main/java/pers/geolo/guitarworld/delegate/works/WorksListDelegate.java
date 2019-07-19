package pers.geolo.guitarworld.delegate.works;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import de.hdodenhof.circleimageview.CircleImageView;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseDelegate;
import pers.geolo.guitarworld.base.BeanFactory;
import pers.geolo.guitarworld.delegate.user.ProfileDelegate;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.User;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.entity.WorksType;
import pers.geolo.guitarworld.model.AuthModel;
import pers.geolo.guitarworld.model.UserModel;
import pers.geolo.guitarworld.model.WorksModel;
import pers.geolo.guitarworld.network.ParamBeanHandler;
import pers.geolo.guitarworld.network.param.WorksParam;
import pers.geolo.guitarworld.util.DateUtils;
import pers.geolo.guitarworld.util.GlideUtils;
import pers.geolo.guitarworld.util.RecyclerViewUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 桀骜(Geolo)
 * @date 2019-05-31
 */
public class WorksListDelegate extends BaseDelegate {

    private static final String FILTER = "FILTER";

    @BindView(R.id.rv_works_list)
    RecyclerView rvWorksList;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;
    @BindView(R.id.ll_works_list)
    LinearLayout llWorksList;

    List<Works> worksList = new ArrayList<>();
    Adapter adapter = new Adapter();
    Map<String, Object> filter = new HashMap<>();

    AuthModel authModel = BeanFactory.getBean(AuthModel.class);
    UserModel userModel = BeanFactory.getBean(UserModel.class);
    WorksModel worksModel = BeanFactory.getBean(WorksModel.class);

    public static WorksListDelegate newInstance(HashMap<String, Object> filter) {
        Bundle args = new Bundle();
        args.putSerializable(FILTER, filter);
        WorksListDelegate worksListDelegate = new WorksListDelegate();
        worksListDelegate.setArguments(args);
        return worksListDelegate;
    }

    @Override
    public void onNewBundle(Bundle args) {
        super.onNewBundle(args);
    }

    @Override
    public Object getLayout() {
        return R.layout.delegate_works_list;
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
        srlRefresh.setOnRefreshListener(() -> {
            loadWorksList();
            srlRefresh.setRefreshing(false);
        });
    }

    void loadWorksList() {
        worksModel.getWorks(filter, new DataListener<List<Works>>() {
            @Override
            public void onReturn(List<Works> worksList) {
                WorksListDelegate.this.worksList = worksList;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    @Override
    public boolean onBackPressedSupport() {
        if (Jzvd.backPress()) {
            return true;
        }
        return super.onBackPressedSupport();
    }

    @Override
    public void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    class Adapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_works_view, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            Works works = worksList.get(i);
            viewHolder.tvAuthor.setText(works.getAuthor());
            viewHolder.tvCreateTime.setText(DateUtils.toFriendlyString(works.getCreateTime()));
            viewHolder.tvTitle.setText(works.getTitle());

            // 加载头像
            viewHolder.civAvatar.setImageBitmap(null);
            userModel.getPublicProfile(works.getAuthor(), new DataListener<User>() {
                @Override
                public void onReturn(User user) {
                    GlideUtils.load(getContext(), user.getAvatarPath(), viewHolder.civAvatar);
                }

                @Override
                public void onError(String message) {

                }
            });

            if (works.getType() == WorksType.IMAGE_TEXT) { // 图文创作
                viewHolder.vvVideo.setVisibility(View.GONE);
                viewHolder.tvContent.setVisibility(View.VISIBLE);
                viewHolder.tvContent.setText(works.getContent());
                // 加载第一张图
                viewHolder.firstImage.setImageBitmap(null);
                if (works.getImagePaths().size() > 0) {
                    GlideUtils.load(getContext(), works.getImagePaths().get(0), viewHolder.firstImage);
                } else {
                    viewHolder.firstImage.setImageBitmap(null);
                }
            } else if (works.getType() == WorksType.VIDEO) { // 视频创作
                String url = works.getVideoUrl();
                viewHolder.tvContent.setVisibility(View.GONE);
                viewHolder.vvVideo.setVisibility(View.VISIBLE);
                viewHolder.vvVideo.setUp(url, works.getTitle());
                viewHolder.vvVideo.startVideo();
            }
        }

        @Override
        public int getItemCount() {
            return worksList.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.civ_avatar)
        CircleImageView civAvatar;
        @BindView(R.id.tv_id)
        TextView tvId;
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.tv_create_time)
        TextView tvCreateTime;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.first_image)
        ImageView firstImage;
        @BindView(R.id.vv_video)
        JzvdStd vvVideo;

        String[] options;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.civ_avatar, R.id.tv_author})
        public void onViewClicked(View view) {
            String author = worksList.get(getAdapterPosition()).getAuthor();
            getContainerActivity().start(ProfileDelegate.newInstance(author));
        }

        @OnClick(R.id.ll_works_item)
        public void onWorksItemClicked() {
            int worksId = worksList.get(getAdapterPosition()).getId();
            getContainerActivity().start(WorksDetailDelegate.newInstance(worksId));
        }

        @OnLongClick(R.id.ll_works_item)
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
                    Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(String message) {

                }
            });
        }
    }
}
