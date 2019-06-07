package pers.geolo.guitarworld.delegate;

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
import java.util.ArrayList;
import java.util.List;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.entity.User;
import pers.geolo.guitarworld.entity.UserRelation;
import pers.geolo.guitarworld.fragmentationtest.delegate.BaseDelegate;
import pers.geolo.guitarworld.http.callback.ISuccess;
import pers.geolo.guitarworld.model.AuthModel;
import pers.geolo.guitarworld.model.UserModel;
import pers.geolo.guitarworld.util.RecyclerViewUtils;

/**
 * @author 桀骜(Geolo)
 * @date 2019-05-31
 */
public class UserListDelegate extends BaseDelegate {

    private static final String ALL = "ALL";
    private static final String FOLLOWER = "FOLLOWER";
    private static final String FOLLOWING = "FOLLOWING";

    @BindView(R.id.rv_user_list)
    RecyclerView rvUserList;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;

    private List<User> userList = new ArrayList<>();
    private Adapter adapter = new Adapter();
    private ISuccess<List<User>> callback = new ISuccess<List<User>>() {
        @Override
        public void onSuccess(List<User> users) {
            userList = users;
            adapter.notifyDataSetChanged();
            loadRelation();
        }
    };

    public static UserListDelegate newInstance(String all, String follower, String following) {
        Bundle args = new Bundle();
        args.putString(ALL, all);
        args.putString(FOLLOWER, follower);
        args.putString(FOLLOWING, following);
        UserListDelegate fragment = new UserListDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object getLayout() {
        return R.layout.delegate_user_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        // 获取启动参数
        Bundle filter = getArguments();
        // 设置刷新控件监听
        srlRefresh.setOnRefreshListener(() -> {
            loadUserList(filter);
            srlRefresh.setRefreshing(false);
        });
        RecyclerViewUtils.setDefaultConfig(getContext(), rvUserList);
        rvUserList.setAdapter(adapter);
        loadUserList(filter);
    }

    void loadUserList(Bundle filter) {
        if (filter.getString("all") != null) {
            UserModel.getAllUser(callback);
        } else if (filter.getString("follower") != null) {
            UserModel.getFollower(filter.getString("follower"), callback);
        } else if (filter.getString("following") != null) {
            UserModel.getFollowing(filter.getString("following"), callback);
        }
    }

    void loadRelation() {

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

        @BindView(R.id.civ_avatar)
        CircleImageView civAvatar;
        @BindView(R.id.tv_username)
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
            UserRelation userRelation = new UserRelation(AuthModel.getCurrentLoginUser().getUsername(),
                    userList.get(getAdapterPosition()).getUsername());
            if (ADD_FOLLOWING.equals(text)) { // 未关注
                // 添加关注
                UserModel.addRelation(userRelation, new ISuccess<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        btFollowing.setText(FOLLOWED);
                    }
                });
            } else { // 已经关注
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("警告");
                builder.setMessage("确定取消关注吗？");
                builder.setPositiveButton("确定", (dialog, which) -> {
                    UserModel.removeRelation(userRelation, new ISuccess<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            btFollowing.setText(ADD_FOLLOWING);
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
            Bundle bundle = new Bundle();
            bundle.putString("username", username);
//            startActivity(ProfileDelegate.class, bundle);
        }
    }
}
