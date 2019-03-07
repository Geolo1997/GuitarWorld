package pers.geolo.guitarworld.adapter;


import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseActivity;
import pers.geolo.guitarworld.base.BaseRecyclerViewAdapter;
import pers.geolo.guitarworld.dao.DAOService;
import pers.geolo.guitarworld.entity.UserRelation;
import pers.geolo.guitarworld.entity.UserRelationType;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.UserRelationAPI;
import pers.geolo.guitarworld.network.callback.BaseCallback;

public class UserListAdapter extends BaseRecyclerViewAdapter<String, UserListAdapter.ViewHolder> {


    public UserListAdapter(BaseActivity activity) {
        super(activity);
    }

    @Override
    public int getItemViewId() {
        return R.layout.item_users_view;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        TextView tvUsername = viewHolder.tvUsername;
        Button btFollowing = viewHolder.btFollowing;

        String otherUsername = getDataList().get(i);
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
    }


    public class ViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder {

        @BindView(R.id.tv_username)
        TextView tvUsername;
        @BindView(R.id.bt_following)
        Button btFollowing;

        @OnClick(R.id.bt_following)
        public void onViewClicked() {
            String text = btFollowing.getText().toString();
            if (text.equals("+z da ")) {
                String otherUsername = getDataList().get(getAdapterPosition());
                HttpService.getInstance().getAPI(UserRelationAPI.class)
                        .addRelation(new UserRelation(DAOService.getInstance().getCurrentLogInfo().getUsername(),
                                otherUsername)).enqueue(new BaseCallback<Void>() {
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
                });
            }
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
