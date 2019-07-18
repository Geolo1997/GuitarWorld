package pers.geolo.guitarworld.delegate.auth;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseDelegate;
import pers.geolo.guitarworld.base.BeanFactory;
import pers.geolo.guitarworld.delegate.user.FriendDelegate;
import pers.geolo.guitarworld.delegate.user.ProfileDelegate;
import pers.geolo.guitarworld.delegate.works.WorksListDelegate;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.User;
import pers.geolo.guitarworld.model.AuthModel;
import pers.geolo.guitarworld.model.UserModel;
import pers.geolo.guitarworld.util.GlideUtils;

import java.util.HashMap;

public class MineDelegate extends BaseDelegate {

    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.civ_avatar)
    CircleImageView civAvatar;

    UserModel userModel = BeanFactory.getBean(UserModel.class);
    AuthModel authModel = BeanFactory.getBean(AuthModel.class);

    @Override
    public Object getLayout() {
        return R.layout.mine;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        String currentUsername = authModel.getCurrentLoginUser().getUsername();
        tvUsername.setText(currentUsername);
        String currentLoginUsername = authModel.getCurrentLoginUser().getUsername();
        userModel.getPublicProfile(currentLoginUsername, new DataListener<User>() {
            @Override
            public void onReturn(User user) {
                // TODO 控件赋值
                GlideUtils.load(getContext(), user.getAvatarPath(), civAvatar);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.civ_avatar, R.id.tv_username})
    public void toProfileDelegate() {
        String currentUsername = authModel.getCurrentLoginUser().getUsername();
        getContainerActivity().start(ProfileDelegate.newInstance(currentUsername));
    }

    @OnClick(R.id.bt_my_profile)
    public void onBtMyProfileClicked() {
        getContainerActivity().start(ProfileDelegate.newInstance(authModel.getCurrentLoginUser().getUsername()));
    }

    @OnClick(R.id.bt_my_works)
    public void onBtMyWorksClicked() {
        HashMap<String, Object> filter = new HashMap<>();
        filter.put("author", authModel.getCurrentLoginUser().getUsername());
        getContainerActivity().start(WorksListDelegate.newInstance(filter));
    }

    @OnClick({R.id.bt_my_following, R.id.bt_my_follower})
    public void onBtMyAttentionOrMyFansClicked(View view) {
        int id = view.getId();
        getContainerActivity().start(FriendDelegate.newInstance(authModel.getCurrentLoginUser().getUsername(),
                id == R.id.bt_my_following ? "following" : "follower"));
    }

    @OnClick(R.id.bt_logout)
    public void onLogout() {
        authModel.logout(new DataListener<Void>() {
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
