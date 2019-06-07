package pers.geolo.guitarworld.delegate;

import android.graphics.Bitmap;
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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.fragmentationtest.delegate.BaseDelegate;
import pers.geolo.guitarworld.model.AuthModel;
import pers.geolo.guitarworld.network.HttpUtils;
import pers.geolo.guitarworld.network.callback.BaseCallback;
import pers.geolo.guitarworld.util.ImageUtils;
import pers.geolo.guitarworld.util.RecyclerViewUtils;

/**
 * @author 桀骜(Geolo)
 * @date 2019-05-31
 */
public class WorksListDelegate extends BaseDelegate {

    private static final String FOLLOWER = "FOLLOWER";
    private static final String AUTHOR = "AUTHOR";

    @BindView(R.id.rv_works_list)
    RecyclerView rvWorksList;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;
    @BindView(R.id.ll_works_list)
    LinearLayout llWorksList;

    List<Works> worksList = new ArrayList<>();
    Adapter adapter = new Adapter();
    Map<String, Object> filter = new HashMap<>();


    public static WorksListDelegate newInstance(String follower, String author) {
        Bundle args = new Bundle();
        args.putString(FOLLOWER, follower);
        args.putString(AUTHOR, author);
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
            String follower = bundle.getString("follower");
            if (follower != null)
                filter.put("follower", follower);
            String author = bundle.getString("author");
            if (author != null)
                filter.put("author", author);
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
        adapter.avatarsHolder = new HashMap<>();
        adapter.firstImageHolder = new HashMap<>();
        HttpUtils.getWorks(filter, new BaseCallback<List<Works>>() {
            @Override
            public void onSuccess(List<Works> responseData) {
                worksList = responseData;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int errorCode, String errorMessage) {

            }

            @Override
            public void onFailure() {

            }
        });
    }

    class Adapter extends RecyclerView.Adapter<ViewHolder> {

        Map<String, Bitmap> avatarsHolder = new ConcurrentHashMap<>();
        Map<Integer, Bitmap> firstImageHolder = new ConcurrentHashMap<>();

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
            Bitmap avatarBitmap = avatarsHolder.get(works.getAuthor());
            if (avatarBitmap != null) {
                viewHolder.civAvatar.setImageBitmap(avatarBitmap);
            } else {
                viewHolder.civAvatar.setImageBitmap(null);
                ImageUtils.getBitmap(getActivity(), HttpUtils.getAavatarRooter(works.getAuthor()), bitmap1 -> {
                    avatarsHolder.put(works.getAuthor(), bitmap1);
                    viewHolder.civAvatar.setImageBitmap(bitmap1);
                });
            }
            // 加载第一张图
            if (works.getImagePaths().size() > 0) {
                Bitmap firstImageBitmap = firstImageHolder.get(works.getId());
                if (firstImageBitmap != null) {
                    viewHolder.firstImage.setImageBitmap(firstImageBitmap);
                } else {
                    viewHolder.firstImage.setImageBitmap(null);
                    ImageUtils.getBitmap(getActivity(), HttpUtils.getImageRooter(works.getImagePaths().get(0)),
                            bitmap1 -> {
                                firstImageHolder.put(works.getId(), bitmap1);
                                viewHolder.firstImage.setImageBitmap(bitmap1);
                            });
                }
            } else {
                viewHolder.firstImage.setImageBitmap(null);
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
        String[] options;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.civ_avatar, R.id.tv_author})
        public void onViewClicked(View view) {
            String author = worksList.get(getAdapterPosition()).getAuthor();
            Bundle bundle = new Bundle();
            bundle.putString("username", author);
//            startActivity(ProfileDelegate.class, bundle);
        }

        @OnClick(R.id.ll_works_item)
        public void onWorksItemClicked() {
            int worksId = worksList.get(getAdapterPosition()).getId();
            Bundle bundle = new Bundle();
            bundle.putInt("worksId", worksId);
//            startActivity(WorksDetailDelegate.class, bundle);
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
                                HttpUtils.removeWorks(filter, new BaseCallback<Void>() {
                                    @Override
                                    public void onSuccess(Void responseData) {
                                        worksList.remove(works);
                                        adapter.notifyItemChanged(getAdapterPosition());
//                                        showToast("删除成功！");
                                    }

                                    @Override
                                    public void onError(int errorCode, String errorMessage) {

                                    }

                                    @Override
                                    public void onFailure() {

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
