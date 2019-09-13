package pers.geolo.guitarworld.controller.user;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import org.microview.core.ViewParams;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.BaseController;
import pers.geolo.guitarworld.controller.base.BeanFactory;
import pers.geolo.guitarworld.entity.DataCallback;
import pers.geolo.guitarworld.entity.User;
import pers.geolo.guitarworld.model.AuthModel;
import pers.geolo.guitarworld.model.UserModel;
import pers.geolo.guitarworld.util.GlideUtils;
import pers.geolo.guitarworld.util.ToastUtils;

import java.util.HashMap;

public class MineController extends BaseController {

    @BindView(R.id.username_text)
    TextView tvUsername;
    @BindView(R.id.avatar_image)
    CircleImageView civAvatar;
    @BindView(R.id.profile_container)
    LinearLayout profileContainer;

    UserModel userModel = BeanFactory.getBean(UserModel.class);
    AuthModel authModel = BeanFactory.getBean(AuthModel.class);

    @Override
    protected int getLayout() {
        return R.layout.mine;
    }

    @Override
    public void initView(ViewParams viewParams) {
        String currentUsername = authModel.getLoginUser().getUsername();
        tvUsername.setText(currentUsername);
        String currentLoginUsername = authModel.getLoginUser().getUsername();
        userModel.getPublicProfile(currentLoginUsername, new DataCallback<User>() {
            @Override
            public void onReturn(User user) {
                // TODO 控件赋值

                String avatarPath = user.getAvatarUrl();
                if (avatarPath != null && !"".equals(avatarPath)) {
                    GlideUtils.load(getActivity(), avatarPath, civAvatar);
                } else {
                    // TODO 区分性别
                    civAvatar.setImageResource(R.drawable.male_default_avatar);
                }
            }

            @Override
            public void onError(String message) {
                ToastUtils.showErrorToast(getActivity(), message);
            }
        });
    }

    @OnClick({R.id.avatar_image, R.id.username_text})
    public void toProfileController() {
        String currentUsername = authModel.getLoginUser().getUsername();
//        getContainerActivity().start(ProfileController.newInstance(currentUsername));
    }

    @OnClick(R.id.my_profile_layout)
    public void onBtMyProfileClicked() {
//        getContainerActivity().start(ProfileController.newInstance(authModel.getLoginUser().getUsername()));
    }

    @OnClick(R.id.my_works_layout)
    public void onBtMyWorksClicked() {
        HashMap<String, Object> filter = new HashMap<>();
        filter.put("author", authModel.getLoginUser().getUsername());
//        getContainerActivity().start(WorksListController.newInstance(filter));
    }

    @OnClick({R.id.my_following_layout, R.id.my_follower_layout})
    public void onBtMyAttentionOrMyFansClicked(View view) {
        int id = view.getId();
//        getContainerActivity().start(FriendController.newInstance(authModel.getLoginUser().getUsername(),
//        id == R.id.bt_my_following ? "following" : "follower"));
    }

    @OnClick(R.id.logout_button)
    public void onLogout() {
        authModel.logout(new DataCallback<Void>() {
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
