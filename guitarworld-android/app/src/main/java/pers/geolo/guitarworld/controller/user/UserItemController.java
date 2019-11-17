package pers.geolo.guitarworld.controller.user;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import org.microview.core.ControllerManager;
import org.microview.core.ViewParams;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.BaseController;
import pers.geolo.guitarworld.entity.User;
import pers.geolo.guitarworld.util.GlideUtils;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/9/14
 */
public class UserItemController extends BaseController {

    private static final String ADD_FOLLOWING = "+关注";
    private static final String FOLLOWED = "已关注";
    private static final String FOLLOW_EACH_OTHER = "互相关注";

    @BindView(R.id.avatar_image)
    CircleImageView avatarImage;
    @BindView(R.id.username_text)
    TextView usernameText;
    @BindView(R.id.email_text)
    TextView emailText;
    @BindView(R.id.follow_button)
    Button followButton;
    @BindView(R.id.user_item_layout)
    LinearLayout userItemLayout;

    private User user;

    @Override
    protected int getLayout() {
        return R.layout.user_item;
    }

    @Override
    public void initView(ViewParams viewParams) {
        user = (User) viewParams.get("user");
        usernameText.setText(user.getUsername());
        emailText.setText(user.getEmail());
        GlideUtils.load(getActivity(), user.getAvatarUrl(), avatarImage);

    }

    @OnClick(R.id.user_item_layout)
    public void onUserItemClicked() {
        ControllerManager.start(new ProfileController(),
                new ViewParams("username", user.getUsername()));
    }

    @OnClick(R.id.follow_button)
    public void onFollowButtonClicked() {

    }
}
