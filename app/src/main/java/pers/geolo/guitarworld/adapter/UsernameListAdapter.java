package pers.geolo.guitarworld.adapter;


import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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
import pers.geolo.guitarworld.network.api.UserRelationApi;
import pers.geolo.guitarworld.network.callback.BaseCallback;

public class UsernameListAdapter extends BaseRecyclerViewAdapter<UsernameListAdapter.ViewHolder> {

    public final String ADD_FOLLOWING = getActivity().getString(R.string.add_following);
    public final String FOLLOWED = getActivity().getString(R.string.followed);
    public final String FOLLOW_EACH_OTHER = getActivity().getString(R.string.follow_each_other);

    public UsernameListAdapter(BaseActivity activity) {
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

        String otherUsername = "";
        tvUsername.setText(otherUsername);
        HttpService.getInstance().getAPI(UserRelationApi.class)
                .getMyRelationTypeWith(otherUsername).enqueue(new BaseCallback<UserRelationType>() {
            @Override
            public void onSuccess(UserRelationType responseData) {
                switch (responseData) {
                    case FOLLOWER:
                    case UN_FOLLOW_EACH_OTHER:
                        btFollowing.setText(ADD_FOLLOWING);
                        break;
                    case FOLLOWING:
                        btFollowing.setText(FOLLOWED);
                        break;
                    case FOLLOW_EACH_OTHER:
                        btFollowing.setText(FOLLOW_EACH_OTHER);
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
            String otherUsername = "";
            UserRelation userRelation = new UserRelation(DAOService.getInstance().getCurrentLogInfo().getUsername(),
                    otherUsername);
            if (ADD_FOLLOWING.equals(text)) {
                HttpService.getInstance().getAPI(UserRelationApi.class)
                        .addRelation(userRelation).enqueue(new BaseCallback<Void>() {
                    @Override
                    public void onSuccess(Void responseData) {
                        btFollowing.setText(FOLLOWED);
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {

                    }

                    @Override
                    public void onFailure() {

                    }
                });
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("警告");
                builder.setMessage("确定取消关注吗？");
                builder.setPositiveButton("确定", (dialog, which) -> {
                    HttpService.getInstance().getAPI(UserRelationApi.class)
                            .removeRelation(userRelation).enqueue(new BaseCallback<Void>() {
                        @Override
                        public void onSuccess(Void responseData) {
                            btFollowing.setText(ADD_FOLLOWING);
                        }

                        @Override
                        public void onError(int errorCode, String errorMessage) {

                        }

                        @Override
                        public void onFailure() {

                        }
                    });
                });
                //    设置一个NegativeButton
                builder.setNegativeButton("取消", (dialog, which) -> {
                });
                //    显示出该对话框
                builder.show();

            }
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
