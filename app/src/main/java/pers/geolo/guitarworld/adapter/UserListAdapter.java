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
import pers.geolo.guitarworld.entity.UserRelation;
import pers.geolo.guitarworld.entity.UserRelationType;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.UserRelationAPI;
import pers.geolo.guitarworld.network.callback.BaseCallback;
import pers.geolo.guitarworld.dao.DAOService;
import pers.geolo.util.SingletonHolder;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {


    private List<String> usernameList;

    public UserListAdapter(List<String> usernameList) {
        this.usernameList = usernameList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_users_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        TextView tvUsername = viewHolder.tvUsername;
        Button btFollowing = viewHolder.btFollowing;

        String otherUsername = usernameList.get(i);
        tvUsername.setText(otherUsername);
        HttpService.getInstance().getAPI(UserRelationAPI.class)
                .getMyRelationTypeWith(otherUsername)
                .enqueue(new BaseCallback<UserRelationType>() {
                    @Override
                    public void onSuccess(UserRelationType responseData) {
                        switch (responseData) {
                            case FOLLOWING:
                            case UN_FOLLOW_EACH_OTHER:
                                btFollowing.setText("+关注");
                                break;
                            case FOLLOWER:
                                btFollowing.setText("已关注");
                                btFollowing.setClickable(false);
                                break;
                            case FOLLOW_EACH_OTHER:
                                btFollowing.setText("互相关注");
                                btFollowing.setClickable(false);
                                break;
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {

                    }

                    @Override
                    public void onFailure() {

                    }
                });
        viewHolder.btFollowing.setOnClickListener(v -> SingletonHolder.getInstance(HttpService.class)
                .getAPI(UserRelationAPI.class)
                .addRelation(new UserRelation(SingletonHolder.getInstance(DAOService.class)
                        .getCurrentLogInfo().getUsername(), otherUsername))
                .enqueue(new BaseCallback<Void>() {
                    @Override
                    public void onSuccess(Void responseData) {
                        btFollowing.setText("已关注");
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {

                    }

                    @Override
                    public void onFailure() {

                    }
                }));
    }

    @Override
    public int getItemCount() {
        return usernameList.size();
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
