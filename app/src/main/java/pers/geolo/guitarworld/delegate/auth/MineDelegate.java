package pers.geolo.guitarworld.delegate.auth;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.HashMap;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseDelegate;
import pers.geolo.guitarworld.delegate.user.FriendDelegate;
import pers.geolo.guitarworld.delegate.user.ProfileDelegate;
import pers.geolo.guitarworld.delegate.works.WorksListDelegate;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.FileListener;
import pers.geolo.guitarworld.model.AuthModel;
import pers.geolo.guitarworld.model.ImageModel;

public class MineDelegate extends BaseDelegate {

    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.civ_avatar)
    CircleImageView civAvatar;

    @Override
    public Object getLayout() {
        return R.layout.delegate_mine;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        String currentUsername = AuthModel.getCurrentLoginUser().getUsername();
        tvUsername.setText(currentUsername);
        ImageModel.getAvatar(currentUsername, new FileListener<Bitmap>() {
            @Override
            public void onProgress(long currentLength, long totalLength) {

            }

            @Override
            public void onFinish(Bitmap bitmap) {
                civAvatar.setImageBitmap(bitmap);
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    @OnClick({R.id.civ_avatar, R.id.tv_username})
    public void toProfileDelegate() {
        String currentUsername = AuthModel.getCurrentLoginUser().getUsername();
        getContainerActivity().start(ProfileDelegate.newInstance(currentUsername));
    }

    @OnClick(R.id.bt_my_profile)
    public void onBtMyProfileClicked() {
        getContainerActivity().start(ProfileDelegate.newInstance(AuthModel.getCurrentLoginUser().getUsername()));
    }

    @OnClick(R.id.bt_my_works)
    public void onBtMyWorksClicked() {
        HashMap<String, Object> filter = new HashMap<>();
        filter.put("author", AuthModel.getCurrentLoginUser().getUsername());
        getContainerActivity().start(WorksListDelegate.newInstance(filter));
    }

    @OnClick({R.id.bt_my_following, R.id.bt_my_follower})
    public void onBtMyAttentionOrMyFansClicked(View view) {
        int id = view.getId();
        getContainerActivity().start(FriendDelegate.newInstance(AuthModel.getCurrentLoginUser().getUsername(),
                id == R.id.bt_my_following ? "following" : "follower"));
    }

    @OnClick(R.id.bt_logout)
    public void onLogout() {
        AuthModel.logout(new DataListener<Void>() {
            @Override
            public void onReturn(Void aVoid) {
                getContainerActivity().startWithPop(new LoginDelegate());
            }

            @Override
            public void onError(String message) {

            }
        });
    }
}
