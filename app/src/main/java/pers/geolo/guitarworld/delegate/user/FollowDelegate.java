package pers.geolo.guitarworld.delegate.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import java.util.HashMap;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseDelegate;

public class FollowDelegate extends BaseDelegate {

    private static final String CURRENT_USERNAME = "CURRENT_USERNAME";
    private static final String FOLLOW_TAG = "FOLLOW_TAG";
    @BindView(R.id.bt_following)
    Button btFollowing;
    @BindView(R.id.bt_follower)
    Button btFollower;
    Unbinder unbinder;

    private Button selectedButton;
    private BaseDelegate[] delegates;

    public static FollowDelegate newInstance(String username, String followTag) {
        Bundle args = new Bundle();
        args.putString(CURRENT_USERNAME, username);
        args.putString(FOLLOW_TAG, followTag);
        FollowDelegate fragment = new FollowDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object getLayout() {
        return R.layout.delegate_follow;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        Bundle args = getArguments();
        if (args != null) {
            String currentUsername = args.getString(CURRENT_USERNAME);
            String tag = args.getString(FOLLOW_TAG);
            HashMap<String, Object> followerFilter = new HashMap<>();
            followerFilter.put("follower", currentUsername);
            HashMap<String, Object> followingFilter = new HashMap<>();
            followingFilter.put("following", currentUsername);
            delegates = new BaseDelegate[]{
                    UserListDelegate.newInstance(followerFilter),
                    UserListDelegate.newInstance(followingFilter)
            };
            loadMultipleRootFragment(R.id.ll_fragment, 0, delegates);
            selectedButton = "following".equals(tag) ? btFollowing : btFollower;
            onViewClicked(selectedButton);
        }
    }

    @OnClick({R.id.bt_following, R.id.bt_follower})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_following:
                showHideFragment(delegates[0]);
                break;
            case R.id.bt_follower:
                showHideFragment(delegates[1]);
                break;
            default:
        }
        selectedButton.setBackgroundColor(getContext().getColor(R.color.white));
        selectedButton = (Button) view;
        selectedButton.setBackgroundColor(getContext().getColor(R.color.colorPrimary));
    }
}
