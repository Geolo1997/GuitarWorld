package pers.geolo.guitarworld.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.entity.User;
import pers.geolo.guitarworld.network.BaseCallback;
import pers.geolo.guitarworld.service.UserService;
import pers.geolo.guitarworld.util.SingletonHolder;

import java.util.List;

public class UserAdapter extends BaseRecyclerViewAdapter<UserAdapter.ViewHolder> {

    List<User> userList;


    public UserAdapter(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_users_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        User user = userList.get(i);
        viewHolder.tvUsername.setText(user.getUsername());
//        viewHolder.btFollowing.setText();
        viewHolder.btFollowing.setOnClickListener(v -> {
            SingletonHolder.getInstance(UserService.class).following(user.getUsername(), new BaseCallback<Void>() {
                @Override
                public void onSuccess(Void data) {
                    viewHolder.btFollowing.setText("已关注");
                }

                @Override
                public void onError(String message) {

                }

                @Override
                public void onFailure() {

                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_username)
        TextView tvUsername;
        @BindView(R.id.bt_following)
        Button btFollowing;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
