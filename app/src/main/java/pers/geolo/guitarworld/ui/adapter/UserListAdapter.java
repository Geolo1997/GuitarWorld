package pers.geolo.guitarworld.ui.adapter;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import java.io.InputStream;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.presenter.user.UserListPresenter;
import pers.geolo.guitarworld.ui.activity.ProfileActivity;
import pers.geolo.guitarworld.ui.base.BaseActivity;
import pers.geolo.guitarworld.ui.temp.ConfirmCallBack;
import pers.geolo.guitarworld.util.ModuleMessage;
import pers.geolo.guitarworld.view.UserItemView;
import pers.geolo.guitarworld.view.UserListView;

public class UserListAdapter extends MvpRecyclerViewAdapter<UserListAdapter.ViewHolder, UserItemView>
        implements UserListView {

    private UserListPresenter presenter = new UserListPresenter();

    public UserListAdapter(BaseActivity activity) {
        super(activity);
        presenter.bind(this);
    }

    public UserListPresenter getPresenter() {
        return presenter;
    }

    @Override
    public int getItemViewId() {
        return R.layout.item_users_view;
    }

    @Override
    public void onBindItemView(ViewHolder viewHolder, int i) {
        presenter.onBindItemView(viewHolder, i);
    }

    @Override
    public int getItemCount() {
        return presenter.getListSize();
    }

    @Override
    protected ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void toUserInfo(String username) {
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        intent.putExtra(ModuleMessage.CURRENT_USERNAME, username);
        getActivity().startActivity(intent);
    }

    public class ViewHolder extends MvpRecyclerViewAdapter.ViewHolder implements UserItemView {

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
        }

        @OnClick(R.id.bt_following)
        public void onViewClicked() {
            presenter.changeFollowState(getIndex());
        }

        @OnClick(R.id.ll_user_item)
        public void toUserInfo() {
            presenter.toUserInfo(getIndex());
        }

        @Override
        public String getFollowState() {
            return btFollowing.getText().toString().trim();
        }

        @Override
        public void setFollowState(String followText) {
            btFollowing.setText(followText);
        }

        @Override
        public void showConfirmAlert(ConfirmCallBack confirmCallBack) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("警告");
            builder.setMessage("确定取消关注吗？");
            builder.setPositiveButton("确定", (dialog, which) -> {
                confirmCallBack.onConfirmCallBack(true);
            });
            //    设置一个NegativeButton
            builder.setNegativeButton("取消", (dialog, which) -> {
                confirmCallBack.onConfirmCallBack(false);
            });
            //    显示出该对话框
            builder.show();
        }

        @Override
        public void setEmail(String email) {
            tvEmail.setText(email);
        }

        @Override
        public String getUsername() {
            return tvUsername.getText().toString();
        }

        @Override
        public void setAvatar(InputStream inputStream) {
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            getActivity().runOnUiThread(() -> {
                civAvatar.setImageBitmap(bitmap);
            });
        }

        @Override
        public void setUsername(String otherUsername) {
            tvUsername.setText(otherUsername);
        }
    }
}
