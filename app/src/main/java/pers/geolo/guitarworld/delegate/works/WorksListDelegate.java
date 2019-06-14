package pers.geolo.guitarworld.delegate.works;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseDelegate;
import pers.geolo.guitarworld.delegate.user.ProfileDelegate;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.FileListener;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.model.AuthModel;
import pers.geolo.guitarworld.model.ImageModel;
import pers.geolo.guitarworld.model.WorksModel;
import pers.geolo.guitarworld.network.HttpClient;
import pers.geolo.guitarworld.util.RecyclerViewUtils;

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
        WorksModel.getWorks(filter, new DataListener<List<Works>>() {
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
            viewHolder.tvCreateTime.setText(works.getCreateTime().toString());
            viewHolder.tvTitle.setText(works.getTitle());
            viewHolder.tvContent.setText(works.getContent());
            // 加载头像
            viewHolder.civAvatar.setImageBitmap(null);
            ImageModel.getAvatar(works.getAuthor(), new FileListener<Bitmap>() {
                @Override
                public void onProgress(int currentLength, int totalLength) {

                }

                @Override
                public void onReturn(Bitmap bitmap) {
                    viewHolder.civAvatar.setImageBitmap(bitmap);
                }

                @Override
                public void onError(String message) {

                }
            });
            // 加载第一张图
            viewHolder.firstImage.setImageBitmap(null);
            if (works.getImagePaths().size() > 0) {
                ImageModel.getImage(works.getImagePaths().get(0), new FileListener<Bitmap>() {
                    @Override
                    public void onProgress(int currentLength, int totalLength) {
                    }

                    @Override
                    public void onReturn(Bitmap bitmap) {
                        viewHolder.firstImage.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onError(String message) {
                    }
                });
            } else {
                viewHolder.firstImage.setImageBitmap(null);
            }


            // TODO 播放视频
            if (i == 1) {
                String url = HttpClient.baseUrl + "video?videoPath=video/test.mp4";
                viewHolder.vvVideo.setVisibility(View.VISIBLE);
                viewHolder.vvVideo.setVideoURI(Uri.parse(url));
                viewHolder.vvVideo.start();
            } else {
                if (viewHolder.vvVideo.isPlaying()) {
                    viewHolder.vvVideo.stopPlayback();
                }
                viewHolder.vvVideo.setVisibility(View.GONE);
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
        VideoView vvVideo;
        String[] options;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.civ_avatar, R.id.tv_author})
        public void onViewClicked(View view) {
            String author = worksList.get(getAdapterPosition()).getAuthor();
            getDelegateActivity().start(ProfileDelegate.newInstance(author));
        }

        @OnClick(R.id.ll_works_item)
        public void onWorksItemClicked() {
            int worksId = worksList.get(getAdapterPosition()).getId();
            getDelegateActivity().start(WorksDetailDelegate.newInstance(worksId));
        }

        @OnLongClick(R.id.ll_works_item)
        public boolean onWorksItemLongClick() {
            Works works = worksList.get(getAdapterPosition());
            String username = works.getAuthor();
            if (username.equals(AuthModel.getCurrentLoginUser().getUsername())) {
                options = new String[]{"删除"};
            }
            //添加列表
            AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                    .setTitle("选项")
                    .setItems(options, (dialogInterface, i) -> {
                        String text = options[i];
                        switch (text) {
                            case "删除":
                                HashMap<String, Object> filter = new HashMap<>();
                                filter.put("id", works.getId());
                                WorksModel.deleteWorks(filter, new DataListener<Void>() {
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
                                break;
                            default:
                        }
                    })
                    .create();
            alertDialog.show();
            return true;
        }
    }
}
