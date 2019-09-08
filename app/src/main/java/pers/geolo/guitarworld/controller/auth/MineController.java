package pers.geolo.guitarworld.controller.auth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.base.BaseController;
import pers.geolo.guitarworld.controller.base.BeanFactory;
import pers.geolo.guitarworld.controller.user.FriendController;
import pers.geolo.guitarworld.controller.user.ProfileController;
import pers.geolo.guitarworld.controller.works.WorksListController;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.User;
import pers.geolo.guitarworld.model.AuthModel;
import pers.geolo.guitarworld.model.UserModel;
import pers.geolo.guitarworld.util.GlideUtils;
import pers.geolo.guitarworld.util.ToastUtils;

import java.util.HashMap;

public class MineController extends BaseController {

    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.civ_avatar)
    CircleImageView civAvatar;
    @BindView(R.id.container_profile)
    LinearLayout profileContainer;

    UserModel userModel = BeanFactory.getBean(UserModel.class);
    AuthModel authModel = BeanFactory.getBean(AuthModel.class);

    @Override
    public Object getLayoutView() {
        return R.layout.mine;
    }

    @Override
    protected View getStatueBarTopView() {
        return profileContainer;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        init();
    }

    public void init() {
        String currentUsername = authModel.getCurrentLoginUser().getUsername();
        tvUsername.setText(currentUsername);
        String currentLoginUsername = authModel.getCurrentLoginUser().getUsername();
        userModel.getPublicProfile(currentLoginUsername, new DataListener<User>() {
            @Override
            public void onReturn(User user) {
                // TODO 控件赋值

                String avatarPath = user.getAvatarUrl();
                if (avatarPath != null && !"".equals(avatarPath)) {
                    GlideUtils.load(getContext(), avatarPath, civAvatar);
                } else {
                    // TODO 区分性别
                    civAvatar.setImageResource(R.drawable.male_default_avatar);
                }
            }

            @Override
            public void onError(String message) {
                ToastUtils.showErrorToast(getContext(), message);
            }
        });
    }

    @OnClick({R.id.civ_avatar, R.id.tv_username})
    public void toProfileController() {
        String currentUsername = authModel.getCurrentLoginUser().getUsername();
        getContainerActivity().start(ProfileController.newInstance(currentUsername));
    }

    @OnClick(R.id.bt_my_profile)
    public void onBtMyProfileClicked() {
        getContainerActivity().start(ProfileController.newInstance(authModel.getCurrentLoginUser().getUsername()));
    }

    @OnClick(R.id.bt_my_works)
    public void onBtMyWorksClicked() {
        HashMap<String, Object> filter = new HashMap<>();
        filter.put("author", authModel.getCurrentLoginUser().getUsername());
        getContainerActivity().start(WorksListController.newInstance(filter));
    }

    @OnClick({R.id.bt_my_following, R.id.bt_my_follower})
    public void onBtMyAttentionOrMyFansClicked(View view) {
        int id = view.getId();
        getContainerActivity().start(FriendController.newInstance(authModel.getCurrentLoginUser().getUsername(),
                id == R.id.bt_my_following ? "following" : "follower"));
    }

    @OnClick(R.id.bt_logout)
    public void onLogout() {
        authModel.logout(new DataListener<Void>() {
            @Override
            public void onReturn(Void aVoid) {
//                startWithPop(new LoginController());
            }

            @Override
            public void onError(String message) {

            }
        });
    }

//    @Override
//    public void onSupportVisible() {
//        super.onSupportVisible();
//        init();
//    }
}
