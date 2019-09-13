package pers.geolo.guitarworld.controller.user;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.base.BaseController;
import pers.geolo.guitarworld.controller.base.BeanFactory;
import pers.geolo.guitarworld.entity.DataCallback;
import pers.geolo.guitarworld.entity.User;
import pers.geolo.guitarworld.entity.UserRelation;
import pers.geolo.guitarworld.model.AuthModel;
import pers.geolo.guitarworld.model.UserModel;
import pers.geolo.guitarworld.util.RecyclerViewUtils;
import pers.geolo.guitarworld.util.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 桀骜(Geolo)
 * @date 2019-05-31
 */
public class UserListController extends BaseController {

    private static final String ALL = "ALL";
    private static final String FOLLOWER = "FOLLOWER";
    private static final String FOLLOWING = "FOLLOWING";
    private static final String FILTER = "FILTER";

    @BindView(R.id.rv_user_list)
    RecyclerView rvUserList;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;

    AuthModel authModel = BeanFactory.getBean(AuthModel.class);
    UserModel userModel = BeanFactory.getBean(UserModel.class);

    private List<User> userList = new ArrayList<>();
    private HashMap<String, Object> filter = new HashMap<>();
    private Adapter adapter = new Adapter();
    private DataCallback callback = new DataCallback<List<User>>() {
        @Override
        public void onReturn(List<User> users) {
            userList = users;
            adapter.notifyDataSetChanged();
            loadRelation();
        }

        @Override
        public void onError(String message) {
            ToastUtils.showErrorToast(getContext(), message);
        }
    };

    public static UserListController newInstance(HashMap<String, Object> filter) {
        Bundle args = new Bundle();
        args.putSerializable(FILTER, filter);
        UserListController fragment = new UserListController();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object getLayoutView() {
        return R.layout.controller_user_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        // 获取启动参数
        Bundle bundle = getArguments();
        if (bundle != null) {
            //noinspection unchecked
            filter = (HashMap<String, Object>) bundle.getSerializable(FILTER);
        }
        // 设置刷新控件监听
        srlRefresh.setOnRefreshListener(() -> {
            initUserList(filter);
            srlRefresh.setRefreshing(false);
        });
        RecyclerViewUtils.setDefaultConfig(getContext(), rvUserList);
        rvUserList.setAdapter(adapter);
        initUserList(filter);
    }

    void initUserList(HashMap<String, Object> filter) {
        if (filter.get("all") != null) {
            userModel.getAllUser(callback);
        } else if (filter.get("follower") != null) {
            userModel.getFollowing((String) filter.get("follower"), callback);
        } else if (filter.get("following") != null) {
            userModel.getFollower((String) filter.get("following"), callback);
        }
    }

    void loadRelation() {
        String currentUsername = authModel.getLoginUser().getUsername();
        for (int i = 0; i < userList.size(); i++) {
            String username = userList.get(i).getUsername();
//            UserModel.getUserRelation(currentUsername, username, new DataCallback<UserRelation>() {
//                @Override
//                public void onReturn(UserRelation userRelation) {
//
//                }
//
//                @Override
//                public void onError(String message) {
//
//                }
//            });
        }
    }

    class Adapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_users_view, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            User user = userList.get(i);
            viewHolder.tvUsername.setText(user.getUsername());
            viewHolder.tvEmail.setText(user.getEmail());
        }

        @Override
        public int getItemCount() {
            return userList.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private static final String ADD_FOLLOWING = "+关注";
        private static final String FOLLOWED = "已关注";
        private static final String FOLLOW_EACH_OTHER = "互相关注";

        @BindView(R.id.avatar_image)
        CircleImageView civAvatar;
        @BindView(R.id.username_text)
        TextView tvUsername;
        @BindView(R.id.tv_email)
        TextView tvEmail;
        @BindView(R.id.bt_following)
        Button btFollowing;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.bt_following)
        public void onBtFollowingClicked() {
            String text = btFollowing.getText().toString();
            UserRelation userRelation = new UserRelation(authModel.getLoginUser().getUsername(),
                    userList.get(getAdapterPosition()).getUsername());
            if (ADD_FOLLOWING.equals(text)) { // 未关注
                // 添加关注
                userModel.addRelation(userRelation, new DataCallback<Void>() {
                    @Override
                    public void onReturn(Void aVoid) {
                        btFollowing.setText(FOLLOWED);
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
            } else { // 已经关注
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("警告");
                builder.setMessage("确定取消关注吗？");
                builder.setPositiveButton("确定", (dialog, which) -> {
                    userModel.removeRelation(userRelation, new DataCallback<Void>() {
                        @Override
                        public void onReturn(Void aVoid) {
                            btFollowing.setText(ADD_FOLLOWING);
                        }

                        @Override
                        public void onError(String message) {

                        }
                    });
                });
                builder.setNegativeButton("取消", (dialog, which) -> {
                });
                //    显示出该对话框
                builder.show();
            }
        }

        @OnClick(R.id.ll_user_item)
        public void onLlUserItemClicked() {
            String username = userList.get(getAdapterPosition()).getUsername();
//            getContainerActivity().start(ProfileController.newInstance(username));
        }
    }
}
